package model.VO;

public enum PetType {
    DOG,
    CAT;

    public static String getType(PetType type) {
        return type.name();
    }
}