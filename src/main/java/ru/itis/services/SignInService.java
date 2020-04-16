package ru.itis.services;


import ru.itis.dto.SignInDto;
import ru.itis.models.AppUser;

public interface SignInService {

    AppUser signIn(SignInDto signInDto);
}
