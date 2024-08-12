package org.taskSystem.jwt.controller.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter
@Setter
public class UserRegistrationRequest
{
    @Email(message = "Invalid email format")
    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
}
