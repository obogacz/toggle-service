package com.richcode.app.configuration;

import com.richcode.app.AppToggle;
import com.richcode.toggle.manager.ToggleManager;
import com.richcode.toggle.manager.ToggleManagerBuilder;
import com.richcode.toggle.repository.InMemoryToggleStateRepository;
import com.richcode.toggle.support.SpringApplicationContextPropertiesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ToggleConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ToggleManager toggleManager() {
        return ToggleManagerBuilder.builder()
//            .propertiesInitToggleStateProvider(SpringApplicationContextPropertiesLoader.load(applicationContext))
//            .propertiesInitToggleStateProvider(FilePropertiesLoader.loadFromClassPath("toggles.properties"))
//            .mapInitToggleStateProvider(Map.of(
//                AppToggle.TOGGLE_1, true,
//                AppToggle.TOGGLE_2, false,
//                AppToggle.ANOTHER_TOGGLE, true
//            ))
            .enumInitToggleStateProvider(AppToggle.class)
            .toggleStateRepository(new InMemoryToggleStateRepository()) // default
            .build();
    }


}
