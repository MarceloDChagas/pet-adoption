package model.VO;

import util.Constants;
import util.exceptions.InvalidNameException;

public class Name {
    private String name;

    public Name(String name) {
        this.name = validateName(name);
    }

    private String validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException(Constants.DEFAULT_UNINFORMED);
        }
        String regex = "^[A-Za-z\\s]+$";  // Apenas letras e espaços
        if (!name.matches(regex)) {
            return Constants.DEFAULT_UNINFORMED;  // Ou lance uma exceção, conforme a lógica que preferir
        }
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validateName(name);
    }

}
