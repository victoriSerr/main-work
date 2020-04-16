package ru.itis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDto {
    private String login;
    private String email;
    private String password;
}