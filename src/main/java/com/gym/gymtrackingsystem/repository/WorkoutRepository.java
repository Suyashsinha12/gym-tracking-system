package com.gym.gymtrackingsystem.repository;

import com.gym.gymtrackingsystem.entity.User;
import com.gym.gymtrackingsystem.entity.Workout;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout,Long> {

    List<Workout> findWorkoutByUser(User user);

    boolean existsByUserAndWorkoutDate(User user, LocalDate workoutDate);

    long countByUserAndWorkoutDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
