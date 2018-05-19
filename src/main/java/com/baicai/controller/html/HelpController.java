package com.baicai.controller.html;

import com.baicai.core.ZKPlugin;
import com.baicai.core.ZooLog;
import com.github.zkclient.ZkClient;
import com.jfinal.core.Controller;
import org.apache.jute.BinaryInputArchive;
import org.apache.jute.Record;
import org.apache.zookeeper.server.TraceFormatter;
import org.apache.zookeeper.server.persistence.FileHeader;
import org.apache.zookeeper.server.persistence.FileTxnLog;
import org.apache.zookeeper.server.util.SerializeUtils;
import org.apache.zookeeper.txn.TxnHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/**
 * @author 猪肉有毒 waitfox@qq.com
 * @version V1.0
 * @Description:
 * @date 2016/12/30 10:44
 */
public class HelpController extends Controller {

    public static Logger log = (Logger) LoggerFactory.getLogger(HelpController.class);

    public void index() {
        render("/admin-help.htm");
    }

    public void log() {
        String logString="";
        ZkClient zkClient = getSessionAttr("zk-client");
        ZKPlugin zkPlugin = new ZKPlugin(zkClient);
        String[] configList = zkPlugin.fetchServerConfig();
        setAttr("config",configList);
        try {
            ZooLog zooLog = new ZooLog();
            String dataDir = zooLog.getLogDir(configList);
            String logFile = zooLog.read(dataDir);
            zooLog.setLogFile(logFile);
            logString = zooLog.getLastLog(100);
        }catch (Exception e){
            log.error("读取系统日志出错",e);
        }
        setAttr("logString", logString);
        render("/admin-log.htm");
    }
}
