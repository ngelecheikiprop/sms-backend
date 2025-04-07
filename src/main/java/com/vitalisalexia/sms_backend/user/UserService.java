package com.vitalisalexia.sms_backend.user;

import com.vitalisalexia.sms_backend.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }


    public LoginResponse verify(Users user) {
        LoginResponse loginResponse = new LoginResponse();
        System.out.println("------------------------->Authenticating<---------------------------");
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()){
            loginResponse.setToken(jwtService.generateToken(user.username));
            loginResponse.setStatus(000);
            loginResponse.setMessage("success");
            return loginResponse;
        }
        loginResponse.setStatus(001);
        loginResponse.setMessage("fail");
        return loginResponse;
    }
}
