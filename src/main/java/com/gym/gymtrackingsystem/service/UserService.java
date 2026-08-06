package com.gym.gymtrackingsystem.service;

import com.gym.gymtrackingsystem.dto.DashboardResponse;
import com.gym.gymtrackingsystem.entity.User;
import com.gym.gymtrackingsystem.entity.Workout;
import com.gym.gymtrackingsystem.repository.UserRepository;
import com.gym.gymtrackingsystem.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    public User registerUser(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(user);
    }

    public String login(String name, String password) {
        User user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            return "Invalid password!";
        }
        return "Successfully Logged In";
    }

    public String punchIn(String name) {
        User user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();

        if (workoutRepository.existsByUserAndWorkoutDate(user, today)) {
            return "Workout already punched for today";
        }

        Workout workout = new Workout();
        workout.setWorkoutDate(today);
        workout.setUser(user);
        workoutRepository.save(workout);
        return "Workout punched successfully";
    }

    public DashboardResponse dashboard(String name) {

        User user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("User not found!"));

        List<Workout> workouts = workoutRepository.findWorkoutByUser(user);

        LocalDate startOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        long totalWorkoutDays = workoutRepository.countByUserAndWorkoutDateBetween(user, startOfYear, endOfYear);

        workouts.sort(Comparator.comparing(Workout::getWorkoutDate).reversed());

        int streak = 0;

        if (!workouts.isEmpty()) {

            LocalDate expectedDate;
            LocalDate latestWorkoutDay = workouts.get(0).getWorkoutDate();

            if (latestWorkoutDay.equals(LocalDate.now())) {
                expectedDate = LocalDate.now();
            } else {
                expectedDate = LocalDate.now().minusDays(1);
            }

            for (Workout workout : workouts) {
                if (workout.getWorkoutDate().equals(expectedDate)) {
                    streak++;
                    expectedDate = expectedDate.minusDays(1);
                } else {
                    break;
                }
            }
        }
        return DashboardResponse.builder().totalWorkoutDays(totalWorkoutDays).streak(streak).build();
    }
}
