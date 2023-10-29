package com.richcode.toggle.repository.init;

import com.richcode.toggle.Toggle;
import com.richcode.toggle.annotation.Enabled;
import com.richcode.toggle.repository.ToggleState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class EnumInitToggleStateProvider implements InitToggleStateProvider {

    private final Map<Toggle, Boolean> initialStates;

    public EnumInitToggleStateProvider(Class<? extends Toggle> toggleEnum) {
        if (!toggleEnum.isEnum()) {
            throw new IllegalArgumentException("Provided class is not enum: " + toggleEnum.getCanonicalName());
        }
        this.initialStates = Arrays.stream(toggleEnum.getEnumConstants())
            .collect(toMap(
                Function.identity(),
                EnumInitToggleStateProvider::isEnabled));
    }

    @Override
    public List<ToggleState> get() {
        return initialStates.entrySet().stream()
            .map(entry -> new ToggleState(entry.getKey(), entry.getValue()))
            .toList();
    }

    private static boolean isEnabled(Toggle toggle) {
        try {
            return toggle
                .getClass()
                .getField(toggle.name())
                .getAnnotation(Enabled.class) != null;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

}
