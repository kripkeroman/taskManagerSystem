package org.taskSystem.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.taskSystem.jwt.controller.dto.TaskRequest;
import org.taskSystem.jwt.security.UserDetailsImpl;
import org.taskSystem.jwt.entity.Task;
import org.taskSystem.jwt.service.task.TaskService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@Validated
@Tag(name = "Tasks", description = "Tasks Management API")
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @Operation(summary = "Create a new task")
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request, Authentication authentication) {
        UUID authorId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        Task task = taskService.createTask(request.getTitle(), request.getDescription(), request.getStatus(), request.getPriority(), authorId, request.getAssigneeId());
        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Update an existing task")
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequest request) {
        Optional<Task> updatedTask = taskService.updateTask(taskId, request.getTitle(), request.getDescription(), request.getStatus(), request.getPriority(), request.getAssigneeId());
        return updatedTask.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all tasks")
    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.getAllTasks(pageable);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Get tasks by author ID")
    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<Task>> getTasksByAuthor(@PathVariable UUID authorId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.getTasksByAuthor(authorId, pageable);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Get tasks by assignee ID")
    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<Page<Task>> getTasksByAssignee(@PathVariable UUID assigneeId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.getTasksByAssignee(assigneeId, pageable);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Get task by ID")
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
