package ru.itis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInDto {
    private String login;
    private String password;
}
