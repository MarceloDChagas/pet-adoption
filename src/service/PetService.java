package service;

import model.PetModel;
import repository.PetRepository;
import util.PetSex;
import util.PetType;
import util.PetValidator;
import util.Adress;
import util.Constants;
import util.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetService {

    private final PetRepository repository;

    public PetService() {
        this.repository = new PetRepository();
    }

    public void createPet(String name, String lastName, PetType type, PetSex sex, String ageInput, String weightInput, Adress adress, String breed) {
        int age = validateAge(ageInput);
        float weight = validateWeight(weightInput);
        validateBreed(breed);
        repository.savePetToFile(new PetModel(name, lastName, type, sex, age, weight, adress, breed));
    }

    private int validateAge(String ageInput) {
        try {
            int age = Integer.parseInt(ageInput.trim());
            PetValidator.validateAge(age);
            return age;
        } catch (NumberFormatException | InvalidAgeException e) {
            return Constants.DEFAULT_UNINFORMED_INT;
        }
    }

    private float validateWeight(String weightInput) {
        try {
            float weight = Float.parseFloat(weightInput.trim());
            PetValidator.isValidWeight(weight);
            return weight;
        } catch (NumberFormatException | InvalidWeightException e) {
            return Constants.DEFAULT_UNINFORMED_INT;
        }
    }

    private void validateBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            breed = Constants.DEFAULT_UNINFORMED;
        }
        PetValidator.isValidBreed(breed);
    }

    public void findAllPets() {
        Map<String, List<String>> filesData = repository.getAllPets();
        List<PetModel> pets = parsePetData(filesData);

        if (pets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
        } else {
            int index = 1;
            for (PetModel pet : pets) {
                System.out.println(index + ". " + pet.toString());
                index++;
            }
        }
    }

    public void findPet(String filter) {
        Map<String, List<String>> filesData = repository.getPetByFilter(filter);
        List<PetModel> pets = parsePetData(filesData);

        if (pets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com o filtro: " + filter);
        } else {
            int index = 1;
            for (PetModel pet : pets) {
                System.out.println(index + ". " + pet.toString());
                index++;
            }
        }
    }

    public void findPet(String filter, String secondFilter) {
        Map<String, List<String>> filesData = repository.getPetByFilter(filter, secondFilter);
        List<PetModel> pets = parsePetData(filesData);

        if (pets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os filtros: " + filter + " e " + secondFilter);
        } else {
            int index = 1;
            for (PetModel pet : pets) {
                System.out.println(index + ". " + pet.toString());
                index++;
            }
        }
    }


    private List<PetModel> parsePetData(Map<String, List<String>> filesData) {
        List<PetModel> pets = new ArrayList<>();
        for (List<String> fileLines : filesData.values()) {
            List<String> petData = new ArrayList<>();
            for (String line : fileLines) {
                if (line.trim().isEmpty()) { // Nova linha separa os pets
                    if (!petData.isEmpty()) {
                        PetModel pet = parseLineToPet(petData);
                        if (pet != null) {
                            pets.add(pet);
                        }
                        petData.clear();
                    }
                } else {
                    petData.add(line);
                }
            }
            // Adiciona o último pet se não houver uma linha em branco no final
            if (!petData.isEmpty()) {
                PetModel pet = parseLineToPet(petData);
                if (pet != null) {
                    pets.add(pet);
                }
            }
        }
        return pets;
    }


    private PetModel parseLineToPet(List<String> lines) {
        try {
            String name = extractValue(lines, "Nome:");
            String lastName = extractValue(lines, "Sobrenome:");
            String breed = extractValue(lines, "Raça:");
            PetType type = PetType.valueOf(extractValue(lines, "Tipo:").toUpperCase());
            PetSex sex = PetSex.valueOf(extractValue(lines, "Sexo:").toUpperCase());
            int age = Integer.parseInt(extractValue(lines, "Idade:"));
            float weight = Float.parseFloat(extractValue(lines, "Peso:"));

            String addressLine = extractValue(lines, "Endereço:");
            String[] addressParts = addressLine.split(",");
            String street = addressParts[0].trim();
            String houseNumber = addressParts.length > 1 ? addressParts[1].trim() : Constants.DEFAULT_UNINFORMED;
            String city = addressParts.length > 2 ? addressParts[2].trim() : Constants.DEFAULT_UNINFORMED;

            return new PetModel(name, lastName, type, sex, age, weight, new Adress(houseNumber, city, street), breed);
        } catch (Exception e) {
            System.out.println("❌ Erro ao converter linhas para PetModel: " + lines);
            e.printStackTrace();
            return null;
        }
    }

    private String extractValue(List<String> lines, String prefix) {
        for (String line : lines) {
            if (line.startsWith(prefix)) {
                return line.replace(prefix, "").trim();
            }
        }
        return Constants.DEFAULT_UNINFORMED;
    }

}
