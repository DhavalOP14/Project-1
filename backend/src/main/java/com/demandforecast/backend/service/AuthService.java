package com.demandforecast.backend.service;

import com.demandforecast.backend.dto.auth.AuthResponse;
import com.demandforecast.backend.dto.auth.LoginRequest;
import com.demandforecast.backend.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}
