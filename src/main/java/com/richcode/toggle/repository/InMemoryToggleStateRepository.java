package com.richcode.toggle.repository;

import com.richcode.toggle.Toggle;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryToggleStateRepository implements ToggleStateRepository {

    private final ConcurrentHashMap<String, ToggleState> states = new ConcurrentHashMap<>();

    @Override
    public List<Toggle> getToggles() {
        return states.values().stream()
            .map(ToggleState::getToggle)
            .toList();
    }

    @Override
    public Optional<ToggleState> get(Toggle toggle) {
        return Optional
            .ofNullable(states.get(toggle.name()))
            .map(state -> ToggleState.copy(state));
    }

    @Override
    public void set(ToggleState toggleState) {
        states.put(toggleState.getToggleName(), ToggleState.copy(toggleState));
    }

}
