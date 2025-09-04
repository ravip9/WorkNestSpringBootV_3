package com.worknest.app.repository;

import com.worknest.app.model.Task;
import com.worknest.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUsersContains(User user);
}