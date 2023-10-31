package com.richcode.toggle.manager;

import com.richcode.toggle.Toggle;
import com.richcode.toggle.annotation.Enabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToggleManagerTest {

    enum TestToggle implements Toggle {

        @Enabled
        TOGGLE_1,

        TOGGLE_2

    }

    private ToggleManager toggleManager;

    @BeforeEach
    void beforeEach() {
        toggleManager = ToggleManagerBuilder.builder()
            .enumInitToggleStateProvider(TestToggle.class)
            .build();
    }

    @Test
    void shouldReturnToggleEnabledTrue() {
        assertThat(toggleManager.isEnabled(TestToggle.TOGGLE_1)).isTrue();
    }

    @Test
    void shouldReturnToggleEnabledFalse() {
        assertThat(toggleManager.isEnabled(TestToggle.TOGGLE_2)).isFalse();
    }

    @Test
    void shouldReturnToggleDisabledTrue() {
        assertThat(toggleManager.isDisabled(TestToggle.TOGGLE_2)).isTrue();
    }

    @Test
    void shouldReturnToggleDisabledFalse() {
        assertThat(toggleManager.isDisabled(TestToggle.TOGGLE_1)).isFalse();
    }

    @Test
    void shouldEnableToggle() {
        toggleManager.enable(TestToggle.TOGGLE_2);
        assertThat(toggleManager.isEnabled(TestToggle.TOGGLE_2)).isTrue();
    }

    @Test
    void shouldDisableToggle() {
        toggleManager.disable(TestToggle.TOGGLE_1);
        assertThat(toggleManager.isDisabled(TestToggle.TOGGLE_1)).isTrue();
    }

    @Test
    void shouldReturnAllToggles() {
        assertThat(toggleManager.getToggles())
            .hasSize(2)
            .containsOnly(TestToggle.values());
    }

}