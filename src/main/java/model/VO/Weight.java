package model.VO;

import util.Constants;

public class Weight {
    private Double weight;

    public Weight(double weight) {
        this.weight = validateWeight(weight);
    }

    private double validateWeight(double weight) {
        if (weight < 0.5 || weight > 60) {
            throw new IllegalArgumentException(Constants.INVALID_WEIGHT_MESSAGE);
        }
        return weight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = validateWeight(weight);
    }
}
