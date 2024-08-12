package org.taskSystem.jwt.controller.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
public class CommentRequest {
    @NotNull(message = "ID is required")
    private UUID id;
    @NotNull(message = "TaskId is required")
    private Long taskId;
    @NotEmpty(message = "Content is required")
    private String content;
}
