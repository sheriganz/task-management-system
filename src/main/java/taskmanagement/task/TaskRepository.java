package taskmanagement.task;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrderByIdDesc();
    List<Task> findAllByAuthorOrderByIdDesc(String author);
    List<Task> findAllByAssigneeOrderByIdDesc(String assignee);
    List<Task> findAllByAssigneeAndAuthorOrderByIdDesc(String assignee, String author);
}
