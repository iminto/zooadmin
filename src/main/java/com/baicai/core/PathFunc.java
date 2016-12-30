package com.baicai.core;

import com.baicai.util.StringUtil;
import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * @author 猪肉有毒 waitfox@qq.com
 * @version V1.0
 * @Description:
 * @date 2016/12/30 9:39
 */
public class PathFunc implements Function{

    @Override
    public String call(Object[] objects, Context context) {
        Object o=objects[0];
        if(o!=null){
            String path=o.toString();
            if(path.equals("/")){
                return path;
            }else{
                path=path.substring(path.lastIndexOf("/"));
                path= StringUtil.ltrim(path,new char[]{'/'});
            }
            return path;
        }
        return null;
    }
}
