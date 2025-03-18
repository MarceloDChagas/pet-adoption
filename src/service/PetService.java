package service;

import model.PetModel;
import repository.PetRepository;
import repository.interfaces.IPetRepository;
import service.interfaces.IPetService;
import util.*;
import util.exceptions.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PetService implements IPetService {
    private final IPetRepository repository;

    public PetService(IPetRepository repository) {
        this.repository = new PetRepository(getFileManage());
    }

    public void createPet(String name, String lastName, PetType type, PetSex sex, String ageInput, String weightInput, Adress adress, String breed) {
        int age = validateAge(ageInput);
        float weight = validateWeight(weightInput);
        validateBreed(breed);
        repository.savePetToFile(new PetModel(name, lastName, type, sex, age, weight, adress, breed));
    }

    public void updatePet(PetModel updatedPet) {
        repository.savePetToFile(updatedPet);
    }

    public List<PetModel> findPet(String filter) {
        return parsePetData(repository.getPetByFilter(filter));
    }

    public List<PetModel> findPet(String filter, String secondFilter) {
        return parsePetData(repository.getPetByFilter(filter, secondFilter));
    }

    public List<PetModel> findAllPets() {
        List<PetModel> pets = parsePetData(repository.getAllPets());
        pets.sort(Comparator.comparing(PetModel::getName, String.CASE_INSENSITIVE_ORDER));
        return pets;
    }

    public List<PetModel> findPetByDate(String date) {
        return parsePetData(repository.getPetByDate(date));
    }

    public List<PetModel> findPetByDateWithFilter(String date, String filter) {
        return parsePetData(repository.getPetByDateWithFilter(date, filter));
    }

    public List<PetModel> findPetByDateWithFilter(String date, String filter, String secondFilter) {
        return parsePetData(repository.getPetByDateWithFilter(date, filter, secondFilter));
    }

    public String findPetFile(PetModel selectedPet, PetRepository petRepository) {
        for (String fileName : petRepository.getAllFileNames()) {
            String[] parts = fileName.split("-");
            if (parts.length > 1) {
                String extractedName = parts[1].replace(".TXT", "").trim();
                String fullPetName = selectedPet.getName() + selectedPet.getLastName();

                if (extractedName.equalsIgnoreCase(fullPetName)) {
                    return fileName;
                }
            }
        }
        return null;
    }

    public void updatePetDetails(PetModel pet, String name, String lastName, String breed, Integer age, Float weight, Adress adress) {
        if (name != null && !name.isEmpty()) pet.setName(name);
        if (lastName != null && !lastName.isEmpty()) pet.setLastName(lastName);
        if (breed != null && !breed.isEmpty()) pet.setBreed(breed);
        if (age != null) pet.setAge(age);
        if (weight != null) pet.setWeight(weight);
        if (adress != null) pet.setAdress(adress);
    }

    public List<PetModel> parsePetData(Map<String, List<String>> filesData) {
        List<PetModel> pets = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : filesData.entrySet()) {
            String filename = entry.getKey();
            List<String> fileLines = entry.getValue();
            List<String> petData = new ArrayList<>();

            for (String line : fileLines) {
                if (line.trim().isEmpty()) {
                    if (!petData.isEmpty()) {
                        PetModel pet = parseLineToPet(petData);
                        if (pet != null) {
                            pet.setSourceFilename(filename);
                            pets.add(pet);
                        }
                        petData.clear();
                    }
                } else {
                    petData.add(line);
                }
            }
            if (!petData.isEmpty()) {
                PetModel pet = parseLineToPet(petData);
                if (pet != null) {
                    pet.setSourceFilename(filename);
                    pets.add(pet);
                }
            }
        }
        return pets;
    }

    public PetModel parseLineToPet(List<String> lines) {
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
            System.out.println("Erro ao converter linhas para PetModel: " + lines);
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletePetByNameAndLastName(String name, String lastName) {
        PetRepository petRepository = new PetRepository(getFileManage());

        PetModel petToFind = new PetModel(name, lastName, null, null, 0, 0, null, null);

        String petFile = findPetFile(petToFind, petRepository);

        if (petFile != null) {
            petRepository.deletePetFile(petFile);
            return true;
        }

        return false;
    }

    public String extractValue(List<String> lines, String prefix) {
        for (String line : lines) {
            if (line.startsWith(prefix)) {
                return line.replace(prefix, "").trim();
            }
        }
        return Constants.DEFAULT_UNINFORMED;
    }

    public int validateAge(String ageInput) {
        try {
            int age = Integer.parseInt(ageInput.trim());
            PetValidator.validateAge(age);
            return age;
        } catch (NumberFormatException | InvalidAgeException e) {
            return Constants.DEFAULT_UNINFORMED_INT;
        }
    }

    public float validateWeight(String weightInput) {
        try {
            float weight = Float.parseFloat(weightInput.trim());
            PetValidator.isValidWeight(weight);
            return weight;
        } catch (NumberFormatException | InvalidWeightException e) {
            return Constants.DEFAULT_UNINFORMED_INT;
        }
    }

    public void validateBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            breed = Constants.DEFAULT_UNINFORMED;
        }
        PetValidator.isValidBreed(breed);
    }
}
