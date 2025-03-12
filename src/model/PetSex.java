package model;

public enum PetSex {
    MALE,
    FEMALE;

    public static String getSexo(PetSex sex) {
        return sex.name();
    }
}
