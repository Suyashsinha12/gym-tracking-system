package com.gym.gymtrackingsystem.controller;

import com.gym.gymtrackingsystem.dto.DashboardResponse;
import com.gym.gymtrackingsystem.entity.User;
import com.gym.gymtrackingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login/{name}/{password}")
    public String LoginUser(@PathVariable String name, @PathVariable String password){
        return userService.login(name,password);
    }

    @PostMapping("/punchIn/{name}")
    public String punchInWorkout(@PathVariable String name){
        return userService.punchIn(name);
    }

    @GetMapping("/dashboard/{name}")
    public DashboardResponse dashboard(@PathVariable String name){
        return userService.dashboard(name);
    }
}
