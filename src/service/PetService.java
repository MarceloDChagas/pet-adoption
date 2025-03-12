package service;

import model.PetModel;
import model.PetSex;
import model.PetType;
import util.PetValidator;
import util.Adress;
import util.Constants;
import util.exceptions.*;

public class PetService {

    public PetModel createPet(String name, String lastName, PetType type, PetSex sex, String ageInput, String weightInput, Adress adress, String breed) {
        int age = validateAge(ageInput);
        float weight = validateWeight(weightInput);
        validateBreed(breed);

        return new PetModel(name, lastName, type, sex, age, weight, adress, breed);
    }

    private int validateAge(String ageInput) {
        try {
            int age = Integer.parseInt(ageInput.trim());
            PetValidator.validateAge(age);
            return age;
        } catch (NumberFormatException | InvalidAgeException e) {
            return Constants.DEFAULT_UNINFORMED_INT;
        }
    }

    private float validateWeight(String weightInput) {
        try {
            float weight = Float.parseFloat(weightInput.trim());
            PetValidator.isValidWeight(weight);
            return weight;
        } catch (NumberFormatException | InvalidWeightException e) {
            return Constants.DEFAULT_UNINFORMED_INT;
        }
    }

    private void validateBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            breed = Constants.DEFAULT_UNINFORMED;
        }
        PetValidator.isValidBreed(breed);
    }
}
