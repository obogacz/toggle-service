package com.richcode.toggle.support;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.Properties;

import static java.util.stream.Collectors.toMap;

public class SpringApplicationContextPropertiesLoader {

    public static final String PROPERTY_PREFIX = "toggles";
    private SpringApplicationContextPropertiesLoader() {}

    public static Properties load(final ApplicationContext applicationContext) {
        return load(applicationContext.getEnvironment());
    }

    public static Properties load(final Environment environment) {
        Properties properties = new Properties();
        BindResult<Map<String, String>> bindResult = Binder
            .get(environment)
            .bind(PROPERTY_PREFIX, Bindable.mapOf(String.class, String.class));

        if (!bindResult.isBound() || bindResult.get() == null || bindResult.get().isEmpty()) {
            return properties;
        }

        Map<String, String> propertiesMap = bindResult.get().entrySet().stream()
            .collect(toMap(
                entry -> PROPERTY_PREFIX + "." + entry.getKey(),
                Map.Entry::getValue));

        properties.putAll(propertiesMap);
        return properties;
    }

}
