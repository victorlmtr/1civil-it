package com.backend.user.security.authentication;

import com.backend.user.model.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Implements UserDetails to provide user information for Spring Security authentication and authorization

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPasswordhash();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsenabled();
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
    public boolean isAccountNonExpired() {
        return true;
    }

    // Get the authorities (roles) of the user, which are assigned from the User's role
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // Role name is retrieved and converted into a SimpleGrantedAuthority
        return List.of(new SimpleGrantedAuthority(user.getRole().getRolename()));
    }
}
