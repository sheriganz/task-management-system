package taskmanagement.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentResponse(
        String id,
        @JsonProperty("task_id")
        String taskId,
        String text,
        String author
) {
    static CommentResponse from(Comment comment) {
        return new CommentResponse(
                String.valueOf(comment.getId()),
                String.valueOf(comment.getTaskId()),
                comment.getText(),
                comment.getAuthor()
        );
    }
}
