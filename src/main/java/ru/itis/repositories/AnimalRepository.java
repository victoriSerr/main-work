package ru.itis.repositories;

import ru.itis.models.Animal;

import java.util.List;

public interface AnimalRepository extends CrudDao<Animal, Long> {
    List<Animal> getOrgAnimals(Long id);
}
