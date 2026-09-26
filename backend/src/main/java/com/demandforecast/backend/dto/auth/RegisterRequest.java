package com.demandforecast.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String roleName;

    public RegisterRequest() {}

    public RegisterRequest(String username, String email, String password, String firstName, String lastName, String roleName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleName = roleName;
    }

    public static RegisterRequestBuilder builder() { return new RegisterRequestBuilder(); }

    public static class RegisterRequestBuilder {
        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String roleName;

        public RegisterRequestBuilder username(String username) { this.username = username; return this; }
        public RegisterRequestBuilder email(String email) { this.email = email; return this; }
        public RegisterRequestBuilder password(String password) { this.password = password; return this; }
        public RegisterRequestBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public RegisterRequestBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public RegisterRequestBuilder roleName(String roleName) { this.roleName = roleName; return this; }
        public RegisterRequest build() { return new RegisterRequest(username, email, password, firstName, lastName, roleName); }
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
