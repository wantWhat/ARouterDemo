package com.example.arouterdemo;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.blankj.utilcode.util.GsonUtils;
import java.lang.reflect.Type;

/**
 * @Author: chason
 * @Description: 在跳转时，将不能序列化的对象转换成json字符串，
 * 传递到目标界面后，再将json转换成对象，实现自动注入解析
 * 只在第一次调用时初始化
 * @CreateDate: 2020/9/17 8:59 PM
 */
@Route(path = RouteConstant.JSONSERICE)
public class JsonServiceImpl implements SerializationService {
    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return GsonUtils.fromJson(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return GsonUtils.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return GsonUtils.fromJson(input, clazz);
    }

    @Override
    public void init(Context context) {
        Log.i("chason","JsonServiceImpl init");
    }
}
