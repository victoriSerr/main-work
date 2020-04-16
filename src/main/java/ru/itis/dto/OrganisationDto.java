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
public class OrganisationDto {

    private String name;
    private String phoneNumber;
    private String address;
    private String description;

    private AppUser moderator;
}
