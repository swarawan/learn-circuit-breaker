package com.swarawan.cb.controller;

import com.swarawan.cb.model.UserData;
import com.swarawan.cb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("api/users")
    public ResponseEntity<List<UserData>> getUsers() {
        List<UserData> users = userService.findAllStudents();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
