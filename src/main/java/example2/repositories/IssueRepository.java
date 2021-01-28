package example2.repositories;

import example2.dao.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Integer> {
}
