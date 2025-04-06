package com.vitalisalexia.sms_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        System.out.println("registering user : " + user.username);
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        System.out.println("logining user : " + user.username);
        return userService.verify(user);
    }
}
