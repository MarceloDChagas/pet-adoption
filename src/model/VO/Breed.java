package model.VO;

import util.Constants;
import util.exceptions.InvalidBreedException;

public class Breed {
    private String breed;

    public Breed(String breed) {
        this.breed = validateBreed(breed);  // Validação chamada no construtor
    }

    private String validateBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            return Constants.DEFAULT_UNINFORMED;
        }

        String regex = "^[A-Za-z\\s]+$";
        if (!breed.matches(regex)) {
            throw new InvalidBreedException(Constants.INVALID_BREED_MESSAGE);
        }

        return breed;
    }

    public String getBreed() { return breed; }

    public void setBreed(String breed) {}
}
