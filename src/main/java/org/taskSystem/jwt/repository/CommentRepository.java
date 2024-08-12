package org.taskSystem.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taskSystem.jwt.entity.Comment;
import org.taskSystem.jwt.entity.Task;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>
{
    List<Comment> findByTask(Task task);
}
