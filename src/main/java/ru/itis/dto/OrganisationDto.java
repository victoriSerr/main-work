package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import ru.itis.models.AppUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganisationDto {


    @NotNull
    @Size(min = 1, message = "{Size.form.name}")
    private String name;

    @NotNull
    @Pattern(regexp = "8[0-9]{10}", message = "{Pattern.form.phoneNumber}")
    private String phoneNumber;

    @NotNull
    @Size(min = 1, message = "{Size.form.address}")
    private String address;
    private String description;

    private AppUser moderator;
}
