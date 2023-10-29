package com.richcode.app.controller;

import com.richcode.app.AppToggle;
import com.richcode.app.dto.ToggleData;
import com.richcode.app.service.ToggleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/toggles")
@RequiredArgsConstructor
public class ToggleController {

    private final ToggleService toggleService;

    @GetMapping
    public List<ToggleData> get() {
        return toggleService.getAll();
    }

    @GetMapping("/{name}")
    public ToggleData get(@PathVariable("name") AppToggle toggle) {
        return toggleService.get(toggle);
    }

    @PutMapping("/{name}")
    public void put(@PathVariable("name") AppToggle toggle, @RequestBody ToggleData toggleData) {
        if (!Objects.equals(toggle, toggleData.toggle())) {
            throw new IllegalArgumentException("Invalid toggle name");
        }
        toggleService.put(toggleData);
    }

}
