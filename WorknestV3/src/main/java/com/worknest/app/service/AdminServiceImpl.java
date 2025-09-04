package com.worknest.app.service;

import com.worknest.app.model.*;
import com.worknest.app.repository.CommentRepository;
import com.worknest.app.repository.ProjectRepository;
import com.worknest.app.repository.TaskRepository;
import com.worknest.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired private UserRepository userRepository;
    @Autowired private TaskRepository taskRepository;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode("password")); // Default password for admin-created users
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @Override
    public void deleteUser(Long id) { userRepository.deleteById(id); }

    @Override
    public Project createProject(Project project) { return projectRepository.save(project); }

    @Override
    public List<Project> getAllProjects() { return projectRepository.findAll(); }

    @Override
    public Task createTask(Task task, List<Long> userIds, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        List<User> usersToAssign = userRepository.findAllById(userIds);
        task.setAssignedUsers(new HashSet<>(usersToAssign));
        for (User user : usersToAssign) {
            if (user.getTasks() == null) user.setTasks(new HashSet<>());
            user.getTasks().add(task);
        }
        task.setProject(project);
        task.setStatus(TaskStatus.PENDING);
        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())) {
            task.setStatus(TaskStatus.DELAYED);
        }
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() { return taskRepository.findAll(); }

    @Override
    public Task forwardTask(Long taskId, Long newUserId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User newUser = userRepository.findById(newUserId).orElseThrow(() -> new RuntimeException("User not found"));
        task.getAssignedUsers().add(newUser);
        if (newUser.getTasks() == null) newUser.setTasks(new HashSet<>());
        newUser.getTasks().add(task);
        return taskRepository.save(task);
    }

    @Override
    public void deleteComment(Long commentId) { commentRepository.deleteById(commentId); }

    @Override
    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }
}