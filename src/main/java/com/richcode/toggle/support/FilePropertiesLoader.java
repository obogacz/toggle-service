package com.richcode.toggle.support;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class FilePropertiesLoader {

    public static final String PROPERTIES_FILE_TYPE = ".properties";

    private FilePropertiesLoader() {}

    public static Properties load(String path) throws IOException {
        validatePath(path);
        Properties properties = new Properties();
        try(InputStream resourceStream = new FileInputStream(path)) {
            properties.load(resourceStream);
        }
        return properties;
    }

    public static Properties loadFromClassPath(String fileName) throws IOException {
        validatePath(fileName);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(fileName)) {
            properties.load(resourceStream);
        }
        return properties;
    }

    private static void validatePath(String path) {
        Objects.requireNonNull(path);
        if (path.endsWith(PROPERTIES_FILE_TYPE)) {
            throw new IllegalArgumentException("Invalid properties file type.");
        };
    }

}
