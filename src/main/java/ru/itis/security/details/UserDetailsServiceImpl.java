package ru.itis.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.models.AppUser;
import ru.itis.repositories.UserRepository;

import java.util.Optional;


@Component(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<AppUser> userCandidate = userRepository.findByLogin(login);
        if(userCandidate.isPresent()) {
            AppUser appUser = userCandidate.get();
            return new UserDetailsImpl(appUser);
        } throw new UsernameNotFoundException("User not found");
    }
}
