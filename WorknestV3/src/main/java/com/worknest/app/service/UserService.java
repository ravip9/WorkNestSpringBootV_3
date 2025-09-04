package com.worknest.app.service;

import com.worknest.app.model.Comment;
import com.worknest.app.model.Task;
import com.worknest.app.model.TaskStatus;
import java.util.List;

public interface UserService {
    List<Task> getMyTasks(String username);
    Task updateTaskStatus(Long taskId, TaskStatus status, String username);
    Comment addComment(Long taskId, Comment comment, String username);
    Task forwardTask(Long taskId, Long newUserId, String currentUsername);
}
