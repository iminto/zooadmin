package com.baicai.core;

import com.github.zkclient.ZkClient;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * @author 猪肉有毒 waitfox@qq.com
 * @version V1.0
 * @Description:
 * @date 2016/12/29 19:12
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        ZkClient zkClient=inv.getController().getSessionAttr("zk-client");
        if(zkClient!=null) {
            inv.invoke();
        }else{
            inv.getController().redirect("/index.html");
        }

    }
}
