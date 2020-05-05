package ru.itis.services;

import ru.itis.dto.OrganisationDto;
import ru.itis.models.Organisation;

import java.util.List;

public interface OrganisationService {
   List<Organisation> findAll();
   void save(OrganisationDto organisationDto);
   Organisation find(Long id);
}
