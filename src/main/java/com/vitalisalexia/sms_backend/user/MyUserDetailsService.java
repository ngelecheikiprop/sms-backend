package com.vitalisalexia.sms_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("--------------------------------getting user:"+username);
        Users user = repo.findByUsername(username);
        System.out.println("********************"+user.username+"*******************");

        if (user == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrinicipal(user);
    }
}
