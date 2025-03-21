package repository.File;

import model.IE.PetSpec;
import model.PetModel;
import repository.interfaces.IPetDAO;
import util.interfaces.IFileManage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class PetFileDAO implements IPetDAO {
    private final IFileManage fileManage;
    private final String petsFilePath = System.getProperty("user.dir") + "\\src\\petsCadastrados\\";

    public PetFileDAO(IFileManage fileManage) {
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

    /**
     * Filtra os pets baseado nos parâmetros do PetSpec.
     * @param petSpec O objeto PetSpec contendo os parâmetros de filtro
     * @return Um mapa contendo os pets filtrados
     */
    public Map<String, List<String>> getPetByFilter(PetSpec petSpec) {
        Map<String, List<String>> filesData = fileManage.loadFilesToMap(petsFilePath);
        Map<String, List<String>> filesDataFiltered = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : filesData.entrySet()) {
            for (String line : entry.getValue()) {
                if (matchesAnyParameter(line, petSpec)) {
                    filesDataFiltered.put(entry.getKey(), entry.getValue());
                    break;
                }
            }
        }
        return filesDataFiltered;
    }

    /**
     * Verifica se uma linha corresponde a qualquer um dos parâmetros do PetSpec.
     * @param line A linha a ser verificada
     * @param petSpec O objeto PetSpec contendo os parâmetros de filtro
     * @return true se a linha corresponder a algum dos parâmetros, false caso contrário
     */
    private boolean matchesAnyParameter(String line, PetSpec petSpec) {
        if (petSpec == null) {
            return false;
        }

        // Convertemos a linha para minúsculo para fazer comparações case-insensitive
        String lowerCaseLine = line.toLowerCase();

        // Verifica nome
        if (petSpec.getName() != null && petSpec.getName().getName() != null) {
            String name = petSpec.getName().getName().toLowerCase();
            if (!name.isEmpty() && lowerCaseLine.contains(name)) {
                return true;
            }
        }

        // Verifica sobrenome
        if (petSpec.getLastName() != null && petSpec.getLastName().getLastName() != null) {
            String lastName = petSpec.getLastName().getLastName().toLowerCase();
            if (!lastName.isEmpty() && lowerCaseLine.contains(lastName)) {
                return true;
            }
        }

        // Verifica idade
        if (petSpec.getAge() != null && petSpec.getAge().getAge() != null) {
            String age = petSpec.getAge().getAge().toString().toLowerCase();
            if (!age.isEmpty() && lowerCaseLine.contains(age)) {
                return true;
            }
        }

        // Verifica peso
        if (petSpec.getWeight() != null && petSpec.getWeight().getWeight() != null) {
            String weight = petSpec.getWeight().getWeight().toString().toLowerCase();
            if (!weight.isEmpty() && lowerCaseLine.contains(weight)) {
                return true;
            }
        }

        // Verifica raça
        if (petSpec.getBreed() != null && petSpec.getBreed().getBreed() != null) {
            String breed = petSpec.getBreed().getBreed().toLowerCase();
            if (!breed.isEmpty() && lowerCaseLine.contains(breed)) {
                return true;
            }
        }

        // Verifica endereço
        if (petSpec.getAddress() != null) {
            String address = petSpec.getAddress().toString().toLowerCase();
            if (!address.isEmpty() && lowerCaseLine.contains(address)) {
                return true;
            }
        }

        return false;
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


    public void deletePetFile(String fileName) {
        String fullPath = petsFilePath + fileName;
        File file = new File(fullPath);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Arquivo deletado com sucesso: " + fileName);
            } else {
                System.out.println("Falha ao deletar o arquivo: " + fileName);
            }
        } else {
            System.out.println("Arquivo não encontrado: " + fileName);
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

