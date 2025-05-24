package com.mdx.xyphose.usermgmt.service;

import com.mdx.xyphose.usermgmt.entity.User;
import org.springframework.http.ResponseEntity;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface UserService {

    ResponseEntity<List<User>> getAllUsers();

    ResponseEntity<?> getUserById(int id);

    ResponseEntity<String> save(User user) throws SQLIntegrityConstraintViolationException;

    ResponseEntity<String> deleteUser(int id);

    ResponseEntity<String> updateUser(int id,User user);


    ResponseEntity<Long> getUsersCount();
}
