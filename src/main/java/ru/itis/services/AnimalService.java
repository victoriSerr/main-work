package ru.itis.services;

import ru.itis.models.Animal;

import java.util.List;

public interface AnimalService {
    List<Animal> findAll();
    List<Animal> findOrganisationAnimals(Long id);
    Animal find(Long id);
    void save(Animal animal);
    void update(Animal animal);
}
