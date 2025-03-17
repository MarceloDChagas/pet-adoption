package model;

import util.Adress;
import util.Constants;
import util.PetSex;
import util.PetType;

public class PetModel{
    private String name;
    private String lastName;
    private PetType type;
    private PetSex sex;
    private int age;
    private float weight;
    private Adress adress;
    private String breed;
    private String sourceFilename;

    public PetModel(String name, String lastName, PetType type, PetSex sex, int age, float weight, Adress adress, String breed) {
        this.name = (name == null || name.trim().isEmpty()) ? Constants.DEFAULT_UNINFORMED : name.trim();
        this.lastName = (lastName == null || lastName.trim().isEmpty()) ? Constants.DEFAULT_UNINFORMED : lastName.trim();
        this.type = type;
        this.sex = sex;
        this.breed = (breed == null || breed.trim().isEmpty()) ? Constants.DEFAULT_UNINFORMED : breed.trim();
        this.age = (age < 0) ? Constants.DEFAULT_UNINFORMED_INT : age;
        this.weight = (weight < 0) ? Constants.DEFAULT_UNINFORMED_INT : weight;
        if (adress == null) {
            this.adress = new Adress(Constants.DEFAULT_UNINFORMED, Constants.DEFAULT_UNINFORMED, Constants.DEFAULT_UNINFORMED);
        } else {
            this.adress = adress;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
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
        return  name  + " - "
                + type + " - "
                + sex + " - "
                + adress.toString() + " - "
                + age + " anos - "
                + weight + " - "
                + breed;
    }
}