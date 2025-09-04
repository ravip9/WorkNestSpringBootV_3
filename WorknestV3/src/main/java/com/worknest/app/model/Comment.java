package com.worknest.app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "task")
@EqualsAndHashCode(exclude = "task")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    
    @ManyToOne
    private Task task;
    
    @ManyToOne
    private User author;
}