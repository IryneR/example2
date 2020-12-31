package example2.repositories;

import example2.dao.IssueEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends CrudRepository<IssueEntity, Integer> {
}
