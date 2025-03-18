package model.VO;

import util.Constants;
import util.exceptions.InvalidNameException;

public class LastName {
    private String lastName;

    public LastName(String lastName) {
        this.lastName = validateName(lastName);
    }

    private String validateName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new InvalidNameException(Constants.DEFAULT_UNINFORMED);
        }
        String regex = "^[A-Za-z\\s]+$";  // Apenas letras e espaços
        if (!lastName.matches(regex)) {
            return Constants.DEFAULT_UNINFORMED;  // Ou lance uma exceção, conforme a lógica que preferir
        }
        return lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = validateName(lastName);
    }
}
