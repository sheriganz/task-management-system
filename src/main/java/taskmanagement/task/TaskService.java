package taskmanagement.task;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taskmanagement.account.AppUserRepository;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;
    private final CommentRepository commentRepository;
    private final BasicErrorController basicErrorController;

    public TaskService(TaskRepository taskRepository, AppUserRepository appUserRepository, CommentRepository commentRepository, BasicErrorController basicErrorController) {
        this.taskRepository = taskRepository;
        this.appUserRepository = appUserRepository;
        this.commentRepository = commentRepository;
        this.basicErrorController = basicErrorController;
    }

    public TaskResponse createTask(CreateTaskRequest createTaskRequest, String email) {
        Task task = new Task(createTaskRequest.title(), createTaskRequest.description(), email);
        return TaskResponse.from(taskRepository.save(task));
    }

    private Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public TaskResponse assignTaskToUser(Long taskId, AssignTaskRequest assignTaskRequest, String currentUser) {
        Task task = findTaskById(taskId);
        if (!currentUser.equals(task.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        String assignee = assignTaskRequest.assignee().toLowerCase(Locale.ROOT);
        if (assignee.equals("none")) {
            task.setAssignee(null);
        } else if (!appUserRepository.existsByEmail(assignee)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            task.setAssignee(assignee);
        }
        return TaskResponse.from(taskRepository.save(task));
    }

    public TaskResponse changeTaskStatus(Long taskId, ChangeStatusRequest changeStatusRequest, String currentUser) {
        Task task = findTaskById(taskId);
        if (!currentUser.equals(task.getAuthor()) && !currentUser.equals(task.getAssignee())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        task.setStatus(changeStatusRequest.status());
        return TaskResponse.from(taskRepository.save(task));
    }

    public List<TaskListResponse> getAllTasks(String author, String assignee) {
        List<Task> tasks;
        if (hasText(author) && hasText(assignee)) {
            tasks = taskRepository.findAllByAssigneeAndAuthorOrderByIdDesc(
                    assignee.toLowerCase(Locale.ROOT), author.toLowerCase(Locale.ROOT));
        } else if (hasText(author)) {
            tasks = taskRepository.findAllByAuthorOrderByIdDesc(author.toLowerCase(Locale.ROOT));
        } else if (hasText(assignee)) {
            tasks = taskRepository.findAllByAssigneeOrderByIdDesc(assignee.toLowerCase(Locale.ROOT));
        } else {
            tasks = taskRepository.findAllByOrderByIdDesc();
        }
        if (tasks.isEmpty()) return List.of();

        List<Long> ids = tasks.stream().map(Task::getId).toList();

        Map<Long,Long> counts = commentRepository.countByTasksIds(ids).stream()
                .collect(Collectors.toMap(CommentCount::taskId, CommentCount::count));

        return tasks.stream()
                .map(task -> {
                    TaskResponse base =  TaskResponse.from(task);
                    long total = counts.getOrDefault(task.getId(), 0L);
                    return TaskListResponse.from(base, total);
                })
                .toList();
    }

    public CommentResponse addComment(Long taskId, CreateCommentRequest createCommentRequest, String currentUser) {
        findTaskById(taskId);
        Comment comment = new Comment(taskId, createCommentRequest.text(), currentUser);
        return CommentResponse.from(commentRepository.save(comment));
    }

    public List<CommentResponse> getTaskComments(Long taskId){
        findTaskById(taskId);
        return commentRepository.findAllByTaskIdOrderByIdDesc(taskId)
                .stream().map(CommentResponse::from).toList();
    }

    private boolean hasText(String s) {
        return s != null && !s.isBlank();
    }
}
