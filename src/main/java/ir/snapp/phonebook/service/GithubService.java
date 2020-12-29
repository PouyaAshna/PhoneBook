package ir.snapp.phonebook.service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Consumer;

public interface GithubService {

    void findAll(String githubUsername, Long contactId, Pageable pageable, Consumer<List<String>> githubResponseConsumer);
}
