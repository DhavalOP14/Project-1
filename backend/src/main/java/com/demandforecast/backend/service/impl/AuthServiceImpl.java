package com.demandforecast.backend.service.impl;

import com.demandforecast.backend.constant.RoleConstants;
import com.demandforecast.backend.dto.auth.AuthResponse;
import com.demandforecast.backend.dto.auth.LoginRequest;
import com.demandforecast.backend.dto.auth.RegisterRequest;
import com.demandforecast.backend.entity.Role;
import com.demandforecast.backend.entity.User;
import com.demandforecast.backend.exception.DuplicateResourceException;
import com.demandforecast.backend.exception.ResourceNotFoundException;
import com.demandforecast.backend.repository.RoleRepository;
import com.demandforecast.backend.repository.UserRepository;
import com.demandforecast.backend.security.CustomUserDetails;
import com.demandforecast.backend.security.JwtService;
import com.demandforecast.backend.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .or(() -> userRepository.findByEmail(request.getUsername()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUsername()));

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().getName())
                .build();
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username is already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already in use: " + request.getEmail());
        }

        String targetRole = (request.getRoleName() != null && !request.getRoleName().isBlank())
                ? request.getRoleName()
                : RoleConstants.ROLE_INVENTORY_MANAGER;

        Role role = roleRepository.findByName(targetRole)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + targetRole));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(role)
                .isActive(true)
                .build();

        user = userRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().getName())
                .build();
    }
}
