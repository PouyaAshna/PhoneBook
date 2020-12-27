package ir.snapp.phonebook.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ir.snapp.phonebook.repository.jpa")
public class DatabaseConfiguration {
}
