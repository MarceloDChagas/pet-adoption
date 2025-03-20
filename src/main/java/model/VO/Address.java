package model.VO;

import util.Constants;

public class Address {
    private String houseNumber;
    private String city;
    private String street;

    public Address(String houseNumber, String city, String street) {
        this.houseNumber = validateHouseNumber(houseNumber);
        this.city = city;
        this.street = street;
    }

    private String validateHouseNumber(String houseNumber) {
        if(houseNumber == null || houseNumber.isEmpty()) {
            houseNumber = Constants.DEFAULT_UNINFORMED;
        }
        return houseNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = validateHouseNumber(houseNumber);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return street + ", " + houseNumber + ", " + city;
    }
}
