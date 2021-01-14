package example2.services;

import example2.dao.IssueEntity;
import example2.dto.IssueDto;
import example2.model.JiraHash;
import example2.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@Transactional
public class IssueService{
    @Autowired
    private IssueRepository issueRepository;

    @Value("${application.hash-service-url}")
    private String hashServiceUrl;

    //private ThreadPoolExecutor executor;
    private ExecutorService executor;

    @Autowired
    private EntityManager entityManager;

    @PostConstruct
    public void afterCreated() {
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    @Transactional
    public int createIssue(IssueDto issueDto) {
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setIssueKey(issueDto.getIssueKey());
        issueEntity.setIssueType(issueDto.getIssueTypeName());
        issueEntity.setProjectKey(issueDto.getProjectKey());
        issueEntity.setCreator(issueDto.getCreatorDisplayName());

        System.out.println("main thread " + Thread.currentThread().getId());

        issueEntity = issueRepository.save(issueEntity);

        entityManager.flush();

        int issueID = issueEntity.getIssueId();
        System.out.println("issueID " + issueID);

        return issueEntity.getIssueId();
    }

    @Async("threadPoolExecutor")
    public void updateIssue(IssueDto issueDto, int issueId) {
        executor.execute(() -> {
            System.out.println("executor for "+ issueId +" thread " + Thread.currentThread().getId());
            Optional<IssueEntity> optionalIssueEntity = issueRepository.findById(issueId);
            System.out.println("executor optionalIssueEntity "+ optionalIssueEntity.isPresent());
            optionalIssueEntity
                    .ifPresent(entity -> {

                        System.out.println("executor thread " + Thread.currentThread().getId());

                        entity.setHash(getHashForIssue(issueDto));
                        System.out.println("executor gethash " + entity.getHash());
                        issueRepository.save(entity);
                    });
        });

    }

    public void saveIssue(IssueDto issueDto) {
      int issueId =  createIssue(issueDto);
        updateIssue(issueDto,issueId);

    }

    public String getHashForIssue(IssueDto issueDto) {
        RestTemplate restTemplate = new RestTemplate();
        String hashUrl = hashServiceUrl;
        JiraHash response = restTemplate.postForObject(hashUrl, issueDto, JiraHash.class);
        return response.getHash();
    }


}