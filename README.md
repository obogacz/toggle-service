# Toggle Service

## General info

[Feature Toggles](https://martinfowler.com/articles/feature-toggles.html) (or Feature Flags) pattern is 
a common technique of features management and their availability for users on the fly without changing the codebase.

Toggle Service provides a simple and flexible implementation of the Feature Toggles pattern. 
It allows you to associate a toggle with your codebase and switch dynamically its behaviour in the application runtime.

## Technologies

- [Java 21](https://openjdk.org/projects/jdk/21/)
- [SpringBoot 3](https://spring.io/projects/spring-boot) - to show example usage
- [Swagger UI](https://swagger.io/tools/swagger-ui/) - to show example usage

## Setup

For the running of real usage examples of Toggle Service, this project provides a simple SpringBoot app with 
one REST controller and is placed in the `com.richcode.app` package. The app exposes 
a Swagger UI under: http://localhost:8080/swagger-ui/index.html and it can be used to test how the toggle management 
could look like from the REST API perspective.

Implementation of Feature Toggles and its feature is in the `com.richcode.toggle` package. There is everything that 
is needed to configure your toggles and Toggle Manager.

## Configuration

To configure Toggle Service you need to provide of Toggles List and Toggle Manager.

The toggles list is a definition of your toggles and by its elements, you can obtain states of your switches. 
The easiest way to do it is to define the toggle enum which implements the `Toggle` interface.

```java
public enum AppToggle implements Toggle {

    TOGGLE_1,
    TOGGLE_2,
    ANOTHER_TOGGLE

}
```

As the last step, you need to put some glue between Toggle Service components by creating the object of Toggle Manager. 
`ToggleManagerBuilder` helps in this task and using its methods you can choose the way of loading initial states of 
the toggles and the way how and where these states are stored. In-memory storage is the default option. 
If you do not declare the initial state for your toggle, the default state is `false`(not active/disabled).

The most advantageous option for SpringBoot is to declare the Toggle Manager as a bean.

```java
@Configuration
public class ToggleConfig {

    @Bean
    public ToggleManager toggleManager() {
        return ToggleManagerBuilder.builder()
            .mapInitToggleStateProvider(Map.of(
                AppToggle.TOGGLE_1, true,
                AppToggle.TOGGLE_2, false,
                AppToggle.ANOTHER_TOGGLE, true
            ))
            .toggleStateRepository(new InMemoryToggleStateRepository())
            .build();
    }

}
```

After that, your Toggle Service is configured and ready to be used. Toggle Manager provides a bunch of methods by which 
you are able to get and manage of states of your toggles.

### Providing initial states by an enum

You can declare the initial states of your toggles by using the `@Enabled` annotation.
Toggles with this annotation will have the state equal to `true`(active/enabled).
Toggles without this annotation will have the state equal to `false`(not active/disabled).

```java
public enum AppToggle implements Toggle {

    @Enabled
    TOGGLE_1,
    
    TOGGLE_2,
    
    ANOTHER_TOGGLE

}
```

Then just indicate that your source of initial states is the enum class. 
Use the appropriate `ToggleManagerBuilder` method.

```java
@Configuration
public class ToggleConfig {

    @Bean
    public ToggleManager toggleManager() {
        return ToggleManagerBuilder.builder()
            .enumInitToggleStateProvider(AppToggle.class)
            .build();
    }

}
```
### Providing initial states by a properties

Initial states can be loaded from an object of the `Properties` class. Toggle Service includes the util classes which 
can help to provide it. It is possible to load properties from a file with the `.properties` extension or 
from an `ApplicationContext` of SpringBoot.

```java
@Configuration
public class ToggleConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ToggleManager toggleManager() {
        return ToggleManagerBuilder.builder()
            .propertiesInitToggleStateProvider(SpringApplicationContextPropertiesLoader.load(applicationContext))
            
            // or ...
                
            .propertiesInitToggleStateProvider(FilePropertiesLoader.load("/full/path/to/toggles.properties"))

            // or ...

            .propertiesInitToggleStateProvider(FilePropertiesLoader.loadFromClassPath("toggles.properties"))
            .build();
    }


}
```

Example content of properties files are presented bellow. Be aware that all your properties keys have to starts 
from word `toggles`. They should always have the format `toggles.toggle_name=true/false`.

For `.properties` file:
```properties
toggles.TOGGLE_1=true
toggles.toggle_2=false
toggles.another_TOGGLE=true
```

For `.yaml` file:
```yaml
toggles:
  TOGGLE_1: true
  toggle_2: false
  another_TOGGLE: true
```

## Advanced configuration

With Toggle Service, nothing stands in the way of providing own methods of loading initial states of 
toggles and storage.

Implement `InitToggleStateProvider` interface to provide own way of loading the initial states of toggles.

```java
public interface InitToggleStateProvider {

    List<ToggleState> get();

}
```

Implement `ToggleStateRepository` interface to provide own way of storing of the toggles states.

```java
public interface ToggleStateRepository {

    default void init(Collection<ToggleState> initStates) {
        initStates.forEach(this::set);
    }

    List<Toggle> getToggles();

    Optional<ToggleState> get(Toggle toggle);

    void set(ToggleState toggleState);

}
```

At the end, provide above implementations to `ToggleManagerBuilder`.

```java
@Configuration
public class ToggleConfig {

    @Bean
    public ToggleManager toggleManager() {
        return ToggleManagerBuilder.builder()
            .initToggleStateProvider(initToggleStateProviderImpl)
            .toggleStateRepository(toggleStateRepositoryImpl)
            .build();
    }
    
}
```

## Code examples

### Check if toggle is enabled
```java
toggleManager.isEnabled(AppToggle.TOGGLE_1);
```

### Check if toggle is disabled
```java
toggleManager.isDisabled(AppToggle.TOGGLE_1);
```

### Enable toggle
```java
toggleManager.enable(AppToggle.TOGGLE_1);
```

### Disable toggle
```java
toggleManager.disable(AppToggle.TOGGLE_1);
```

### Get all stored toggles
```java
toggleManager.getToggles();
```
