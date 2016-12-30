package com.baicai.controller.html;

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
}
