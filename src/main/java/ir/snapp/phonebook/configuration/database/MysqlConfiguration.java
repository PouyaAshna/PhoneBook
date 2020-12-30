package ir.snapp.phonebook.configuration.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This class define mysql repository path
 *
 * @author Pouya Ashna
 */
@Configuration
@EnableJpaRepositories(basePackages = "ir.snapp.phonebook.repository.jpa")
public class MysqlConfiguration {
}
