package com.baicai.core;

import com.baicai.entity.ZkData;
import com.baicai.util.TelnetUtil;
import com.github.zkclient.ZkClient;
import com.jfinal.plugin.IPlugin;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * @author 猪肉有毒 waitfox@qq.com
 * @version V1.0
 * @Description:ZooKeeper插件
 * @date 2016/12/27 15:37
 */
public class ZKPlugin implements IPlugin{

    protected  Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
    private static String cxnStr;//连接字符串
    private  ZkClient client;
    private boolean isStarted = false;
    private String[] configList;

    public ZKPlugin(String cxnStr) {
        this.cxnStr=cxnStr;
        start();
    }
    
    public ZKPlugin(ZkClient client) {
        this.client=client;
    }

    @Override
    public boolean start() {
        if (isStarted)
            return true;
        client=new ZkClient(cxnStr,5000);
        isStarted = true;
        return true;
    }

    @Override
    public boolean stop() {
        if(client!=null){
            client.close();
        }
        client=null;
        isStarted = false;
        return true;
    }

    public boolean exists(String path) {
        if (path == null || path.trim().equals("")) {
            throw new IllegalArgumentException("path can not be nu;l or empty");
        }
        return getClient().exists(path);
    }

    public ZkData readData(String path) {
        ZkData zkdata = new ZkData();
        Stat stat = new Stat();
        zkdata.setData(getClient().readData(getPath(path), stat));
        zkdata.setStat(stat);
        return zkdata;
    }

    public String[] fetchServerConfig(){
        String[] configList = new String[]{};
        if(cxnStr==null){//session过期的时候可能为空
            return configList;
        }
        try {
            if (configList.length==0) {
                String[] server = cxnStr.split(":");
                String serverAddr = server[0];
                Integer port = Integer.valueOf(server[1]);
                TelnetUtil telnet = new TelnetUtil(serverAddr, port);
                telnet.connect();
                String config = telnet.writeAndRead("conf");
                telnet.close();
                configList = new String[]{};
                if (config != null) {
                    configList = config.split(System.lineSeparator());
                }
                return configList;
            } else {
                return configList;
            }
        }catch (Exception e){
            log.error("获取zookeeper配置出错",e);
            return configList;
        }
    }

    public List<String> getChildren(String path) {
        return getClient().getChildren(getPath(path));
    }

    public void create(String path, byte[] data,int mode) {
        path = getPath(path);
        Stat stat=null;
        switch (mode){
            case 0:
                getClient().createPersistent(path, true);
                stat = getClient().writeData(path, data);
                break;
            case 1:
                getClient().createEphemeral(path,data);
                break;
            case 2:
                getClient().createPersistentSequential(path,data);
                break;
            case 3:
                getClient().createEphemeralSequential(path,data);
                break;
            default:
                getClient().createPersistent(path, true);
                stat = getClient().writeData(path, data);
                break;
        }
        log.info("create: node:{}, mode={},stat{}:", path, mode,stat);
    }

    public void edit(String path, byte[] data) {
        path = getPath(path);
        Stat stat = getClient().writeData(path, data);
        log.info("edit: node:{}, stat{}:", path, stat);
    }

    public void delete(String path) {
        path = getPath(path);
        boolean del = getClient().delete(path);
        log.info("delete: node:{}, boolean{}:", path, del);
    }

    public void deleteRecursive(String path) {
        path = getPath(path);
        boolean deleteRecursive = getClient().deleteRecursive(path);
        log.info("rmr: node:{}, boolean{}:", path, deleteRecursive);
    }
    private String getPath(String path) {
        path = path == null ? "/" : path.trim();
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return path;
    }

    public String getCxnStr() {
        return cxnStr;
    }

    public void setCxnStr(String cxnStr) {
        this.cxnStr = cxnStr;
    }

    public ZkClient getClient() {
        return client;
    }

    public void setClient(ZkClient client) {
        this.client = client;
    }
}
