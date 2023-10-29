package com.richcode.toggle.repository.init;

import com.richcode.toggle.Toggle;
import com.richcode.toggle.repository.ToggleState;

import java.util.List;
import java.util.Map;

public class MapInitToggleStateProvider implements InitToggleStateProvider {

    private final Map<Toggle, Boolean> initialStates;

    public MapInitToggleStateProvider(final Map<Toggle, Boolean> map) {
        this.initialStates = map;
    }

    @Override
    public List<ToggleState> get() {
        return initialStates.entrySet().stream()
            .map(entry -> new ToggleState(entry.getKey(), entry.getValue()))
            .toList();
    }

}
