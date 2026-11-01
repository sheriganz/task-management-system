package taskmanagement.task;

import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequest(
        @NotNull TaskStatus status
) {
}
