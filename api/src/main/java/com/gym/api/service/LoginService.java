package com.gym.api.service;

import com.gym.api.dto.request.LoginRequestDTO;

import java.util.List;

public interface LoginService {
    LoginRequestDTO Login(LoginRequestDTO request);
}
