package org.taskSystem.jwt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.taskSystem.jwt.entity.Task;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long>
{
    Page<Task> findByAuthorId(UUID authorId, Pageable pageable);
    Page<Task> findByAssigneeId(UUID assigneeId, Pageable pageable);
}
