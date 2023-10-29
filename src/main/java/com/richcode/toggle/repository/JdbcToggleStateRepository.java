package com.richcode.toggle.repository;

import com.richcode.toggle.Toggle;

import java.util.List;
import java.util.Optional;

public class JdbcToggleStateRepository implements ToggleStateRepository {

    @Override
    public List<Toggle> getToggles() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Optional<ToggleState> get(Toggle toggle) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void set(ToggleState toggleState) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
