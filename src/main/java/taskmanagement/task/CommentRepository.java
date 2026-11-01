package taskmanagement.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTaskIdOrderByIdDesc(Long taskId);

    @Query("""
        select new taskmanagement.task.CommentCount(c.taskId, count(c))
        from Comment c
        where c.taskId in :taskIds
        group by c.taskId
        """)
    List<CommentCount> countByTasksIds(@Param("taskIds")Collection<Long> taskIds);
}
