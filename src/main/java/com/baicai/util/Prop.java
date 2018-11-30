package com.baicai.util;

import java.io.*;
import java.util.Properties;

/**
 * Prop. Prop can load properties file from CLASSPATH or File object.
 */
public class Prop {

    private Properties properties = null;

    /**
     * Prop constructor.
     *
     * @see #Prop(String, String)
     */
    public Prop(final String fileName) {
        this(fileName, "UTF-8");
    }

    /**
     * Prop constructor
     * <p>
     * Example:<br>
     * Prop prop = new Prop("my_config.txt", "UTF-8");<br>
     * String userName = prop.get("userName");<br>
     * <br>
     * prop = new Prop("com/jfinal/file_in_sub_path_of_classpath.txt", "UTF-8"); <br>
     * String value = prop.get("key");
     *
     * @param fileName
     *            the properties file's name in classpath or the sub directory of classpath
     * @param encoding
     *            the encoding
     */
    public Prop(final String fileName, final String encoding) {
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName); // properties.load(Prop.class.getResourceAsStream(fileName));
            if (inputStream == null) {
                throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
            }
            properties = new Properties();
            properties.load(new InputStreamReader(inputStream, encoding));
        } catch (final IOException e) {
            throw new RuntimeException("Error loading properties file.", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException e) {
                    throw new RuntimeException("Error loading properties file.", e);
                }
            }
        }
    }

    /**
     * Prop constructor.
     *
     * @see #Prop(File, String)
     */
    public Prop(final File file) {
        this(file, "UTF-8");
    }

    /**
     * Prop constructor
     * <p>
     * Example:<br>
     * Prop prop = new Prop(new File("/var/config/my_config.txt"), "UTF-8");<br>
     * String userName = prop.get("userName");
     *
     * @param file
     *            the properties File object
     * @param encoding
     *            the encoding
     */
    public Prop(final File file, final String encoding) {
        if (file == null) {
            throw new IllegalArgumentException("File can not be null.");
        }
        if (file.isFile() == false) {
            throw new IllegalArgumentException("Not a file : " + file.getName());
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            properties = new Properties();
            properties.load(new InputStreamReader(inputStream, encoding));
        } catch (final IOException e) {
            throw new RuntimeException("Error loading properties file.", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException e) {
                    throw new RuntimeException("Error loading properties file.", e);
                }
            }
        }
    }

    public String get(final String key) {
        return properties.getProperty(key);
    }

    public String get(final String key, final String defaultValue) {
        final String value = get(key);
        return (value != null) ? value : defaultValue;
    }

    public Integer getInt(final String key) {
        final String value = get(key);
        return (value != null) ? Integer.parseInt(value) : null;
    }

    public Integer getInt(final String key, final Integer defaultValue) {
        final String value = get(key);
        return (value != null) ? Integer.parseInt(value) : defaultValue;
    }

    public Long getLong(final String key) {
        final String value = get(key);
        return (value != null) ? Long.parseLong(value) : null;
    }

    public Long getLong(final String key, final Long defaultValue) {
        final String value = get(key);
        return (value != null) ? Long.parseLong(value) : defaultValue;
    }

    public Boolean getBoolean(final String key) {
        final String value = get(key);
        return (value != null) ? Boolean.parseBoolean(value) : null;
    }

    public Boolean getBoolean(final String key, final Boolean defaultValue) {
        final String value = get(key);
        return (value != null) ? Boolean.parseBoolean(value) : defaultValue;
    }

    public boolean containsKey(final String key) {
        return properties.containsKey(key);
    }

    public Properties getProperties() {
        return properties;
    }
}
