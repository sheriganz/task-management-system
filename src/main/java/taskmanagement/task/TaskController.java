package taskmanagement.task;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @PostMapping("/api/tasks")
    public TaskResponse createTask(@RequestBody @Valid CreateTaskRequest createTaskRequest, Authentication authentication) {
        return taskService.createTask(createTaskRequest, authentication.getName());
    }

    @GetMapping("/api/tasks")
    public List<TaskListResponse> getTasks(@RequestParam(required = false) String author, @RequestParam(required = false) String assignee) {
        return taskService.getAllTasks(author, assignee);
    }

    @PutMapping("/api/tasks/{id}/assign")
    public TaskResponse assignTask(@PathVariable Long id, @RequestBody @Valid AssignTaskRequest assignTaskRequest, Authentication authentication) {
        return taskService.assignTaskToUser(id,assignTaskRequest,authentication.getName());
    }

    @PutMapping("/api/tasks/{id}/status")
    public TaskResponse changeTaskStatus(@PathVariable Long id, @RequestBody @Valid ChangeStatusRequest changeStatusRequest, Authentication authentication) {
        return taskService.changeTaskStatus(id,changeStatusRequest,authentication.getName());
    }

    @PostMapping("/api/tasks/{id}/comments")
    public CommentResponse addComment(@PathVariable Long id, @RequestBody @Valid CreateCommentRequest createCommentRequest, Authentication authentication) {
        return taskService.addComment(id,createCommentRequest,authentication.getName());
    }

    @GetMapping("/api/tasks/{id}/comments")
    public List<CommentResponse> getTaskComments(@PathVariable Long id) {
        return taskService.getTaskComments(id);
    }
}
