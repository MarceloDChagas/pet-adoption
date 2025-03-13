package view;
import java.util.Scanner;

import service.PetService;
import util.Adress;
import util.PetSex;
import util.PetType;
import util.StarterMenuValidator;

public class CLIInteface {
    public static void showStarterMenu() {
        PetService petService = new PetService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Pet Adoption System ===");
            System.out.println("1. Add a pet");
            System.out.println("2. Update a pet");
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

            int option = Integer.parseInt(choice);

            switch (option) {
                case 1:
                    petService.createPet("Ximbinha", "Da Silva", PetType.DOG, PetSex.FEMALE, "2", "4.5f", new Adress("123", "Belo jardim", "Centro"), "Poodle");
                    break;
                case 2:
                    System.out.println("Updating a pet...");
                    break;
                case 3:
                    System.out.println("Deleting a pet...");
                    break;
                case 4:
                    petService.findAllPets();
                    break;
                case 5:
                    petService.findPet("Ximbinha");
                    petService.findPet("Da Silva", "Poodle");
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }
}
