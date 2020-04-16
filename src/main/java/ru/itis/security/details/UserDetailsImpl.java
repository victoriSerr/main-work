package ru.itis.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.models.AppUser;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private AppUser appUser;

    public UserDetailsImpl(AppUser appUser) {
        this.appUser = appUser;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = appUser.getRole().toString();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        return Collections.singleton(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return appUser.getHashPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getLogin();
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
        return appUser.getIsConfirmed();
    }
}
