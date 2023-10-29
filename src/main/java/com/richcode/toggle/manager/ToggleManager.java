package com.richcode.toggle.manager;

import com.richcode.toggle.Toggle;
import com.richcode.toggle.repository.ToggleState;
import com.richcode.toggle.repository.ToggleStateRepository;

import java.util.List;
import java.util.Objects;

public class ToggleManager {

    private final ToggleStateRepository repository;

    public ToggleManager(ToggleStateRepository repository) {
        Objects.requireNonNull(repository);
        this.repository = repository;
    }

    public List<Toggle> getToggles() {
        return repository.getToggles();
    }

    public boolean isEnabled(Toggle toggle) {
        return repository.get(toggle).map(ToggleState::isEnabled).orElse(false);
    }

    public boolean isDisabled(Toggle toggle) {
        return !isEnabled(toggle);
    }

    public void enable(Toggle toggle) {
        repository.set(ToggleState.enabled(toggle));
    }

    public void disable(Toggle toggle) {
        repository.set(ToggleState.disabled(toggle));
    }

}
