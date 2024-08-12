package org.taskSystem.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.taskSystem.jwt.controller.dto.CommentRequest;
import org.taskSystem.jwt.security.UserDetailsImpl;
import org.taskSystem.jwt.entity.Comment;
import org.taskSystem.jwt.service.comment.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@Validated
@Tag(name = "Comments", description = "Comments Management API")
public class CommentController
{
    @Autowired
    private CommentService commentService;

    @Operation(summary = "Add a comment to a task")
    @PostMapping
    public ResponseEntity<Comment> addComment(@Valid @RequestBody CommentRequest request, Authentication authentication) {
        UUID authorId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        Comment comment = commentService.addComment(request.getTaskId(), authorId, request.getContent());
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Get comments by task ID")
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Comment>> getCommentsByTask(@PathVariable Long taskId) {
        List<Comment> comments = commentService.getCommentsByTask(taskId);
        return ResponseEntity.ok(comments);
    }
}
