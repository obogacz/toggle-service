package com.richcode.toggle.repository;

import com.richcode.toggle.Toggle;

public class ToggleState {

    private final Toggle toggle;
    private final boolean state;

    public ToggleState(Toggle toggle, boolean enabled) {
        this.toggle = toggle;
        this.state = enabled;
    }

    public static ToggleState enabled(Toggle toggle) {
        return new ToggleState(toggle, true);
    }

    public static ToggleState disabled(Toggle toggle) {
        return new ToggleState(toggle, false);
    }

    public Toggle getToggle() {
        return this.toggle;
    }

    public String getToggleName() {
        return this.toggle.name();
    }

    public boolean isEnabled() {
        return this.state;
    }

    public boolean isDisabled() {
        return !isEnabled();
    }

    public ToggleState copy() {
        return new ToggleState(this.toggle, this.state);
    }

    public static ToggleState copy(ToggleState toggleState) {
        return toggleState == null ? null : toggleState.copy();
    }

}
