package com.richcode.toggle.repository.init;

import com.richcode.toggle.repository.ToggleState;

import java.util.List;

public interface InitToggleStateProvider {

    List<ToggleState> get();

}
