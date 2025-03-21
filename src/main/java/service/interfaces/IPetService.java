package service.interfaces;

import model.IE.PetSpec;
import model.PetModel;
import repository.File.PetFileDAO;
import model.VO.Address;
import model.VO.PetSex;
import model.VO.PetType;
import java.util.List;
import java.util.Map;

public interface IPetService {
    void createPet(String name, String lastName, PetType type, PetSex sex, String ageInput, String weightInput, Address address, String breed);
    void updatePet(PetModel updatedPet);
    List<PetModel> findPetBySpec(PetSpec petSpec);
    List<PetModel> findAllPets();
    List<PetModel> findPetByDate(String date);
    List<PetModel> findPetByDateWithFilter(String date, String filter);
    List<PetModel> findPetByDateWithFilter(String date, String filter, String secondFilter);
    String findPetFile(PetModel selectedPet, PetFileDAO petFileDAO);
    List<PetModel> parsePetData(Map<String, List<String>> filesData);
    PetModel parseLineToPet(List<String> lines);
    boolean deletePetByNameAndLastName(String name, String lastName);
    String extractValue(List<String> lines, String prefix);
}
