package example2.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "issue_created")
public class IssueEntity {
    @Id
    @Column(name = "issue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issueId;

    @Column(name = "issue_key")
    private String issueKey;

    @Column(name = "issue_type")
    private String issueType;

    @Column(name = "project_key")
    private String projectKey;

    @Column(name = "creator")
    private String creator;

    @Column(name = "hash")
    private String hash;
}

