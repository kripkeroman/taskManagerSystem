package org.taskSystem.jwt.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.taskSystem.jwt.entity.Task;
import org.taskSystem.jwt.repository.TaskRepository;
import org.taskSystem.jwt.entity.User;
import org.taskSystem.jwt.repository.UserRepository;
import org.taskSystem.jwt.exception.ResourceNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService
{
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(String title, String description, String status, String priority, UUID authorId, UUID assigneeId)
    {
        User author = userRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        User assignee = userRepository.findById(assigneeId).orElseThrow(() -> new ResourceNotFoundException("Assignee not found"));

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setAuthor(author);
        task.setAssignee(assignee);

        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long taskId, String title, String description, String status, String priority, UUID assigneeId) {
        return Optional.ofNullable(taskRepository.findById(taskId).map(task -> {
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(status);
            task.setPriority(priority);
            User assignee = userRepository.findById(assigneeId).orElseThrow(() -> new ResourceNotFoundException("Assignee not found"));
            task.setAssignee(assignee);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("Task not found")));
    }

    public Page<Task> getTasksByAuthor(UUID authorId, Pageable pageable)
    {
        return taskRepository.findByAuthorId(authorId, pageable);
    }

    public Page<Task> getTasksByAssignee(UUID assigneeId, Pageable pageable)
    {
        return taskRepository.findByAssigneeId(assigneeId, pageable);
    }

    public Page<Task> getAllTasks(Pageable pageable)
    {
        return taskRepository.findAll(pageable);
    }

    public Optional<Task> getTaskById(Long taskId)
    {
        return Optional.ofNullable(taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found")));
    }
}
