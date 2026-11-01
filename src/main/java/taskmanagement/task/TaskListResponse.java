package taskmanagement.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TaskListResponse(
        String id,
        String title,
        String description,
        String status,
        String author,
        String assignee,
        @JsonProperty("total_comments") long totalComments
) {
    static TaskListResponse from(TaskResponse taskResponse, long totalComments) {
        return new TaskListResponse(
                taskResponse.id(),
                taskResponse.title(),
                taskResponse.description(),
                taskResponse.status(),
                taskResponse.author(),
                taskResponse.assignee(),
                totalComments
        );
    }
}
