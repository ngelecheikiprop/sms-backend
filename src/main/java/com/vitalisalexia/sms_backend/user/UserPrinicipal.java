package com.vitalisalexia.sms_backend.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrinicipal implements UserDetails {

    private Users user;

    public UserPrinicipal(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER")) ;
    }

    @Override
    public String getPassword() {
        System.out.print(user.getPassword()+"***********************the pass");
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        System.out.print("--------------------->(UserPriniciplal)get username"+user.getUsername()+"<--------------------------------------");
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
