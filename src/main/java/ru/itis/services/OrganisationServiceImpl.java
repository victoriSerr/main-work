package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.OrganisationDto;
import ru.itis.models.Organisation;
import ru.itis.repositories.OrganisationRepository;

import java.util.List;

@Component
public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    private OrganisationRepository organisationRepository;

    @Override
    public List<Organisation> findAll() {
        return organisationRepository.findAll();
    }

    @Override
    public void save(OrganisationDto organisationDto) {
        Organisation organisation = Organisation.builder()
                .name(organisationDto.getName())
                .phoneNumber(organisationDto.getPhoneNumber())
                .address(organisationDto.getAddress())
                .description(organisationDto.getDescription())
                .moderator(organisationDto.getModerator())
                .build();

        organisationRepository.save(organisation);
    }


}
