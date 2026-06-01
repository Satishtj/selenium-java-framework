package com.qaframework.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * Loads configuration from {@code config.properties} on the classpath.
 * Any value can be overridden at runtime via a system property of the same key,
 * e.g. {@code -Dbrowser=firefox -Dheadless=true}.
 */
public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            PROPERTIES.load(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load config.properties: " + e.getMessage());
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        // System property wins, so CI/CLI can override file values.
        return System.getProperty(key, PROPERTIES.getProperty(key));
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key, "false"));
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        return value != null ? Integer.parseInt(value.trim()) : defaultValue;
    }
}
