package com.baicai.controller.html;

import com.baicai.core.ResultData;
import com.baicai.core.ZKPlugin;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 猪肉有毒 waitfox@qq.com
 * @version V1.0
 * @Description:
 * @date 2016/12/27 13:59
 */
public class IndexController extends Controller {
    public static Logger log = (Logger) LoggerFactory.getLogger(IndexController.class);

    public void index() {
        render("/login.htm");
    }

    public void dologin() {
        ResultData result = new ResultData();
        String addr = getPara("addr");
        String password = getPara("password");
        String pwd = PropKit.use("user.properties").get("root");
        if (StrKit.isBlank(password) || !password.equals(pwd)) {
            result.setSuccess(false);
            result.setMessage("请输入正确的密码登陆！");
            renderJson(result);
        } else {
            if (StrKit.notBlank(addr)) {
                try {
                    ZKPlugin zkPlugin = new ZKPlugin(addr);
                    if (getSessionAttr("zk-client") == null) {
                        setSessionAttr("zk-client", zkPlugin.getClient());
                        setSessionAttr("addr", addr);
                    }
                } catch (Exception e) {
                    log.error("ZKPlugin error.", e);
                    result.setSuccess(false);
                    result.setMessage("连接到ZooKeeper失败，请复核！");
                }

            } else {
                result.setSuccess(false);
                result.setMessage("ZooKeeper 地址不能为空！");
            }
            renderJson(result);
        }
    }

    public void test() {
        renderText("Hello ZooAdmin.");
    }

    public void logout(){
        removeSessionAttr("addr");
        removeSessionAttr("zk-client");
        redirect("/index.html");
    }
}
