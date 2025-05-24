package com.mdx.xyphose.usermgmt.entity.dto;


import java.util.Set;

public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private Set<String> interests;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public Set<String> getInterests() {
//        return interests;
//    }
//
//    public void setInterests(Set<String> interests) {
//        this.interests = interests;
//    }
}

