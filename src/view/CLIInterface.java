package view;

import java.util.List;
import java.util.Scanner;

import model.PetModel;
import service.PetService;
import util.Adress;
import util.PetSex;
import util.PetType;
import util.StarterMenuValidator;

public class CLIInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PetService petService = new PetService();

    public static void showStarterMenu() {
        while (true) {
            System.out.println("\n=== Pet Adoption System ===");
            System.out.println("1. Add a pet");
            System.out.println("2. Search and update a pet");
            System.out.println("3. Delete a pet");
            System.out.println("4. Show all pets");
            System.out.println("5. Show all pets with filter");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            if (!StarterMenuValidator.validateMenu(choice)) {
                System.out.println("Invalid input! Please enter a valid number (1-6).");
                continue;
            }

            switch (Integer.parseInt(choice)) {
                case 1 -> addPet();
                case 2 -> searchAndUpdatePet();
                case 3 -> deletePet();
                case 4 -> findAllPets();
                case 5 -> filterPets();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    private static void addPet() {
        System.out.println("\n=== Add a New Pet ===");

        String name = getUserInput("Enter pet name: ");
        String lastName = getUserInput("Enter pet last name: ");
        PetType type = getEnumValue(PetType.class, "Select pet type: ");
        PetSex sex = getEnumValue(PetSex.class, "Select pet sex: ");
        String ageInput = getUserInput("Enter pet age: ");
        String weightInput = getUserInput("Enter pet weight: ");
        String breed = getUserInput("Enter pet breed: ");
        String street = getUserInput("Enter address street: ");
        String houseNumber = getUserInput("Enter house number: ");
        String city = getUserInput("Enter city: ");

        Adress address = new Adress(houseNumber, city, street);

        petService.createPet(name, lastName, type, sex, ageInput, weightInput, address, breed);

        System.out.println("Pet added successfully!");
    }

    private static void findAllPets(){
        printAllPets(petService.findAllPets());
    }

    private static void printAllPets(List <PetModel> pets){
        for (PetModel pet : pets){
            System.out.println(pet);
        }
    }

    private static void deletePet() {
        String name = getUserInput("Enter the name of the pet to delete: ");
        String lastName = getUserInput("Enter the last name of the pet: ");

        boolean deleted = petService.deletePetByNameAndLastName(name, lastName);

        if (deleted) {
            System.out.println("Pet deleted successfully.");
        } else {
            System.out.println("Pet not found.");
        }
    }
    private static void deletePet(PetModel petToUpdate) {
        String name = petToUpdate.getName();
        String lastName = petToUpdate.getLastName();

        boolean deleted = petService.deletePetByNameAndLastName(name, lastName);

        if (deleted) {
            System.out.println("Pet deleted successfully.");
        } else {
            System.out.println("Pet not found.");
        }
    }

    private static void filterPets() {
        System.out.println("\n=== Filter Pets ===");
        System.out.println("1. Search by one filter");
        System.out.println("2. Search by two filters");
        System.out.println("3. Return to main menu");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();

        if (!StarterMenuValidator.validateMenu(choice)) {
            System.out.println("Invalid input! Please enter a valid number (1-3).");
            return;
        }

        switch (Integer.parseInt(choice)) {
            case 1 -> {
                System.out.println("\nChoose a filter:");
                System.out.println("1. Name");
                System.out.println("2. Breed");
                System.out.print("Enter your choice: ");

                String filterChoice = scanner.nextLine();
                if (!StarterMenuValidator.validateMenu(filterChoice)) {
                    System.out.println("Invalid input!");
                    return;
                }

                switch (Integer.parseInt(filterChoice)) {
                    case 1 -> {
                        System.out.print("Enter pet name: ");
                        String name = scanner.nextLine().trim();
                        printAllPets(petService.findPet(name));
                    }
                    case 2 -> {
                        System.out.print("Enter pet breed: ");
                        String breed = scanner.nextLine().trim();
                        petService.findPet(breed);
                        printAllPets(petService.findPet(breed));
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
            case 2 -> {
                System.out.println("\nChoose two filters:");
                System.out.println("1. Name and Breed");
                System.out.println("2. Name and Type");
                System.out.println("3. Breed and Type");
                System.out.print("Enter your choice: ");

                String filterChoice = scanner.nextLine();
                if (!StarterMenuValidator.validateMenu(filterChoice)) {
                    System.out.println("Invalid input!");
                    return;
                }

                switch (Integer.parseInt(filterChoice)) {
                    case 1 -> {
                        System.out.print("Enter pet name: ");
                        String name = scanner.nextLine().trim();
                        System.out.print("Enter pet breed: ");
                        String breed = scanner.nextLine().trim();
                        printAllPets(petService.findPet(name, breed));
                    }
                    case 2 -> {
                        System.out.print("Enter pet name: ");
                        String name = scanner.nextLine().trim();
                        System.out.println("Select pet type:");
                        for (PetType type : PetType.values()) {
                            System.out.println(type.ordinal() + 1 + ". " + type);
                        }
                        System.out.print("Enter pet type (number): ");
                        PetType type = PetType.values()[Integer.parseInt(scanner.nextLine().trim()) - 1];
                        petService.findPet(name, type.toString());
                        printAllPets(petService.findPet(name, type.toString()));

                    }
                    case 3 -> {
                        System.out.print("Enter pet breed: ");
                        String breed = scanner.nextLine().trim();
                        System.out.println("Select pet type:");
                        for (PetType type : PetType.values()) {
                            System.out.println(type.ordinal() + 1 + ". " + type);
                        }
                        System.out.print("Enter pet type (number): ");
                        PetType type = PetType.values()[Integer.parseInt(scanner.nextLine().trim()) - 1];
                        petService.findPet(breed, type.toString());
                        printAllPets(petService.findPet(breed, type.toString()));
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
            case 3 -> System.out.println("Returning to main menu...");
            default -> System.out.println("Invalid choice.");
        }
    }


    private static void searchAndUpdatePet() {
        List<PetModel> searchResults = getSearchResults();

        if (searchResults == null || searchResults.isEmpty()) {
            System.out.println("No pets found.");
            return;
        }

        displaySearchResults(searchResults);
        PetModel selectedPet = selectPetFromResults(searchResults);

        if (selectedPet == null) {
            System.out.println("No pet selected.");
            return;
        }
        deletePet(selectedPet);
        updatePetDetails(selectedPet);
        petService.updatePet(selectedPet);
        System.out.println("Pet details updated successfully.");
    }

    private static List<PetModel> getSearchResults() {
        while (true) {
            System.out.println("\n=== Search Pets ===");
            System.out.println("1. Search by name");
            System.out.println("2. Search by date");
            System.out.println("3. Search by breed");
            System.out.println("4. Return to main menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            if (!StarterMenuValidator.validateMenu(choice)) {
                System.out.println("Invalid input! Please enter a valid number (1-4).");
                continue;
            }

            return switch (Integer.parseInt(choice)) {
                case 1 -> petService.findPet(getUserInput("Enter pet name to search: "));
                case 2 -> petService.findPetByDate(getUserInput("Enter date (YYYYMMDD): "));
                case 3 -> petService.findPet(getUserInput("Enter breed to search: "));
                case 4 -> null;
                default -> {
                    System.out.println("Invalid choice.");
                    yield null;
                }
            };
        }
    }

    private static void updatePetDetails(PetModel pet) {
        System.out.println("\n=== Update Pet Details ===");

        pet.setName(getUpdatedValue("Enter new name", pet.getName()));
        pet.setLastName(getUpdatedValue("Enter new last name", pet.getLastName()));
        pet.setBreed(getUpdatedValue("Enter new breed", pet.getBreed()));

        String ageInput = getUpdatedValue("Enter new age", String.valueOf(pet.getAge()));
        if (!ageInput.isEmpty()) {
            try {
                pet.setAge(Integer.parseInt(ageInput));
            } catch (NumberFormatException ignored) {}
        }

        String weightInput = getUpdatedValue("Enter new weight", String.valueOf(pet.getWeight()));
        if (!weightInput.isEmpty()) {
            try {
                pet.setWeight(Float.parseFloat(weightInput));
            } catch (NumberFormatException ignored) {}
        }

        pet.getAdress().setStreet(getUpdatedValue("Enter new address street", pet.getAdress().getStreet()));
        pet.getAdress().setHouseNumber(getUpdatedValue("Enter new address house number", pet.getAdress().getHouseNumber()));
        pet.getAdress().setCity(getUpdatedValue("Enter new address city", pet.getAdress().getCity()));
    }

    private static void displaySearchResults(List<PetModel> pets) {
        if (pets.isEmpty()) {
            System.out.println("No pets found.");
            return;
        }
        System.out.println("\n=== Search Results ===");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + ". " + pets.get(i));
        }
    }

    private static PetModel selectPetFromResults(List<PetModel> pets) {
        while (true) {
            String selection = getUserInput("\nEnter the number of the pet you want to update (or 0 to cancel): ");
            if (selection.equals("0")) return null;
            if (!StarterMenuValidator.validateMenu(selection)) continue;

            int index = Integer.parseInt(selection) - 1;
            if (index >= 0 && index < pets.size()) return pets.get(index);
        }
    }

    private static String getUserInput(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }

    private static <T extends Enum<T>> T getEnumValue(Class<T> enumClass, String prompt) {
        System.out.println(prompt);
        T[] values = enumClass.getEnumConstants();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        return values[Integer.parseInt(getUserInput("Enter choice: ")) - 1];
    }

    private static String getUpdatedValue(String prompt, String currentValue) {
        String input = getUserInput(prompt + " (" + currentValue + "): ");
        return input.isEmpty() ? currentValue : input;
    }
}
