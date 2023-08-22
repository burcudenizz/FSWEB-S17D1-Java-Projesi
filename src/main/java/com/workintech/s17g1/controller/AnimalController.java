package com.workintech.s17g1.controller;

import com.workintech.s17g1.entity.Animal;
import com.workintech.s17g1.mapping.AnimalResponse;
import com.workintech.s17g1.validation.AnimalValidation;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/animal")
public class AnimalController {

    @Value("${student.name}")
    private String name;

    @Value("${student.surname}")
    private String surname;


    private Map<Integer, Animal> animalMap;

    @GetMapping("/welcome")
    public String welcomne() {
        return name + " - " + surname + "says hi";
    }

    @GetMapping("/")
    public List<Animal> getAllAnimals() {
        return animalMap.values().stream().toList();
    }

    @GetMapping("/{id}")
    public AnimalResponse get(@PathVariable int id) {

        if (AnimalValidation.isIdValid(id)) {
            return new AnimalResponse(null, "Id is not valid", 400);
        }
        if (!AnimalValidation.isMapContainsKey(animalMap, id)) {
            return new AnimalResponse(null, "Id is not found", 400);
        }
        return new AnimalResponse(animalMap.get(id), "Success", 200);
    }

    @PostMapping("/")
    public AnimalResponse save(@RequestBody Animal animal) {
        if (AnimalValidation.isMapContainsKey(animalMap, animal.getId())) {
            return new AnimalResponse(null, "animal is already exist", 400);
        }
        if (AnimalValidation.isCredentialsValid(animal)) {
            return new AnimalResponse(null, "animal credentials are not valid", 400);
        }
        animalMap.put(animal.getId(), animal);
        return new AnimalResponse(animalMap.get(animal.getId()), "animal created successfully", 201);
    }

    @PutMapping("/{id}")
    public AnimalResponse update(@PathVariable int id, @RequestBody Animal animal) {
        if (!AnimalValidation.isIdValid(id)) {
            return new AnimalResponse(null, "Id is not found", 400);
        }
        if (!AnimalValidation.isCredentialsValid(animal)) {
            return new AnimalResponse(null, "animal credentials are not valid", 400);
        }
        animalMap.put(id, animal);
        return new AnimalResponse(animalMap.get(id), "animal updated", 200);
    }


    @DeleteMapping("/{id}")
    public AnimalResponse delete(@PathVariable int id) {
        if (!AnimalValidation.isMapContainsKey(animalMap, id)) {
            //TODO: Animal not exist
            return new AnimalResponse(null, "Id is not found", 400);
        }

        Animal willDelete = animalMap.get(id);
        animalMap.remove(id);
        return new AnimalResponse(willDelete, "animal successfully deleted", 200);

    }

    @PostConstruct
    public void init() {
        animalMap = new HashMap<>();
    }


    @PreDestroy // instance'ın çalışacak son metotudur.
    public void destroy() {
        System.out.println("animal controlled has been destroyed.");
    }

}
