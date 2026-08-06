package com.gym.gymtrackingsystem.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DashboardResponse {

    private long totalWorkoutDays;
    private int streak;
}
