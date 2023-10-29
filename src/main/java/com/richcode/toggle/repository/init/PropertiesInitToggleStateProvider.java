package com.richcode.toggle.repository.init;

import com.richcode.toggle.Toggle;
import com.richcode.toggle.SimpleToggle;
import com.richcode.toggle.repository.ToggleState;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static java.util.stream.Collectors.toMap;

public class PropertiesInitToggleStateProvider implements InitToggleStateProvider {

    public static final String PROPERTY_PREFIX = "toggles";

    private final Map<Toggle, Boolean> initialStates;

    public PropertiesInitToggleStateProvider(Properties properties) {
        this.initialStates = properties.entrySet().stream()
            .filter(entry -> ((String) entry.getKey()).startsWith(PROPERTY_PREFIX))
            .collect(toMap(
                entry -> new SimpleToggle(extractToggleName((String) entry.getKey())),
                entry -> Boolean.parseBoolean((String) entry.getValue())));
    }

    private static String extractToggleName(String propertyKey) {
        return propertyKey.replace(PROPERTY_PREFIX + ".", "").toUpperCase();
    }

    @Override
    public List<ToggleState> get() {
        return initialStates.entrySet().stream()
            .map(entry -> new ToggleState(entry.getKey(), entry.getValue()))
            .toList();
    }

}
