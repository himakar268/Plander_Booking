package com.mdx.xyphose.usermgmt.service;

import com.mdx.xyphose.usermgmt.entity.dto.ForgetPasswordRequest;
import com.mdx.xyphose.usermgmt.entity.dto.LoginRequest;
import com.mdx.xyphose.usermgmt.entity.dto.ResetPasswordRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<?> loginUser(LoginRequest loginRequest);
    ResponseEntity<?> forgetPassword(ForgetPasswordRequest request);
    ResponseEntity<?> resetPassword(ResetPasswordRequest request);
}
