package org.taskSystem.jwt.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taskSystem.jwt.entity.Comment;
import org.taskSystem.jwt.repository.CommentRepository;
import org.taskSystem.jwt.entity.Task;
import org.taskSystem.jwt.repository.TaskRepository;
import org.taskSystem.jwt.entity.User;
import org.taskSystem.jwt.repository.UserRepository;
import org.taskSystem.jwt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService
{
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment addComment(Long taskId, UUID authorId, String content)
    {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        User author = userRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        Comment comment = new Comment();
        comment.setTask(task);
        comment.setAuthor(author);
        comment.setContent(content);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByTask(Long taskId)
    {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return commentRepository.findByTask(task);
    }
}
