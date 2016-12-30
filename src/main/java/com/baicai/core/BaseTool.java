package com.baicai.core;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;

public class BaseTool {

    private static NumberFormat fmtI = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.CHINA));
    private static NumberFormat fmtD = new DecimalFormat("###,##0.000", new DecimalFormatSymbols(Locale.CHINA));

    public static String getServer() {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ArrayList<String> endPoints = new ArrayList<String>();
        try {
            Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
            String hostname = InetAddress.getLocalHost().getHostName();
            InetAddress[] addresses = InetAddress.getAllByName(hostname);
            for (Iterator<ObjectName> i = objs.iterator(); i.hasNext(); ) {
                ObjectName obj = i.next();
                String scheme = mbs.getAttribute(obj, "scheme").toString();
                String port = obj.getKeyProperty("port");
                for (InetAddress addr : addresses) {
                    String host = addr.getHostAddress();
                    String ep = scheme + "://" + host + ":" + port;
                    endPoints.add(ep);
                }
            }
        } catch (Exception e) {
            return "";
        }
        if (endPoints.size() > 0) {
            return endPoints.get(0);
        } else {
            return "";
        }
    }

    public static String toDuration() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        double uptime = runtime.getUptime();
        uptime /= 1000;
        if (uptime < 60) {
            return fmtD.format(uptime) + "秒";
        }
        uptime /= 60;
        if (uptime < 60) {
            long minutes = (long) uptime;
            String s = fmtI.format(minutes) + "分";
            return s;
        }
        uptime /= 60;
        if (uptime < 24) {
            long hours = (long) uptime;
            long minutes = (long) ((uptime - hours) * 60);
            String s = fmtI.format(hours) + "小时";
            if (minutes != 0) {
                s += " " + fmtI.format(minutes) + "分";
            }
            return s;
        }
        uptime /= 24;
        long days = (long) uptime;
        long hours = (long) ((uptime - days) * 24);
        String s = fmtI.format(days) + "天";
        if (hours != 0) {
            s += " " + fmtI.format(hours) + "小时";
        }
        return s;
    }

    public static List<String> splitPath(String str, char split) {
        List<String> strL = new ArrayList<>();
        if (str.charAt(str.length() - 1) != split) {
            str = str + split;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == split) {
                String stri = "";
                if (i == 0) {
                    stri = str.substring(0, 1);
                } else {
                    stri = str.substring(0, i);
                }
                if (stri != null || !("").equals(stri)) {
                    strL.add(stri);
                }
            }
        }
        return strL;
    }

}
