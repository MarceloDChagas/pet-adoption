package repository;

import model.PetModel;
import util.FileManage;

import java.text.SimpleDateFormat;
import java.util.*;

public class PetRepository {
    private final FileManage fileManage;
    private final String petsFilePath = "C:\\Users\\Telo\\IdeaProjects\\pet-adoption\\src\\petsCadastrados\\"; // Caminho para o arquivo de objetos binários

    public PetRepository() {
        this.fileManage = new FileManage();
    }

    public void savePetToFile(PetModel pet) {
        String formattedFileName = getFormattedFileName(pet);
        String filePath = "petsCadastrados/" + formattedFileName;

        String petInfo = "Nome: " + pet.getName() + "\n " +
                "Sobrenome: " + pet.getLastName() + "\n" +
                "Raça: " + pet.getBreed() + "\n" +
                "Tipo: " + pet.getType() + "\n" +
                "Sexo: " + pet.getSex() + "\n" +
                "Idade: " + pet.getAge() + "\n" +
                "Peso: " + pet.getWeight() + "\n" +
                "Endereço: " + pet.getAdress().toString();

        fileManage.writeFile(filePath, petInfo);
    }

    private String getFormattedFileName(PetModel pet) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmm");
        String dateTime = sdf.format(new Date());
        String fileName = dateTime + "-" + pet.getName().toUpperCase() + pet.getLastName().toUpperCase() + ".TXT";

        return fileName;
    }

    public Map<String, List<String>> getAllPets() {
        Map<String, List<String>> filesData = fileManage.loadFilesToMap(petsFilePath);
        return filesData;
    }

    public Map<String, List<String>> getPetByFilter(String filter) {
        Map<String, List<String>> filesData = fileManage.loadFilesToMap(petsFilePath);
        Map<String, List<String>> filesDataFiltered = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : filesData.entrySet()) {
            for (String line : entry.getValue()) {
                if (line.toLowerCase().contains(filter.toLowerCase())) {
                    filesDataFiltered.put(entry.getKey(), entry.getValue());
                    break;
                }
            }
        }
        return filesDataFiltered;
    }

    public Map<String, List<String>> getPetByFilter(String filter, String secondFilter) {
        Map<String, List<String>> filesData = fileManage.loadFilesToMap(petsFilePath);
        Map<String, List<String>> filesDataFiltered = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : filesData.entrySet()) {
            for (String line : entry.getValue()) {
                if (line.toLowerCase().contains(filter.toLowerCase()) && line.toLowerCase().contains(secondFilter.toLowerCase())) {
                    filesDataFiltered.put(entry.getKey(), entry.getValue());
                    break;
                }
            }
        }
        return filesDataFiltered;
    }

    public Map<String, List<String>> getPetByDate(String date) {
        Map<String, List<String>> filesData = fileManage.loadFilesToMap(petsFilePath);
        Map<String, List<String>> filesDataFiltered = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : filesData.entrySet()) {
            if (entry.getKey().startsWith(date)) {
                filesDataFiltered.put(entry.getKey(), entry.getValue());
            }
        }
        return filesDataFiltered;
    }

    public Map<String, List<String>> getPetByDateWithFilter(String date, String filter) {
        Map<String, List<String>> filesData = fileManage.loadFilesToMap(petsFilePath);
        Map<String, List<String>> filesDataFiltered = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : filesData.entrySet()) {
            if (entry.getKey().startsWith(date)) {
                for (String line : entry.getValue()) {
                    if (line.toLowerCase().contains(filter.toLowerCase())) {
                        filesDataFiltered.put(entry.getKey(), entry.getValue());
                        break;
                    }
                }
            }
        }
        return filesDataFiltered;
    }

    public Map<String, List<String>> getPetByDateWithFilter(String date, String filter, String secondFilter) {
        Map<String, List<String>> filesData = fileManage.loadFilesToMap(petsFilePath);
        Map<String, List<String>> filesDataFiltered = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : filesData.entrySet()) {
            if (entry.getKey().startsWith(date)) {
                for (String line : entry.getValue()) {
                    if (line.toLowerCase().contains(filter.toLowerCase()) && line.toLowerCase().contains(secondFilter.toLowerCase())) {
                        filesDataFiltered.put(entry.getKey(), entry.getValue());
                        break;
                    }
                }
            }
        }
        return filesDataFiltered;
    }

}
