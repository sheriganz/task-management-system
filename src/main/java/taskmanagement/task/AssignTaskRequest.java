package taskmanagement.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AssignTaskRequest(
        @NotBlank @Pattern(regexp = "none|\\S+@\\S+\\.\\S+") String assignee
        ) {
}
