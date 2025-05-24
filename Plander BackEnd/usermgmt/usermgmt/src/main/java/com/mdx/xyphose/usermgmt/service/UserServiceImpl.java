package com.mdx.xyphose.usermgmt.service;

import com.mdx.xyphose.usermgmt.entity.User;
import com.mdx.xyphose.usermgmt.repo.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok().body(users);
    }

    @Override
    public ResponseEntity<?> getUserById(int id) {
        Optional<User> userOptional = userRepo.findById((long) id);
        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<String> save(User user) throws SQLIntegrityConstraintViolationException {
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (Objects.nonNull(existingUser)) {
            throw new SQLIntegrityConstraintViolationException("Email already exists, please provide another email.");
        }
        userRepo.save(user);
        return ResponseEntity.ok("User added successfully.");
    }

    @Override
    public ResponseEntity<String> deleteUser(int id) {
        if (!userRepo.existsById((long) id)) {
            return ResponseEntity.badRequest().body("User not found.");
        }
        userRepo.deleteById((long) id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @Override
    public ResponseEntity<String> updateUser(int id, User user) {
        Optional<User> existing = userRepo.findById((long) id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }
        User updateUser = existing.get();
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setGender(user.getGender());
        userRepo.save(updateUser);
        return ResponseEntity.ok("User updated successfully.");
    }

    @Override
    public ResponseEntity<Long> getUsersCount() {
        long count = userRepo.count();
        return ResponseEntity.ok(count);
    }
}
