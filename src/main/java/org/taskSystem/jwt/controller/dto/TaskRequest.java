package org.taskSystem.jwt.controller.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
public class TaskRequest
{
    @NotEmpty(message = "Title is required")
    private String title;
    @NotEmpty(message = "Description is required")
    private String description;
    @NotEmpty(message = "Status is required")
    private String status;
    @NotEmpty(message = "Priority is required")
    private String priority;
    @NotNull(message = "Assignee ID is required")
    private UUID assigneeId;
}
