package ir.snapp.phonebook.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = "ir.snapp.phonebook.configuration.properties")
public class PropertiesConfiguration {
}
