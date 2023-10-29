package com.richcode.app.service;

import com.richcode.app.AppToggle;
import com.richcode.app.dto.ToggleData;
import com.richcode.toggle.Toggle;
import com.richcode.toggle.manager.ToggleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class ToggleService {

    private final ToggleManager toggleManager;

    public Set<AppToggle> getToggles() {
        return toggleManager.getToggles().stream()
            .map(Toggle::name)
            .map(AppToggle::valueOf)
            .collect(toSet());
    }

    public ToggleData get(AppToggle toggle) {
        return new ToggleData(toggle, toggleManager.isEnabled(toggle));
    }

    public List<ToggleData> getAll() {
        return getToggles().stream()
            .map(this::get)
            .sorted(Comparator.comparing(ToggleData::toggle))
            .toList();
    }

    public void put(ToggleData toggle) {
        if (toggle.enabled()) {
            toggleManager.enable(toggle.toggle());
        } else {
            toggleManager.disable(toggle.toggle());
        }
    }

}
