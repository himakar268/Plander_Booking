package com.mdx.xyphose.usermgmt.service;

import com.mdx.xyphose.usermgmt.entity.Role;
import com.mdx.xyphose.usermgmt.entity.User;
import com.mdx.xyphose.usermgmt.entity.dto.UserDTO;
import com.mdx.xyphose.usermgmt.repo.RoleRepo;
import com.mdx.xyphose.usermgmt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignupService(UserRepo userRepository, RoleRepo roleRepository,
                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDTO userDTO) {
        Optional<Role> userRole = roleRepository.findByName("USER");

        if (userRole.isEmpty()) {
            throw new RuntimeException("Default user role not found");
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setGender(userDTO.getGender());
        user.setRole(userRole.get());

        return userRepository.save(user);
    }
}