package com.mdx.xyphose.usermgmt.service;

import com.mdx.xyphose.usermgmt.entity.User;
import com.mdx.xyphose.usermgmt.entity.dto.ForgetPasswordRequest;
import com.mdx.xyphose.usermgmt.entity.dto.LoginRequest;
import com.mdx.xyphose.usermgmt.entity.dto.LoginResponse;
import com.mdx.xyphose.usermgmt.entity.dto.ResetPasswordRequest;
import com.mdx.xyphose.usermgmt.repo.UserRepo;
import com.mdx.xyphose.usermgmt.service.JwtService;
import com.mdx.xyphose.usermgmt.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginServiceImpl(UserRepo userRepo, JwtService jwtService,
                            AuthenticationManager authenticationManager,
                            PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(jwtToken);
        loginResponse.setUserName(user.getFirstName());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setRefreshToken(null);

        return ResponseEntity.ok(loginResponse);
    }

    @Override
    public ResponseEntity<?> forgetPassword(ForgetPasswordRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getGender().equalsIgnoreCase(request.getGender())) {
            return ResponseEntity.status(401).body("Invalid gender for this email.");
        }

        return ResponseEntity.ok("Verification successful.");
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepo.save(user);

        return ResponseEntity.ok("Password updated successfully.");
    }
}
