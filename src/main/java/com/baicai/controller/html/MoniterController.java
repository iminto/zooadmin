package com.baicai.controller.html;

import com.baicai.core.ZKPlugin;
import com.baicai.entity.ZooConnection;
import com.github.zkclient.ZkClient;
import com.jfinal.core.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * 监控页面
 */
public class MoniterController extends Controller {

    public static Logger log = (Logger) LoggerFactory.getLogger(MoniterController.class);

    public void index() {
        ZkClient zkClient = getSessionAttr("zk-client");
        ZKPlugin zkPlugin = new ZKPlugin(zkClient);
        String[] sessionList = zkPlugin.fetchSessionList();
        List<ZooConnection> zooSession=new ArrayList<>();
        if(sessionList!=null && sessionList.length>0){
            for(int i=0;i<sessionList.length;i++){
                ZooConnection conn=new ZooConnection();
                conn.setId(i+1);
                String connectionStr=sessionList[i];
                String host="";
                String detail="";
                if(connectionStr!=null && connectionStr.length()>1) {
                    int index = connectionStr.indexOf("(");
                    if(index==-1) index=0;
                    host = connectionStr.substring(0, index>0?index:1);
                    detail = connectionStr.substring(index, connectionStr.length());
                }
                conn.setHost(host);
                conn.setDetail(detail);
                zooSession.add(conn);
            }
        }
        setAttr("zooSession",zooSession);
        render("/admin-moniter.htm");
    }
}
