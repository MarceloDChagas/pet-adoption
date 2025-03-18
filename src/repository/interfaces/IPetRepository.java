package repository.interfaces;

import model.PetModel;
import java.util.List;
import java.util.Map;

public interface IPetRepository {
    void savePetToFile(PetModel pet);

    Map<String, List<String>> getAllPets();

    Map<String, List<String>> getPetByFilter(String filter);

    Map<String, List<String>> getPetByFilter(String filter, String secondFilter);

    Map<String, List<String>> getPetByDate(String date);

    Map<String, List<String>> getPetByDateWithFilter(String date, String filter);

    Map<String, List<String>> getPetByDateWithFilter(String date, String filter, String secondFilter);

    boolean deletePetFile(String fileName);

    List<String> getAllFileNames();
}
