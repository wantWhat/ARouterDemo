package com.example.arouterdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.arouterdemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        //普通跳转
        viewBinding.turnSimpleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(RouteConstant.SimpleActivity).navigation(MainActivity.this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.i("chason", "onArrival path=" + postcard.getPath());
                    }
                });
            }
        });
        viewBinding.turnWithParmasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //使用api传递参数
                ARouter.getInstance().build(RouteConstant.SimpleActivity)
                        .withString("mUserName", "hello")
                        .withInt("mAge", 15)
                        .withString("type", "test")
                        .withParcelable("userBean", new UserBean("chason", 17))
                        .navigation();

            }
        });
        viewBinding.turnWithNoParcelableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传递未序列化的对象，（map，List，bean）
                Person person1 = new Person("chason1", "男1");
                Person person2 = new Person("chason2", "男2");
                Person person3 = new Person("chason3", "男3");
                List<Person> list = new ArrayList<>();
                list.add(person1);
                list.add(person2);
                list.add(person3);
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                map.put("key3", "value3");
                ARouter.getInstance().build(RouteConstant.SimpleActivity)
                        .withObject("list", list)
                        .withObject("map", map)
                        .navigation();
            }
        });
        viewBinding.turnWithUriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri跳转， 使用scheme，从应用外部打开,先跳转到SchemeFilter之后，交给ARouter进行跳转
                String url = "common://com.example.arouterdemo" + RouteConstant.URI_ACTIVITY + "?mUserName=chason&age=18";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        viewBinding.interceptorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拦截器
                //指定路由分组，也可以直接在Activity中的注解中添加group参数
                ARouter.getInstance().build(RouteConstant.SECOND_ACTIVITY).navigation(MainActivity.this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        Log.i("chason", "已找到 path=" + postcard.getPath());
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        Log.i("chason", "界面未找到 path=" + postcard.getPath());
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.i("chason", "已跳转 path=" + postcard.getPath());
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        Log.i("chason", "已拦截 path=" + postcard.getPath());
                        //将被拦截的路由，传递给login界面，登陆成功后，再进行跳转
                        ARouter.getInstance().build(RouteConstant.LOGIN)
                                .withString(LoginActivity.KEY_PATH, postcard.getPath())
                                .navigation();
                    }
                });
            }
        });
        viewBinding.intenerfaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*HelloService service =  ARouter.getInstance().navigation(HelloService.class);
               service.sayHello("hello");
               service.sayBye("bye");*/
              HelloService service = (HelloService) ARouter.getInstance().build(RouteConstant.HELLO_SERVICE).navigation();
                service.sayHello("hello");
            }
        });
        viewBinding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.getInstance().put("login", false);
                ToastUtils.showShort("退出登陆");
            }
        });
    }
}