package com.gym.gymtrackingsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LocalDate workoutDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
