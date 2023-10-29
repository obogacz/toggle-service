package com.richcode.toggle;

import java.util.Objects;

public record SimpleToggle(String name) implements Toggle {

    public SimpleToggle {
        name = Objects.requireNonNull(name).toUpperCase();
    }

}
