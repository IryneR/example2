package example2.services;

import example2.dao.IssueEntity;
import example2.dto.IssueDto;
import example2.model.JiraHash;
import example2.repositories.IssueRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Log
@Service
@Transactional
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;

    @Value("${application.hash-service-url}")
    private String hashServiceUrl;

    @Transactional
    public int createIssue(IssueDto issueDto) {
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setIssueKey(issueDto.getIssueKey());
        issueEntity.setIssueType(issueDto.getIssueTypeName());
        issueEntity.setProjectKey(issueDto.getProjectKey());
        issueEntity.setCreator(issueDto.getCreatorDisplayName());

        log.info("main thread : " + Thread.currentThread().getId());

        issueEntity = issueRepository.saveAndFlush(issueEntity);

        int issueID = issueEntity.getIssueId();
        log.info("issueID : " + issueID);

        return issueID;
    }

    @Async("threadPoolExecutor")
    public void updateIssue(IssueDto issueDto, int issueId) {
        log.info("executor for issue : " + issueId + " thread : " + Thread.currentThread().getId());
        Optional<IssueEntity> optionalIssueEntity = issueRepository.findById(issueId);
        optionalIssueEntity
                .ifPresent(entity -> {
                    entity.setHash(getHashForIssue(issueDto));
                    log.info("gethash = " + entity.getHash());
                    issueRepository.save(entity);
                });
    }

    public String getHashForIssue(IssueDto issueDto) {
        RestTemplate restTemplate = new RestTemplate();
        String hashUrl = hashServiceUrl;
        JiraHash response = restTemplate.postForObject(hashUrl, issueDto, JiraHash.class);
        return response.getHash();
    }
}