package com.worknest.app.controller;

import com.worknest.app.model.*;
import com.worknest.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private AdminService adminService;

    @PostMapping("/users")
    public User createUser(@RequestBody User user) { return adminService.createUser(user); }

    @GetMapping("/users")
    public List<User> getAllUsers() { return adminService.getAllUsers(); }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) { adminService.deleteUser(id); return ResponseEntity.ok().build(); }
    
    @PostMapping("/projects")
    public Project createProject(@RequestBody Project project) { return adminService.createProject(project); }

    @GetMapping("/projects")
    public List<Project> getAllProjects() { return adminService.getAllProjects(); }

    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) { return ResponseEntity.ok(adminService.updateTaskStatus(taskId, status)); }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) { adminService.deleteComment(commentId); return ResponseEntity.ok().build(); }
    
    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestParam List<Long> userIds, @RequestParam Long projectId) { return ResponseEntity.ok(adminService.createTask(task, userIds, projectId)); }
    
    @GetMapping("/tasks")
    public List<Task> getAllTasks() { return adminService.getAllTasks(); }
}