package com.baicai.util;
import java.util.*;
public class PropUtil {
    private static ResourceBundle config  = ResourceBundle.getBundle("user");

    private static String         delimit = ",";

    // 加载少量必要配置，随后可用getProperty(...)获取值
    public Properties loadProp(final String pro, final String dev) {
        try {
            return loadPropertyFile(pro, "UTF-8");
        } catch (final Exception e) {
            return loadPropertyFile(dev, "UTF-8");
        }
    }

    /**
     * Load property file. Example:<br>
     * loadPropertyFile("db_username_pass.txt", "UTF-8");
     *
     * @param fileName
     *            the file in CLASSPATH or the sub directory of the CLASSPATH
     * @param encoding
     *            the encoding
     */
    public Properties loadPropertyFile(final String fileName,
                                       final String encoding) {
        final Prop prop = PropKit.use(fileName, encoding);
        return prop.getProperties();
    }

    /**
     * @Date: 2013-5-21上午10:09:32
     * @Description: Hashtable
     * @param key
     * @return 返回某key值的Map
     */
    public static Hashtable<String, String> getMap(final String key) {
        final String vals[] = getStrings(key);
        final Hashtable<String, String> map = new Hashtable<String, String>(
                vals.length);
        for (int i = 0; i < vals.length; i++) {
            final String[] nv = split(vals[i], ":");
            if (nv.length == 2) {
                map.put(nv[0], nv[1]);
            }
        }

        return map;
    }

    /**
     * @Date: 2013-5-21上午10:09:49
     * @Description: String
     * @param key
     * @return 获得某key值的String
     */
    public static String get(final String key) {
        final String val = config.getString(key).trim();
        return val;
    }

    /**
     * @Date: 2013-5-21上午10:09:49
     * @Description: String
     * @param key
     * @return 获得某key值的String
     */
    public static String getString(final String key) {
        final String val = config.getString(key).trim();
        return val;
    }

    public static String getString(final Properties pro, final String key) {
        final String val = pro.getProperty(key);
        return val;
    }

    /**
     * @Date: 2013-5-21上午10:10:06
     * @Description: String[]
     * @param key
     * @return 获取某key值的String
     */
    public static String[] getStrings(final String key) {
        final String str = getString(key);
        return split(str, delimit);
    }

    private static String[] split(final String str, final String del) {
        final StringTokenizer st = new StringTokenizer(str, del);
        final ArrayList<String> al = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            final String v = st.nextToken();
            al.add(v.trim());
        }
        final String[] vals = new String[al.size()];
        al.toArray(vals);
        return vals;
    }

    /**
     * @Date: 2013-5-21上午10:10:28
     * @Description: int
     * @param key
     * @return 获取某key值的String
     */
    public static int getInt(final String key) {
        final String val = getString(key);

        int i = -1;
        i = Integer.parseInt(val);
        return i;
    }

    /**
     * @Date: 2013-5-21上午10:10:39
     * @Description: long
     * @param key
     * @return 获取某key值的Long
     */
    public static long getLong(final String key) {
        final String val = getString(key);
        long l = -1;
        l = Long.parseLong(val);
        return l;
    }

    /**
     * @Date: 2013-5-21上午10:10:56
     * @Description: double
     * @param key
     * @return 获取某key值的Double
     */
    public static double getDouble(final String key) {
        final String val = getString(key);
        double d = 0.0;
        d = Double.parseDouble(val);
        return d;
    }

    /**
     * @Date: 2013-5-21上午10:11:12
     * @Description: boolean
     * @param key
     * @return 获取Boolean对象值
     */
    public static boolean getBoolean(final String key) {
        final String val = getString(key);
        boolean b = false;
        b = Boolean.valueOf(val).booleanValue();
        return b;
    }
}
