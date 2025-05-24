package com.mdx.xyphose.usermgmt.api;

import com.mdx.xyphose.usermgmt.entity.User;
import com.mdx.xyphose.usermgmt.entity.dto.*;
import com.mdx.xyphose.usermgmt.service.LoginService;
import com.mdx.xyphose.usermgmt.service.SignupService;
import com.mdx.xyphose.usermgmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/users/login")
@CrossOrigin
public class LoginController {

    Logger logger = Logger.getLogger("LoginController");

    @Autowired
    LoginService loginService;
    @Autowired
    SignupService signupService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Request Receeived : " + loginRequest);
        return loginService.loginUser(loginRequest);
    }

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User registerUser(@RequestBody UserDTO userDTO) {
        return signupService.registerUser(userDTO);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgetPasswordRequest request) {
        logger.info("Forgot password verification for: " + request.getEmail());
        return loginService.forgetPassword(request);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        logger.info("Resetting password for: " + request.getEmail());
        return loginService.resetPassword(request);
    }
}