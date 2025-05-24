package com.mdx.xyphose.usermgmt.entity.dto;

public class ForgetPasswordRequest {
    private String email;
    private String gender;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
