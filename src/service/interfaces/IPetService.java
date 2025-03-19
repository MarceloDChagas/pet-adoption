package service.interfaces;

import model.PetModel;
import repository.PetRepository;
import model.VO.Adress;
import model.VO.Name;
import model.VO.PetSex;
import model.VO.PetType;
import java.util.List;
import java.util.Map;

public interface IPetService {
    void createPet(String name, String lastName, PetType type, PetSex sex, String ageInput, String weightInput, Adress adress, String breed);
    void updatePet(PetModel updatedPet);
    List<PetModel> findPet(String filter);
    List<PetModel> findPet(String filter, String secondFilter);
    List<PetModel> findAllPets();
    List<PetModel> findPetByDate(String date);
    List<PetModel> findPetByDateWithFilter(String date, String filter);
    List<PetModel> findPetByDateWithFilter(String date, String filter, String secondFilter);
    String findPetFile(PetModel selectedPet, PetRepository petRepository);
    void updatePetDetails(PetModel pet, String name, String lastName, String breed, Double age, Double weight, Adress adress);
    List<PetModel> parsePetData(Map<String, List<String>> filesData);
    PetModel parseLineToPet(List<String> lines);
    boolean deletePetByNameAndLastName(String name, String lastName);
    String extractValue(List<String> lines, String prefix);
}
