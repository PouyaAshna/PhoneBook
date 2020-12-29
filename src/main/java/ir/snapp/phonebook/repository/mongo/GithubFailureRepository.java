package ir.snapp.phonebook.repository.mongo;

import ir.snapp.phonebook.domain.mongo.GithubFailureEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubFailureRepository extends MongoRepository<GithubFailureEntity, Long> {
}
