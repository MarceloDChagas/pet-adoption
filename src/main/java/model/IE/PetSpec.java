package model.IE;
import model.VO.*;

public class PetSpec {
    private final Name name;
    private final LastName lastName;
    private final Age age;
    private final Weight weight;
    private final Breed breed;
    private final Address address;

    private PetSpec(Builder builder) {
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.weight = builder.weight;
        this.breed = builder.breed;
        this.address = builder.address;
    }

    public Name getName() {
        return name;
    }

    public LastName getLastName() {
        return lastName;
    }

    public Age getAge() {
        return age;
    }

    public Weight getWeight() {
        return weight;
    }

    public Breed getBreed() {
        return breed;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "PetSpec{" +
                "name=" + (name != null ? name.getName() : "N/A") +
                ", lastName=" + (lastName != null ? lastName.getLastName() : "N/A") +
                ", age=" + (age != null ? age.getAge() : "N/A") +
                ", weight=" + (weight != null ? weight.getWeight() : "N/A") +
                ", breed=" + (breed != null ? breed.getBreed() : "N/A") +
                ", address=" + (address != null ? address.toString() : "N/A") +
                '}';
    }
    public static class Builder {
        private Name name;
        private LastName lastName;
        private Age age;
        private Weight weight;
        private Breed breed;
        private Address address;

        public Builder setName(Name name) {
            this.name = name;
            return this;
        }

        public Builder setLastName(LastName lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setAge(Age age) {
            this.age = age;
            return this;
        }

        public Builder setWeight(Weight weight) {
            this.weight = weight;
            return this;
        }

        public Builder setBreed(Breed breed) {
            this.breed = breed;
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public PetSpec build() {
            return new PetSpec(this);
        }
    }
}