package com.baicai.controller.html;

import com.baicai.core.ZKPlugin;
import com.github.zkclient.ZkClient;
import com.jfinal.core.Controller;

/**
 * @author 猪肉有毒 waitfox@qq.com
 * @version V1.0
 * @Description:
 * @date 2016/12/30 10:44
 */
public class HelpController extends Controller {

    public void index() {
        render("/admin-help.htm");
    }

    public void log() {
        ZkClient zkClient = getSessionAttr("zk-client");
        ZKPlugin zkPlugin = new ZKPlugin(zkClient);
        String[] configList = zkPlugin.fetchServerConfig();
        setAttr("config",configList);
        render("/admin-log.htm");
    }
}
