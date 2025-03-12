package util;

import util.exceptions.*;
import util.Constants;

public class PetValidator {

    public static boolean isValidAge(int age) {
        if (age < 0 || age > 20) {
            throw new InvalidAgeException(Constants.INVALID_AGE_MESSAGE);
        }
        return true;
    }

    public static boolean isValidWeight(float weight) {
        if (weight < 0 || weight > 100) {
            throw new InvalidWeightException(Constants.INVALID_WEIGHT_MESSAGE);
        }
        return true;
    }

    public static boolean isValidBreed(String breed) {
        if (!(breed.matches("^[a-zA-Z ]+$"))) {
            throw new InvalidBreedException(Constants.INVALID_BREED_MESSAGE);
        }
        return true;
    }

    public static String validateHouseNumber(String houseNumber) {
        if (houseNumber == null || houseNumber.trim().isEmpty()) {
            return Constants.DEFAULT_UNINFORMED;
        }
        return houseNumber.trim();
    }

    public static int validateAge(int age) {
        if (age < 1) {
            return Constants.DEFAULT_UNINFORMED_INT; // Retorna valor "não informado" caso a idade seja inválida
        }
        isValidAge(age); // Verifica se a idade está no intervalo de 0 a 20
        return age;
    }
}
