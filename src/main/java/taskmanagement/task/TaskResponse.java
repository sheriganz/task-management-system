package taskmanagement.task;

public record TaskResponse(
         String id,
         String title,
         String description,
         String status,
         String author,
         String assignee
        ) {
    static TaskResponse from(Task task) {
        return new TaskResponse(
                String.valueOf(task.getId()),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getAuthor(),
                task.getAssignee() == null ? "none" : task.getAssignee()
                );
    }
}
