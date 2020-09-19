package com.example.arouterdemo;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.blankj.utilcode.util.SPUtils;

/**
 * @Author: chason
 * @Description:
 * @CreateDate: 2020/9/17 9:39 PM
 */
//priority越小，优先级越高，name是拦截器名称，与代码逻辑无关。（生成的ARouter文档中的description字段）
@Interceptor(priority = 1, name = "登陆拦截")
public class LoginIntercepter implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        boolean isLogin = SPUtils.getInstance().getBoolean("login");
        if (isLogin) {//已经登陆,继续跳转
            Log.i("chason", "已经登陆");
            callback.onContinue(postcard);
        } else {
            Log.i("chason", "登陆拦截" + "path==" + path);
            switch (path) {
                //case RouteConstant.SimpleActivity:
                case RouteConstant.SECOND_ACTIVITY://需要登陆的界面，进行拦截
                    callback.onInterrupt(null);
                    break;
                default:
                    callback.onContinue(postcard);
                    break;
            }
        }
    }

    @Override
    public void init(Context context) {
        Log.i("chason", "登陆拦截器初始化");
    }
}
