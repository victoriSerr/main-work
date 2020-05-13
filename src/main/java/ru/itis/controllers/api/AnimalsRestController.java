package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.AnimalDto;
import ru.itis.models.Animal;
import ru.itis.services.AnimalService;

import java.util.List;

@RestController
@PreAuthorize("permitAll()")
public class AnimalsRestController {


    @Autowired
    private AnimalService animalService;

    @GetMapping("/api/animals")
    public ResponseEntity<List<Animal>> getAllAnimals() {

        List<Animal> animals = animalService.findAll();
        return ResponseEntity.ok(animals);
    }
}
