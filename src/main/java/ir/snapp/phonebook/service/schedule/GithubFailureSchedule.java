package ir.snapp.phonebook.service.schedule;

import ir.snapp.phonebook.domain.mongo.GithubFailureEntity;
import ir.snapp.phonebook.repository.mongo.GithubFailureRepository;
import ir.snapp.phonebook.service.ContactGithubService;
import ir.snapp.phonebook.service.GithubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubFailureSchedule {

    private final GithubFailureRepository githubFailureRepository;
    private final GithubService githubService;
    private final ContactGithubService contactGithubService;

    @Scheduled(fixedRate = 300000)
    private void schedule() {
        log.debug("{} : Start Schedule for githubFailure", this.getClass().getCanonicalName());
        this.schedulePageable(PageRequest.of(0, 20));
    }

    private void schedulePageable(Pageable pageable) {
        Page<GithubFailureEntity> githubFailures = this.githubFailureRepository.findAll(pageable);
        githubFailures.forEach(githubFailureEntity -> {
            this.githubService.findAll(
                    githubFailureEntity.getRepositoryName(),
                    githubFailureEntity.getContactId(),
                    PageRequest.of(githubFailureEntity.getPageNumber(), githubFailureEntity.getPageSize()),
                    githubRepositoryNames -> {
                        this.contactGithubService.saveAllRepositories(githubRepositoryNames, githubFailureEntity.getContactId());
                    }
            );
            this.githubFailureRepository.delete(githubFailureEntity);
        });
        if (!githubFailures.isLast()) {
            this.schedulePageable(githubFailures.nextPageable());
        }
    }
}
