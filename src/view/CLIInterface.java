package view;

import java.util.List;
import java.util.Scanner;

import model.PetModel;
import repository.PetRepository;
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
                case 4 -> petService.findAllPets();
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
        petService.createPet("flo", "Da Silva", PetType.DOG, PetSex.FEMALE, "2", "4.5", new Adress("123", "Belo jardim", "Centro"), "Poodle");
    }

    private static void deletePet() {
        System.out.print("Enter the name of the pet to delete: ");
        String name = scanner.nextLine();
        System.out.print("Enter the last name of the pet: ");
        String lastName = scanner.nextLine();

        PetRepository petRepository = new PetRepository();
        String petFile = findPetFile(new PetModel(name, lastName, null, null, 0, 0, null, null), petRepository);

        if (petFile != null) {
            petRepository.deletePetFile(petFile);
            System.out.println("Pet deleted successfully.");
        } else {
            System.out.println("Pet file not found.");
        }
    }

    private static void filterPets() {
        petService.findPet("Ximbinha");
        petService.findPet("Da Silva", "Poodle");
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

        System.out.println("Pet selected: " + selectedPet.getName());
        PetRepository petRepository = new PetRepository();
        String petFile = findPetFile(selectedPet, petRepository);

        if (petFile != null) {
            petRepository.deletePetFile(petFile);
            updatePetDetails(selectedPet);
            petRepository.savePetToFile(selectedPet);
        } else {
            System.out.println("Pet file not found.");
        }
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
                case 1 -> {
                    System.out.print("Enter pet name to search: ");
                    yield petService.findPet(scanner.nextLine());
                }
                case 2 -> {
                    System.out.print("Enter date (YYYYMMDD): ");
                    yield petService.findPetByDate(scanner.nextLine());
                }
                case 3 -> {
                    System.out.print("Enter breed to search: ");
                    yield petService.findPet(scanner.nextLine());
                }
                case 4 -> null;
                default -> {
                    System.out.println("Invalid choice.");
                    yield null;
                }
            };
        }
    }

    private static String findPetFile(PetModel selectedPet, PetRepository petRepository) {
        for (String fileName : petRepository.getAllFileNames()) {
            System.out.println("File name: " + fileName);

            // Extrai a parte do nome após o último "-"
            String[] parts = fileName.split("-");
            if (parts.length > 1) {
                String extractedName = parts[1].replace(".TXT", "").trim(); // Remove a extensão e espaços extras
                System.out.println("Extracted name: " + extractedName);

                String fullPetName = selectedPet.getName() + selectedPet.getLastName()  ;
                System.out.println("Selected pet: " + fullPetName);

                if (extractedName.equalsIgnoreCase(fullPetName)) {
                    System.out.println("Matching file found: " + fileName);
                    return fileName;
                }
            }
        }
        return null;
    }

    private static void updatePetDetails(PetModel pet) {
        System.out.println("\n=== Update Pet Details ===");

        System.out.print("Enter new name (" + pet.getName() + "): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) pet.setName(name);

        System.out.print("Enter new last name (" + pet.getLastName() + "): ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) pet.setLastName(lastName);

        System.out.print("Enter new breed (" + pet.getBreed() + "): ");
        String breed = scanner.nextLine().trim();
        if (!breed.isEmpty()) pet.setBreed(breed);

        System.out.print("Enter new age (" + pet.getAge() + "): ");
        String age = scanner.nextLine().trim();
        if (!age.isEmpty()) pet.setAge(Integer.parseInt(age));

        System.out.print("Enter new weight (" + pet.getWeight() + "): ");
        String weight = scanner.nextLine().trim();
        if (!weight.isEmpty()) pet.setWeight(Float.parseFloat(weight));

        System.out.print("Enter new address street (" + pet.getAdress().getStreet() + "): ");
        String street = scanner.nextLine().trim();
        if (!street.isEmpty()) pet.getAdress().setStreet(street);

        System.out.print("Enter new address house number (" + pet.getAdress().getHouseNumber() + "): ");
        String houseNumber = scanner.nextLine().trim();
        if (!houseNumber.isEmpty()) pet.getAdress().setHouseNumber(houseNumber);

        System.out.print("Enter new address city (" + pet.getAdress().getCity() + "): ");
        String city = scanner.nextLine().trim();
        if (!city.isEmpty()) pet.getAdress().setCity(city);

        System.out.println("Pet details updated successfully.");
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
            System.out.print("\nEnter the number of the pet you want to update (or 0 to cancel): ");
            String selection = scanner.nextLine();
            if (selection.equals("0")) return null;
            if (!StarterMenuValidator.validateMenu(selection)) continue;

            int index = Integer.parseInt(selection) - 1;
            if (index >= 0 && index < pets.size()) return pets.get(index);
        }
    }
}