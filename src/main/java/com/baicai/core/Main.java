package com.baicai.core;

import com.baicai.util.PropUtil;
import com.jfinal.core.JFinalFilter;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.io.File;

public class Main {
    protected static Logger log= LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setContextPath("/")
                .setClassLoader(Main.class.getClassLoader())
                .setDeploymentName("zooadmin.war")
                ;
        Integer port= PropUtil.getInt("port");
        String host=PropUtil.getString("host");
        String resource=PropUtil.getString("resource");
        FilterInfo jfinalFilter=new FilterInfo("jfinal",JFinalFilter.class);
        jfinalFilter.addInitParam("configClass","com.baicai.core.Config");
        servletBuilder.addFilter(jfinalFilter);
        servletBuilder.addFilterUrlMapping("jfinal","/*", DispatcherType.REQUEST);
        servletBuilder.addFilterUrlMapping("jfinal","/*", DispatcherType.FORWARD);
        servletBuilder.setResourceManager(new FileResourceManager(new File(resource), 1024));


        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        PathHandler path = Handlers.path(Handlers.redirect("/"))
               .addPrefixPath("/", manager.start());
        Undertow server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(path)
                .build();
        // start server
        server.start();
        log.info("http://"+host+":"+port);
    }
}
