package com.baicai.core;

import com.baicai.controller.html.HelpController;
import com.baicai.controller.html.IndexController;
import com.baicai.controller.html.NodeController;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PathKit;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;
import java.util.HashMap;
import java.util.Map;
/**
 * @author 猪肉有毒 waitfox@qq.com
 * @version V1.0
 * @Description:
 * @date 2016/12/27 13:22
 */
public class Config extends JFinalConfig {
    public void configConstant(Constants me) {
        me.setDevMode(false);
        me.setMainRenderFactory(new BeetlRenderFactory());
        // 获取GroupTemplate ,可置共享变量等操作
        GroupTemplate group = BeetlRenderFactory.groupTemplate;
        Map<String, Object> shared = new HashMap<String, Object>();
        group.setSharedVars(shared);
    }

    public void configRoute(Routes me) {
        me.add("/", IndexController.class);
        me.add("/node", NodeController.class);
        me.add("/help", HelpController.class);
    }

    public void configPlugin(Plugins me) {
    }

    public void configInterceptor(Interceptors me) {
    }

    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("host"));
        me.add(new ActionExtentionHandler());
    }

    public void afterJFinalStart(){};
    public void beforeJFinalStop(){};
}

