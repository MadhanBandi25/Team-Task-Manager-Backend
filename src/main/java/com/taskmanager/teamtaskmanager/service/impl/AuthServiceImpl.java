package com.taskmanager.teamtaskmanager.service.impl;

import com.taskmanager.teamtaskmanager.config.JwtUtil;
import com.taskmanager.teamtaskmanager.dto.request.LoginRequest;
import com.taskmanager.teamtaskmanager.dto.request.SignupRequest;
import com.taskmanager.teamtaskmanager.dto.response.AuthResponse;
import com.taskmanager.teamtaskmanager.dto.response.UserResponse;
import com.taskmanager.teamtaskmanager.entity.User;
import com.taskmanager.teamtaskmanager.exception.DuplicateResourceException;
import com.taskmanager.teamtaskmanager.exception.UnauthorizedException;
import com.taskmanager.teamtaskmanager.mapper.UserMapper;
import com.taskmanager.teamtaskmanager.repository.UserRepository;
import com.taskmanager.teamtaskmanager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public UserResponse signup(SignupRequest request) {

        if (userRepository.existsByEmailAndIsDeletedFalse(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
