package example2.services;

import example2.dao.IssueEntity;
import example2.dto.IssueDto;
import example2.model.JiraHash;
import example2.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Transactional
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;

    @Value("${application.hash-service-url}")
    private String hashServiceUrl;

    public int createIssue(IssueDto issueDto) {
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setIssueKey(issueDto.getIssueKey());
        issueEntity.setIssueType(issueDto.getIssueTypeName());
        issueEntity.setProjectKey(issueDto.getProjectKey());
        issueEntity.setCreator(issueDto.getCreatorDisplayName());
        //issueEntity.setHash(getHash());
        issueEntity.setHash(getHashForIssue(issueDto));

        issueEntity = issueRepository.save(issueEntity);
        return issueEntity.getIssueId();
    }

    public String getHash() {
        RestTemplate restTemplate = new RestTemplate();
        String hashUrl
                = hashServiceUrl;
        ResponseEntity<JiraHash> response
                = restTemplate.getForEntity(hashUrl, JiraHash.class);
        return response.getBody().getHash();
    }

    public String getHashForIssue(IssueDto issueDto) {
        RestTemplate restTemplate = new RestTemplate();
        String hashUrl = hashServiceUrl;
        JiraHash response = restTemplate.postForObject(hashUrl, issueDto, JiraHash.class);
        return response.getHash();
    }


}