package view;

import java.util.List;
import java.util.Scanner;

import DI.DependencyContainer;
import model.IE.PetSpec;
import model.PetModel;
import model.VO.*;
import service.interfaces.IPetService;
import util.StarterMenuValidator;

public class CLIInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IPetService petService = DependencyContainer.getPetService();

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

        Address address = new Address(houseNumber, city, street);

        petService.createPet(name, lastName, type, sex, ageInput, weightInput, address, breed);

        System.out.println("Pet added successfully!");
    }

    private static void findAllPets(){
        printAllPets(petService.findAllPets());
    }

    private static void printAllPets(List<PetModel> pets) {
        if (pets == null || pets.isEmpty()) {
            System.out.println("No pets found.");
            return;
        }

        System.out.println("\n=== Pet List ===");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + ". " + pets.get(i));
        }
    }

    private static void deletePet() {
        List<PetModel> pets = petService.findAllPets();
        if (pets.isEmpty()) {
            System.out.println("No pets available for deletion.");
            return;
        }
        printAllPets(pets);
        System.out.print("\nEnter the number of the pet you want to delete (or 0 to cancel): ");
        String input = scanner.nextLine();

        if (!StarterMenuValidator.validateMenu(input)) {
            System.out.println("Invalid input! Please enter a valid number.");
            return;
        }

        int index = Integer.parseInt(input) - 1;
        if (index < 0 || index >= pets.size()) {
            System.out.println("Invalid choice! Returning to search.");
            return;
        }

        PetModel petToDelete = pets.get(index);

        System.out.print("Are you sure you want to delete " + petToDelete.getName() + "? (YES/NO): ");
        String confirmation = scanner.nextLine().trim().toUpperCase();

        if (confirmation.equals("YES")) {
            boolean deleted = petService.deletePetByNameAndLastName(petToDelete.getName(), petToDelete.getLastName());

            if (deleted) {
                System.out.println("Pet deleted successfully.");
            } else {
                System.out.println("Error: Pet not found or could not be deleted.");
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    private static void deletePetForUpdate(PetModel petToDelete) {
        if (petToDelete == null) {
            System.out.println("Invalid pet selection.");
            return;
        }

        boolean deleted = petService.deletePetByNameAndLastName(petToDelete.getName(), petToDelete.getLastName());

        if (deleted) {
            System.out.println("Pet deleted successfully.");
        } else {
            System.out.println("Error: Pet not found or could not be deleted.");
        }
    }

    private static void filterPets() {
        List<PetModel> filteredPets = null;

        while (true) {
            System.out.println("\n=== Filter Pets ===");
            System.out.println("1. Search by one filter");
            System.out.println("2. Search by two filters");
            System.out.println("3. Return to main menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            if (!StarterMenuValidator.validateMenu(choice)) {
                System.out.println("Invalid input! Please enter a valid number (1-3).");
                continue;
            }

            int option = Integer.parseInt(choice);
            if (option == 3) {
                System.out.println("Returning to main menu...");
                return;
            }

            switch (option) {
                case 1 -> filteredPets = searchPet();
                case 2 -> filteredPets = searchByTwoFilters();
                default -> System.out.println("Invalid choice.");
            }

            if (filteredPets != null && !filteredPets.isEmpty()) {
                printAllPets(filteredPets);
            } else {
                System.out.println("No pets found matching the criteria. Try again.");
            }
        }
    }

    private static List<PetModel> searchPet() {
        System.out.println("\nChoose a filter:");
        System.out.println("1. Name");
        System.out.println("2. Breed");
        System.out.print("Enter your choice: ");

        String filterChoice = scanner.nextLine();
        if (!StarterMenuValidator.validateMenu(filterChoice)) {
            System.out.println("Invalid input! Please try again.");
            return null;
        }
        PetSpec.Builder builder = new PetSpec.Builder();
        switch (Integer.parseInt(filterChoice)) {
            case 1 -> {
                System.out.print("Enter pet name: ");
                String name = scanner.nextLine().trim();
                builder.setName(new Name(name));
            }
            case 2 -> {
                System.out.print("Enter pet breed: ");
                String breed = scanner.nextLine().trim();
                builder.setBreed(new Breed(breed));
            }
            default -> System.out.println("Invalid choice.");
        }
        PetSpec petSpec = builder.build();
        return petService.findPetBySpec(petSpec);
    }

    private static List<PetModel> searchByOneFilter() {
        System.out.println("\nChoose a filter:");
        System.out.println("1. Name");
        System.out.println("2. Breed");
        System.out.print("Enter your choice: ");

        String filterChoice = scanner.nextLine();
        if (!StarterMenuValidator.validateMenu(filterChoice)) {
            System.out.println("Invalid input! Please try again.");
            return null;
        }

        PetSpec.Builder builder = new PetSpec.Builder();

        switch (Integer.parseInt(filterChoice)) {
            case 1 -> {
                System.out.print("Enter pet name: ");
                String name = scanner.nextLine().trim();
                builder.setName(new Name(name));
            }
            case 2 -> {
                System.out.print("Enter pet breed: ");
                String breed = scanner.nextLine().trim();
                builder.setBreed(new Breed(breed));
            }
            default -> {
                System.out.println("Invalid choice.");
                return null;
            }
        }

        return petService.findPetBySpec(builder.build());
    }
    private static List<PetModel> searchByTwoFilters() {
        System.out.println("\nChoose two filters:");
        System.out.println("1. Name and Breed");
        System.out.println("2. Name and Type");
        System.out.println("3. Breed and Type");
        System.out.print("Enter your choice: ");

        String filterChoice = scanner.nextLine();
        if (!StarterMenuValidator.validateMenu(filterChoice)) {
            System.out.println("Invalid input! Please try again.");
            return null;
        }

        PetSpec.Builder builder = new PetSpec.Builder();

        switch (Integer.parseInt(filterChoice)) {
            case 1 -> {
                System.out.print("Enter pet name: ");
                String name = scanner.nextLine().trim();
                builder.setName(new Name(name));

                System.out.print("Enter pet breed: ");
                String breed = scanner.nextLine().trim();
                builder.setBreed(new Breed(breed));
            }
            case 2 -> {
                System.out.print("Enter pet name: ");
                String name = scanner.nextLine().trim();
                builder.setName(new Name(name));

                // Observação: Você precisará adaptar esta parte para guardar o tipo
                // no objeto PetSpec. O PetSpec atual não tem um campo para tipo.
                // Uma solução seria adicionar isso ao PetSpec ou tratar o tipo como
                // uma string no método de filtragem.

                // PetType type = selectPetType();
                // builder.setType(new Type(type)); // Você precisa adicionar isso ao PetSpec
            }
            case 3 -> {
                System.out.print("Enter pet breed: ");
                String breed = scanner.nextLine().trim();
                builder.setBreed(new Breed(breed));

                // Mesmo comentário sobre o tipo do pet
                // PetType type = selectPetType();
                // builder.setType(new Type(type));
            }
            default -> {
                System.out.println("Invalid choice.");
                return null;
            }
        }

        return petService.findPetBySpec(builder.build());
    }

    private static PetType selectPetType() {
        System.out.println("Select pet type:");
        for (PetType type : PetType.values()) {
            System.out.println(type.ordinal() + 1 + ". " + type);
        }
        System.out.print("Enter pet type (number): ");
        return PetType.values()[Integer.parseInt(scanner.nextLine().trim()) - 1];
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
        updatePetDetails(selectedPet);
        petService.updatePet(selectedPet);
        deletePetForUpdate(selectedPet);
        System.out.println("Pet details updated successfully.");
    }

    private static List<PetModel> getSearchResults() {
        while (true) {
            System.out.println("\n=== Search Pets ===");
            System.out.println("1. Search by name");
            System.out.println("2. Search by last name");
            System.out.println("3. Search by age");
            System.out.println("4. Search by weight");
            System.out.println("5. Search by breed");
            System.out.println("6. Search by address");
            System.out.println("7. Advanced search (multiple criteria)");
            System.out.println("8. Return to main menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            if (!StarterMenuValidator.validateMenu(choice)) {
                System.out.println("Invalid input! Please enter a valid number (1-8).");
                continue;
            }

            int choiceNum = Integer.parseInt(choice);
            if (choiceNum == 8) {
                return null;
            }

            // Criar o Builder para o PetSpec
            PetSpec.Builder builder = new PetSpec.Builder();

            switch (choiceNum) {
                case 1: // Nome
                    String name = getUserInput("Enter pet name to search: ");
                    builder.setName(new Name(name));
                    break;
                case 2: // Sobrenome
                    String lastName = getUserInput("Enter pet last name to search: ");
                    builder.setLastName(new LastName(lastName));
                    break;
                case 3: // Idade
                    String ageStr = getUserInput("Enter pet age to search: ");
                    try {
                        Double age = Double.parseDouble(ageStr);
                        builder.setAge(new Age(age));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid age format. Please enter a number.");
                        continue;
                    }
                    break;
                case 4: // Peso
                    String weightStr = getUserInput("Enter pet weight to search: ");
                    try {
                        Double weight = Double.parseDouble(weightStr);
                        builder.setWeight(new Weight(weight));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid weight format. Please enter a number.");
                        continue;
                    }
                    break;
                case 5: // Raça
                    String breed = getUserInput("Enter pet breed to search: ");
                    builder.setBreed(new Breed(breed));
                    break;
                case 6: // Endereço
                    String number = getUserInput("Enter pet address number to search: ");
                    String street = getUserInput("Enter pet address street to search: ");
                    String city = getUserInput("Enter pet address city to search: ");
                    builder.setAddress(new Address(number, city, street));
                    break;
                case 7: // Busca avançada com múltiplos critérios
                    System.out.println("\n=== Advanced Search ===");

                    String advName = getUserInput("Enter pet name (leave empty to skip): ");
                    if (!advName.isEmpty()) {
                        builder.setName(new Name(advName));
                    }

                    String advLastName = getUserInput("Enter pet last name (leave empty to skip): ");
                    if (!advLastName.isEmpty()) {
                        builder.setLastName(new LastName(advLastName));
                    }

                    String advAge = getUserInput("Enter pet age (leave empty to skip): ");
                    if (!advAge.isEmpty()) {
                        try {
                            Double age = Double.parseDouble(advAge);
                            builder.setAge(new Age(age));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid age format. Skipping age filter.");
                        }
                    }

                    String advWeight = getUserInput("Enter pet weight (leave empty to skip): ");
                    if (!advWeight.isEmpty()) {
                        try {
                            Double weight = Double.parseDouble(advWeight);
                            builder.setWeight(new Weight(weight));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid weight format. Skipping weight filter.");
                        }
                    }

                    String advBreed = getUserInput("Enter pet breed (leave empty to skip): ");
                    if (!advBreed.isEmpty()) {
                        builder.setBreed(new Breed(advBreed));
                    }

                    String AdNumber = getUserInput("Enter pet address number to search: ");
                    String AdStreet = getUserInput("Enter pet address street to search: ");
                    String AdCity = getUserInput("Enter pet address city to search: ");
                    if (!(AdNumber.isEmpty() && AdStreet.isEmpty() && AdCity.isEmpty())) {
                        builder.setAddress(new Address(AdNumber, AdCity, AdStreet));
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
                    continue;
            }

            // Construir o PetSpec com os filtros e realizar a busca
            PetSpec petSpec = builder.build();
            return petService.findPetBySpec(petSpec);
        }
    }

    private static void updatePetDetails(PetModel pet) {
        System.out.println("\n=== Update Pet Details ===");

        // Atualizando o nome e sobrenome
        String newName = getUpdatedValue("Enter new name", pet.getName());
        if (!newName.isEmpty()) {
            pet.setName(new Name(newName)); // Atualiza o nome
        }

        String newLastName = getUpdatedValue("Enter new last name", pet.getLastName());
        if (!newLastName.isEmpty()) {
            pet.setLastName(new LastName(newLastName)); // Atualiza o sobrenome
        }

        // Atualizando a raça
        String newBreed = getUpdatedValue("Enter new breed", pet.getBreed());
        if (!newBreed.isEmpty()) {
            pet.setBreed(new Breed(newBreed));
        }

        String ageInput = getUpdatedValue("Enter new age", String.valueOf(pet.getAge()));
        if (!ageInput.isEmpty()) {
            try {
                Double newAge = Double.parseDouble(ageInput);
                pet.setAge(new Age(newAge));
            } catch (NumberFormatException ignored) {}
        }


        String weightInput = getUpdatedValue("Enter new weight", String.valueOf(pet.getWeight()));
        if (!weightInput.isEmpty()) {
            try {
                float newWeight = Float.parseFloat(weightInput);
                pet.setWeight(new Weight(newWeight));
            } catch (NumberFormatException ignored) {}
        }

        // Atualizando o endereço
        String newStreet = getUpdatedValue("Enter new address street", pet.getAdress().getStreet());
        if (!newStreet.isEmpty()) {
            pet.getAdress().setStreet(newStreet); // Atualiza a rua
        }

        String newHouseNumber = getUpdatedValue("Enter new address house number", pet.getAdress().getHouseNumber());
        if (!newHouseNumber.isEmpty()) {
            pet.getAdress().setHouseNumber(newHouseNumber); // Atualiza o número da casa
        }

        String newCity = getUpdatedValue("Enter new address city", pet.getAdress().getCity());
        if (!newCity.isEmpty()) {
            pet.getAdress().setCity(newCity); // Atualiza a cidade
        }
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
