package com.richcode.toggle.manager;

import com.richcode.toggle.Toggle;
import com.richcode.toggle.repository.InMemoryToggleStateRepository;
import com.richcode.toggle.repository.ToggleStateRepository;
import com.richcode.toggle.repository.init.EnumInitToggleStateProvider;
import com.richcode.toggle.repository.init.InitToggleStateProvider;
import com.richcode.toggle.repository.init.MapInitToggleStateProvider;
import com.richcode.toggle.repository.init.PropertiesInitToggleStateProvider;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class ToggleManagerBuilder {

    private InitToggleStateProvider initToggleStateProvider;
    private ToggleStateRepository toggleStateRepository = new InMemoryToggleStateRepository();

    private ToggleManagerBuilder() {
    }

    public static ToggleManagerBuilder builder() {
        return new ToggleManagerBuilder();
    }

    public ToggleManagerBuilder initToggleStateProvider(InitToggleStateProvider initToggleStateProvider) {
        this.initToggleStateProvider = initToggleStateProvider;
        return this;
    }

    public ToggleManagerBuilder enumInitToggleStateProvider(Class<? extends Toggle> toggleEnum) {
        this.initToggleStateProvider(new EnumInitToggleStateProvider(toggleEnum));
        return this;
    }

    public ToggleManagerBuilder propertiesInitToggleStateProvider(Properties properties) {
        this.initToggleStateProvider(new PropertiesInitToggleStateProvider(properties));
        return this;
    }

    public ToggleManagerBuilder mapInitToggleStateProvider(Map<Toggle, Boolean> map) {
        this.initToggleStateProvider(new MapInitToggleStateProvider(map));
        return this;
    }

    public ToggleManagerBuilder toggleStateRepository(ToggleStateRepository toggleStateRepository) {
        this.toggleStateRepository = toggleStateRepository;
        return this;
    }

    public ToggleManager build() {
        Objects.requireNonNull(initToggleStateProvider);
        Objects.requireNonNull(toggleStateRepository);
        toggleStateRepository.init(initToggleStateProvider.get());
        return new ToggleManager(toggleStateRepository);
    }

}
