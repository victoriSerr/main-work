package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import ru.itis.models.Animal;
import ru.itis.repositories.AnimalRepository;
import ru.itis.repositories.jpa.AnimalRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Component
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public List<Animal> findOrganisationAnimals(Long id) {
        return animalRepository.getOrgAnimals(id);
    }

    @Override
    public Animal find(Long id) {
        Optional<Animal> animal = animalRepository.findOne(id);
        if(animal.isPresent()) {
            return animal.get();
        }
        throw new AccessDeniedException("Not Found");
    }

    @Override
    public void save(Animal animal) {
        animalRepository.save(animal);
    }

    @Override
    public void update(Animal animal) {
        animalRepository.update(animal);
    }
}
