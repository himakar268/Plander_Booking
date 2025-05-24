package com.mdx.xyphose.usermgmt.initlizer;

import com.mdx.xyphose.usermgmt.entity.Role;
import com.mdx.xyphose.usermgmt.entity.User;
import com.mdx.xyphose.usermgmt.repo.RoleRepo;
import com.mdx.xyphose.usermgmt.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner loadData(RoleRepo roleRepository, UserRepo userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create roles first
            Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
                Role role = new Role();
                role.setName("USER");
                return roleRepository.save(role);
            });

            Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setName("ADMIN");
                return roleRepository.save(role);
            });

            // Create admin user if not exists
            if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
                User admin = new User();
                admin.setFirstName("Admin");
                admin.setLastName("User");
                admin.setEmail("admin@admin.com");
                admin.setPassword(passwordEncoder.encode("admin@123"));
                admin.setGender("Male");
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }
}