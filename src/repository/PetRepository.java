package repository;

import model.PetModel;
import repository.interfaces.IPetRepository;
import util.FileManage;
import util.interfaces.IFileManage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class PetRepository implements IPetRepository {
    private final IFileManage fileManage;
    private final String petsFilePath = System.getProperty("user.dir") + "\\src\\petsCadastrados\\";

    public PetRepository(IFileManage fileManage) {
        this.fileManage = fileManage;
    }

    public void savePetToFile(PetModel pet) {
        String formattedFileName = getFormattedFileName(pet);
        String filePath = "src/petsCadastrados/" + formattedFileName;

        String petInfo = "Nome: " + pet.getName() + "\n" +
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

        return dateTime + "-" + pet.getName().toUpperCase() + pet.getLastName().toUpperCase() + ".TXT";
    }

    public Map<String, List<String>> getAllPets() {
        return fileManage.loadFilesToMap(petsFilePath);
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

    private void listPet(Map<String, List<String>> filesData) {
        for (Map.Entry<String, List<String>> entry : filesData.entrySet()) {
            for (String line : entry.getValue()) {
                System.out.println(line);
            }
        }
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
        listPet(filesDataFiltered);
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
        listPet(filesDataFiltered);
        return filesDataFiltered;
    }


    public boolean deletePetFile(String fileName) {
        String fullPath = petsFilePath + fileName;
        File file = new File(fullPath);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Arquivo deletado com sucesso: " + fileName);
                return true;
            } else {
                System.out.println("Falha ao deletar o arquivo: " + fileName);
                return false;
            }
        } else {
            System.out.println("Arquivo não encontrado: " + fileName);
            return false;
        }
    }

    public List<String> getAllFileNames() {
        System.out.println("Buscando arquivos no diretório: " + petsFilePath);
        for (String fileName : fileManage.getAllFileNames(petsFilePath)) {
            System.out.println(fileName);
        }
        return fileManage.getAllFileNames(petsFilePath);
    }
}

