package com.nixsolutions.bondarenko.study.jsp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Yuliya Bondarenko
 */
public class PropertySource {
    public static Properties getDbProperties() {
        String resourceName = "db.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try (InputStream resourceStream = loader
                .getResourceAsStream(resourceName)) {
            properties.load(resourceStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Can't find db.properties", e);
        }
    }
}
