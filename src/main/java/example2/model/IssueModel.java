package example2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueModel {
    private Integer issueId;
    private String issueKey;
    private String issueType;
    private String projectKey;
    private String creator;
    private String hash;

}
