package ru.itis.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class SignInDto {
//    @NotNull
//    @Size(min = 1)
    private String login;
//    @NotNull
//    @Size(min = 3)
    private String password;
}
