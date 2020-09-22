package com.example.arouterdemo;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @ClassName HelloService
 * @Author dongxueqiang
 * @Date 2020/9/21 17:16
 * @Title
 */
@Route(path = RouteConstant.HELLO_SERVICE)
interface HelloService extends IProvider {
    void sayHello(String content);
    void sayBye(String content);
}
