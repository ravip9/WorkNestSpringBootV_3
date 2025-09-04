package com.worknest.app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"project", "assignedUsers", "comments"})
@ToString(exclude = {"project", "assignedUsers", "comments"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    
    @ManyToOne
    private Project project;
    
    @ManyToMany
    @JoinTable(
        name = "task_users",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> assignedUsers;
    
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments;
}