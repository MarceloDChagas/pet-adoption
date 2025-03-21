package service;

import model.IE.PetSpec;
import model.PetModel;
import model.VO.*;
import repository.File.PetFileDAO;
import repository.interfaces.IPetDAO;
import service.interfaces.IPetService;
import util.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PetService implements IPetService {
    private final IPetDAO repository;

    public PetService(IPetDAO repository) {
        this.repository = repository;
    }

    public void createPet(String name, String lastName, PetType type, PetSex sex, String ageInput, String weightInput, Address address, String breed) {
        // Convertendo os parâmetros para as classes VO correspondentes
        Name petName = new Name(name);
        LastName petLastName = new LastName(lastName);
        Age petAge = new Age(Double.parseDouble(ageInput));
        Weight petWeight = new Weight(Double.parseDouble(weightInput));
        Breed petBreed = new Breed(breed);

        // Criando o PetModel com os objetos VO
        PetModel petModel = new PetModel(petName, petLastName, type, sex, petAge, petWeight, address, petBreed);

        // Salvando o pet no repositório
        repository.savePetToFile(petModel);
    }

    public void updatePet(PetModel updatedPet) {
        repository.savePetToFile(updatedPet);
    }

    public List<PetModel> findPetBySpec(PetSpec petSpec) {
        return parsePetData(repository.getPetByFilter(petSpec));
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

    public String findPetFile(PetModel selectedPet, PetFileDAO petFileDAO) {
        for (String fileName : petFileDAO.getAllFileNames()) {
            String[] parts = fileName.split("-");
            if (parts.length > 1) {
                String extractedName = parts[1].replace(".TXT", "").trim();
                String fullPetName = selectedPet.getName().toString() + selectedPet.getLastName().toString();

                if (extractedName.equalsIgnoreCase(fullPetName)) {
                    return fileName;
                }
            }
        }
        return null;
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
            Double age = Double.parseDouble(extractValue(lines, "Idade:"));
            Double weight = Double.parseDouble(extractValue(lines, "Peso:"));

            String addressLine = extractValue(lines, "Endereço:");
            String[] addressParts = addressLine.split(",");
            String street = addressParts[0].trim();
            String houseNumber = addressParts.length > 1 ? addressParts[1].trim() : Constants.DEFAULT_UNINFORMED;
            String city = addressParts.length > 2 ? addressParts[2].trim() : Constants.DEFAULT_UNINFORMED;

            // Usando os objetos VO para construir o PetModel
            return new PetModel(new Name(name), new LastName(lastName), type, sex, new Age(age), new Weight(weight), new Address(houseNumber, city, street), new Breed(breed));
        } catch (Exception e) {
            System.out.println("Erro ao converter linhas para PetModel: " + lines);
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletePetByNameAndLastName(String name, String lastName) {
        PetModel petToFind = new PetModel(new Name(name), new LastName(lastName), null, null, new Age(10.0), new Weight(10.0), null, null);
        String petFile = findPetFile(petToFind, (PetFileDAO)repository); // Cast if necessary
        if (petFile != null) {
            repository.deletePetFile(petFile);
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

}
