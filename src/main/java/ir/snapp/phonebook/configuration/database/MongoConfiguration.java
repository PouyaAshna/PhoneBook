package ir.snapp.phonebook.configuration.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ir.snapp.phonebook.repository.mongo")
public class MongoConfiguration {
}
