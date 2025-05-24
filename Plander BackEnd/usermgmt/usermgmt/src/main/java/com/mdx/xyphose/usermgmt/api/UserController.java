package com.mdx.xyphose.usermgmt.api;

import com.mdx.xyphose.usermgmt.entity.User;
import com.mdx.xyphose.usermgmt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getUsersCount() {
        return userService.getUsersCount();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable(name="id") int userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user) throws SQLIntegrityConstraintViolationException {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable(name="id") int userId) {
        return userService.deleteUser(userId);
    }
}