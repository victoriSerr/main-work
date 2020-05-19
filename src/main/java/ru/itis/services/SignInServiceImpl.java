package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignInDto;
import ru.itis.models.AppUser;
import ru.itis.repositories.UserRepository;

import java.util.Optional;

@Component
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public AppUser signIn(SignInDto signInDto) {

        Optional<AppUser> userCandidate = repository.findByLogin(signInDto.getLogin());

        if (userCandidate.isPresent()) {
            AppUser appUser = userCandidate.get();
            if (encoder.matches(signInDto.getPassword(), appUser.getHashPassword())) {
                return appUser;
            } else throw new AccessDeniedException("Wrong login or password");
        } else throw new AccessDeniedException("User not found");

    }
}
