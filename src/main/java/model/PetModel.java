package model;

import model.VO.*;

public class PetModel {
    private Name name;
    private LastName lastName;
    private PetType type;
    private PetSex sex;
    private Age age;
    private Weight weight;
    private Address address;
    private Breed breed;
    private String sourceFilename;

    public PetModel(Name name, LastName lastName, PetType type, PetSex sex, Age age, Weight weight, Address address, Breed breed) {
        this.name = name;
        this.lastName = lastName;
        this.type = type;
        this.sex = sex;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.address = address;
    }

    public String getName() {
        return this.name.getName();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getLastName() {
        return this.name.getName();
    }

    public void setLastName(LastName lastName) {
        this.lastName = lastName;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public PetSex getSex() {
        return sex;
    }

    public void setSex(PetSex sex) {
        this.sex = sex;
    }

    public Double getAge() {
        return this.age.getAge();
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Double getWeight() {
        return this.weight.getWeight();
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Address getAdress() {
        return address;
    }

    public void setAdress(Address address) {
        this.address = address;
    }

    public String getBreed() {
        return this.breed.getBreed();
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public String getSourceFilename() {
        return sourceFilename;
    }

    public void setSourceFilename(String sourceFilename) {
        this.sourceFilename = sourceFilename;
    }

    @Override
    public String toString() {
        return  name.getName() + " " + lastName.getLastName() + " - "
                + type + " - "
                + sex + " - "
                + address.toString() + " - "
                + age.getAge() + " anos - "
                + weight.getWeight() + " kg - "
                + breed;
    }
}
