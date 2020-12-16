package example2.services;

import example2.dao.IssueEntity;
import example2.dto.IssueDto;
import example2.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;

    public int createIssue(IssueDto issueDto) {
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setIssueKey(issueDto.getIssueKey());
        issueEntity.setIssueType(issueDto.getIssueTypeName());
        issueEntity.setProjectKey(issueDto.getProjectKey());
        issueEntity.setCreator(issueDto.getCreatorDisplayName());

        issueEntity = issueRepository.save(issueEntity);
        return issueEntity.getIssueId();

    }
}