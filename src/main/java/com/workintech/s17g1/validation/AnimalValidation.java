package com.workintech.s17g1.validation;

import com.workintech.s17g1.entity.Animal;
import com.workintech.s17g1.mapping.AnimalResponse;

import java.util.Map;

public class AnimalValidation {


    public static boolean isIdValid(int id) {
        return !(id < 0);
    }

    public static boolean isMapContainsKey(Map<Integer, Animal> animals, int id) {
        return animals.containsKey(id);
    }

    public static boolean isCredentialsValid(Animal animal) {
        return !(animal.getId() <= 0 || animal.getName() == null || animal.getName().isEmpty());
    }

}
