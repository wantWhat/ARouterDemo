package com.example.arouterdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.arouterdemo.databinding.ActivitySimpleBinding;

import java.util.List;
import java.util.Map;

// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = RouteConstant.SimpleActivity, name = "测试界面")
public class SimpleActivity extends AppCompatActivity {
    //Autowired
    @Autowired
    String mUserName;
    @Autowired
    int mAge;
    @Autowired(name = "type")
    String mParmas;
    @Autowired
    UserBean userBean;

    @Autowired
    List<Person> list;
    @Autowired
    Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySimpleBinding binding = ActivitySimpleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ARouter.getInstance().inject(this);
        binding.textView.setText(mUserName + "," + mAge + "," + mParmas);
        if (userBean != null) {
            binding.userTv.setText(userBean.toString());
        }
        if (list != null) {
            binding.objectListTv.setText(list.toString());
        }
        if (map != null) {
            binding.objectMapTv.setText(map.toString());
        }
    }
}