package com.worknest.app.service;

import com.worknest.app.model.*;
import com.worknest.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommentRepository commentRepository;

    @Override
    public List<Task> getMyTasks(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByAssignedUsersContainsAndIsClosedFalse(user);
    }

    @Override
    public Task updateTaskStatus(Long taskId, TaskStatus status, String username) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        // Basic check to see if user is assigned to this task
        boolean isAssigned = task.getAssignedUsers().stream().anyMatch(u -> u.getUsername().equals(username));
        if (!isAssigned) throw new RuntimeException("User not authorized for this task");
        
        if (status == TaskStatus.IN_PROGRESS || status == TaskStatus.COMPLETED) {
            task.setStatus(status);
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("Invalid status update for user");
    }

    @Override
    public Comment addComment(Long taskId, Comment comment, String username) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        comment.setTask(task);
        comment.setAuthor(user);
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public Task forwardTask(Long taskId, Long newUserId, String currentUsername) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User newUser = userRepository.findById(newUserId).orElseThrow(() -> new RuntimeException("User not found"));
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Current user not found"));

        if (!task.getAssignedUsers().contains(currentUser)) {
            throw new RuntimeException("User not assigned to this task, cannot forward.");
        }
        
        task.getAssignedUsers().add(newUser);
        return taskRepository.save(task);
    }
}
