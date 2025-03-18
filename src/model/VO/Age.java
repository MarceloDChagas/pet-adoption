package model.VO;

import util.Constants;
import util.exceptions.InvalidAgeException;

public class Age {
    private Double age;

    public Age(int age) {
        this.age = validateAge(age);
    }

    private double validateAge(double age) {
        if (age < 1) {
            return age / 12.0;  // Para idades em meses
        }
        if (age > 20) {
            throw new InvalidAgeException(Constants.INVALID_AGE_MESSAGE);
        }
        return age;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = validateAge(age);
    }
}
