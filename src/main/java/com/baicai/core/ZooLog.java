package com.baicai.core;

import org.apache.jute.BinaryInputArchive;
import org.apache.jute.Record;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.server.persistence.FileHeader;
import org.apache.zookeeper.server.persistence.FileTxnLog;
import org.apache.zookeeper.server.util.SerializeUtils;
import org.apache.zookeeper.txn.TxnHeader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/**
 * 读取zookeeper的日志
 */
public class ZooLog {

    private String logFile;

    public String getLogDir(String[] config){
        for (String s : config) {
            String[] configValue=s.split("=");
            if(configValue!=null && configValue[0].equals("dataLogDir")){
                return configValue[1];
            }
        }
        return null;
    }

    /**
     * 找到最新的日志文件路径
     * @param dir
     * @return
     */
    public String read(String dir) {
        File path = new File(dir);
        File[] fileArr = path.listFiles();
        List<File> list = Arrays.asList(fileArr);
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return 1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }

                }
            });
            return list.get(0).getAbsolutePath();
        }
        return null;
    }

    public static String op2String(int op) {
        switch (op) {
            case ZooDefs.OpCode.notification:
                return "notification";
            case ZooDefs.OpCode.create:
                return "create";
            case ZooDefs.OpCode.delete:
                return "delete";
            case ZooDefs.OpCode.exists:
                return "exists";
            case ZooDefs.OpCode.getData:
                return "getDate";
            case ZooDefs.OpCode.setData:
                return "setData";
            case ZooDefs.OpCode.multi:
                return "multi";
            case ZooDefs.OpCode.getACL:
                return "getACL";
            case ZooDefs.OpCode.setACL:
                return "setACL";
            case ZooDefs.OpCode.getChildren:
                return "getChildren";
            case ZooDefs.OpCode.getChildren2:
                return "getChildren2";
            case ZooDefs.OpCode.ping:
                return "ping";
            case ZooDefs.OpCode.createSession:
                return "createSession";
            case ZooDefs.OpCode.closeSession:
                return "closeSession";
            case ZooDefs.OpCode.error:
                return "error";
            default:
                return "unknown " + op;
        }
    }

    /**
     * 读取多行日志
     * @param total 读取的行数
     * @return
     * @throws IOException
     */
    public String getLastLog(int total) throws IOException {
        StringBuilder sb=new StringBuilder(1024);
        FileInputStream fis = new FileInputStream(this.logFile);
        BinaryInputArchive logStream = BinaryInputArchive.getArchive(fis);
        FileHeader fhdr = new FileHeader();
        fhdr.deserialize(logStream, "fileheader");

        if (fhdr.getMagic() != FileTxnLog.TXNLOG_MAGIC) {
            return "Invalid magic number for " + logFile;
        }
        sb.append("ZooKeeper Transactional Log File with dbid "
                + fhdr.getDbid() + " txnlog format version "
                + fhdr.getVersion()+"\r\n");
        int count=0;
        while (count<total) {
            long crcValue;
            byte[] bytes;
            try {
                crcValue = logStream.readLong("crcvalue");
                bytes = logStream.readBuffer("txnEntry");
            } catch (EOFException e) {
                sb.append("EOF reached after " + count + " txns.\r\n");
                break;
            }
            if (bytes.length == 0) {
                // Since we preallocate, we define EOF to be an
                // empty transaction
                sb.append("EOF reached after " + count + " txns.\r\n");
                break;
            }
            Checksum crc = new Adler32();
            crc.update(bytes, 0, bytes.length);
            if (crcValue != crc.getValue()) {
                throw new IOException("CRC doesn't match " + crcValue +
                        " vs " + crc.getValue());
            }
            TxnHeader hdr = new TxnHeader();
            Record txn = SerializeUtils.deserializeTxn(bytes, hdr);
            sb.append(DateFormat.getDateTimeInstance(DateFormat.SHORT,
                    DateFormat.LONG).format(new Date(hdr.getTime()))
                    + " session 0x"
                    + Long.toHexString(hdr.getClientId())
                    + " cxid 0x"
                    + Long.toHexString(hdr.getCxid())
                    + " zxid 0x"
                    + Long.toHexString(hdr.getZxid())
                    + " " + ZooLog.op2String(hdr.getType()) + " " + txn+"\r\n");
            if (logStream.readByte("EOR") != 'B') {
                sb.append("Last transaction was partial.");
            }
            count++;
        }
        return sb.toString();
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
}
