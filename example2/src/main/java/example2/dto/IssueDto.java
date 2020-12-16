package example2.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.Map;

@ToString
@JsonAutoDetect
public class IssueDto {
    private String issueKey;
    private String issueType;
    private String projectKey;
    private String creator;

    public String getIssueKey() {
        return issueKey;
    }

    @JsonProperty("key")
    public void setIssueKey(String key) {
        this.issueKey = key;
    }

    public String getIssueTypeName() {
        return issueType;
    }

    @JsonProperty("fields")
    public void setIssueFields(Map<String, Object> fields) {
        Map<String, Object> issueType = (Map<String, Object>) fields.get("issuetype");
        this.issueType = (String) issueType.get("name");
        Map<String, Object> project = (Map<String, Object>) fields.get("project");
        this.projectKey = (String) project.get("name");
        Map<String, Object> creator = (Map<String, Object>) fields.get("creator");
        this.creator = (String) creator.get("displayName");
    }

    public String getProjectKey() {
        return projectKey;
    }

    public String getCreatorDisplayName() {
        return creator;
    }

}

