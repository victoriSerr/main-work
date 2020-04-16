package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.AppUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String login;
    private String email;


    public static UserDto from(AppUser appUser) {
        return UserDto.builder().id(appUser.getId())
                .login(appUser.getLogin())
                .email(appUser.getEmail())
                .build();
    }
}
