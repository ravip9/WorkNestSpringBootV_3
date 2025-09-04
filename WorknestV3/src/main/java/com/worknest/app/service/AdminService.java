package com.worknest.app.service;

import com.worknest.app.model.Project;
import com.worknest.app.model.Task;
import com.worknest.app.model.TaskStatus;
import com.worknest.app.model.User;
import java.util.List;

public interface AdminService {
    User createUser(User user);
    List<User> getAllUsers();
    void deleteUser(Long id);
    Project createProject(Project project);
    List<Project> getAllProjects();
    Task createTask(Task task, List<Long> userIds, Long projectId);
    List<Task> getAllTasks();
    Task forwardTask(Long taskId, Long newUserId);
    void deleteComment(Long commentId);
    Task updateTaskStatus(Long taskId, TaskStatus status);
}