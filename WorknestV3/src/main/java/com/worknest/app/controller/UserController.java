package com.worknest.app.controller;

import com.worknest.app.model.*;
import com.worknest.app.repository.*;
import com.worknest.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private AdminService adminService;

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getMyTasks(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(taskRepository.findByAssignedUsersContains(user));
    }

    @PutMapping("/tasks/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        if (status == TaskStatus.IN_PROGRESS || status == TaskStatus.COMPLETED) {
             task.setStatus(status);
            return ResponseEntity.ok(taskRepository.save(task));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/tasks/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody Comment comment, @AuthenticationPrincipal UserDetails userDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        comment.setTask(task);
        comment.setAuthor(user);
        comment.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(commentRepository.save(comment));
    }

    @PutMapping("/tasks/{taskId}/forward")
    public ResponseEntity<Task> forwardTask(@PathVariable Long taskId, @RequestParam Long newUserId) {
        return ResponseEntity.ok(adminService.forwardTask(taskId, newUserId));
    }
}