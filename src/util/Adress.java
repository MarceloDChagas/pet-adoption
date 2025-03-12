package util;
import util.Constants;

public class Adress {
    private String houseNumber;
    private String city;
    private String street;

    public Adress(String houseNumber, String city, String street) {
        this.houseNumber = (houseNumber == null || houseNumber.trim().isEmpty())
                ? Constants.DEFAULT_UNINFORMED
                : houseNumber;
        this.city = city;
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
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
        return "Rua " + street  +
                ", " + houseNumber +
                ", " + city ;
    }
}
