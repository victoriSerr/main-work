package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.AnimalDto;
import ru.itis.dto.InformationDto;
import ru.itis.models.Animal;
import ru.itis.models.FileInfo;
import ru.itis.models.Organisation;
import ru.itis.services.AnimalService;
import ru.itis.services.FileService;
import ru.itis.services.OrganisationService;

import java.util.List;

@RestController
@PreAuthorize("permitAll()")
public class AnimalsRestController {
    @Autowired
    private FileService fileService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private AnimalService animalService;

    @GetMapping("/api/animals")
    public List<Animal> getAllAnimals() {
        FileInfo fileInfo = fileService.findFile("77c86096-9810-4caa-ac4d-331e79c2135f.jpg");
        Organisation organisation = organisationService.find(1L);
//        System.out.println(organisation);
        System.out.println(organisation.getAnimalsNeedHome());
        System.out.println(organisation.getAnimalsInHome());
        System.out.println(fileInfo);
        List<Animal> animals = animalService.findAll();
        return animals;
    }

    @GetMapping("/api/info/{id}")
    public ResponseEntity<InformationDto> getInformation(@PathVariable("id") Long id){
        InformationDto dto = fileService.getInformation(id);
        return ResponseEntity.ok(dto);
    }
}
