package repository.interfaces;

import model.IE.PetSpec;
import model.PetModel;
import java.util.List;
import java.util.Map;

public interface IPetDAO {
    void savePetToFile(PetModel pet);

    Map<String, List<String>> getAllPets();

    Map<String, List<String>> getPetByFilter(PetSpec petSpec);

    Map<String, List<String>> getPetByFilter(String filter, String secondFilter);

    Map<String, List<String>> getPetByDate(String date);

    Map<String, List<String>> getPetByDateWithFilter(String date, String filter);

    Map<String, List<String>> getPetByDateWithFilter(String date, String filter, String secondFilter);

    void deletePetFile(String fileName);

    List<String> getAllFileNames();
}
