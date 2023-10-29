package com.richcode.toggle.repository;

import com.richcode.toggle.Toggle;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ToggleStateRepository {

    default void init(Collection<ToggleState> initStates) {
        initStates.forEach(this::set);
    }

    List<Toggle> getToggles();

    Optional<ToggleState> get(Toggle toggle);

    void set(ToggleState toggleState);

}
