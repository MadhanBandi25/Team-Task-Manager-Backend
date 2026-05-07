package com.taskmanager.teamtaskmanager.service;

import com.taskmanager.teamtaskmanager.dto.request.LoginRequest;
import com.taskmanager.teamtaskmanager.dto.request.SignupRequest;
import com.taskmanager.teamtaskmanager.dto.response.AuthResponse;
import com.taskmanager.teamtaskmanager.dto.response.UserResponse;

public interface AuthService {

    UserResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}
