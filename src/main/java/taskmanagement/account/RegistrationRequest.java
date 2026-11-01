package taskmanagement.account;

import jakarta.validation.constraints.*;

public record RegistrationRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password) {}
