package repository;

import model.PetModel;
import util.FileManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PetRepository {
    private final FileManage fileManage;
    private final String petsFilePath = "C:\\Users\\Telo\\IdeaProjects\\pet-adoption\\src\\petsCadastrados\\"; // Caminho para o arquivo de objetos binários

    public PetRepository() {
        this.fileManage = new FileManage();
    }

    // Método para salvar o pet em um arquivo .txt com o nome formatado
    public void savePetToFile(PetModel pet) {
        String formattedFileName = getFormattedFileName(pet);
        String filePath = "petsCadastrados/" + formattedFileName; // Caminho completo do arquivo

        // Criação do conteúdo do arquivo (informações do pet)
        String petInfo = "Nome: " + pet.getName() + "\n " +
                "Sobrenome: " + pet.getLastName() + "\n" +
                "Raça: " + pet.getBreed() + "\n" +
                "Tipo: " + pet.getType() + "\n" +
                "Sexo: " + pet.getSex() + "\n" +
                "Idade: " + pet.getAge() + "\n" +
                "Peso: " + pet.getWeight() + "\n" +
                "Endereço: " + pet.getAdress().toString(); // Aqui você pode customizar o endereço como quiser

        // Salva o conteúdo no arquivo .txt
        fileManage.writeFile(filePath, petInfo);
    }

    // Método para gerar o nome formatado do arquivo com base na data e no nome do pet
    private String getFormattedFileName(PetModel pet) {
        // Obtém a data e hora atual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmm");
        String dateTime = sdf.format(new Date());

        // Formata o nome do arquivo
        String fileName = dateTime + "-" + pet.getName().toUpperCase() + pet.getLastName().toUpperCase() + ".TXT";

        return fileName;
    }

    // Método para obter todos os pets do arquivo
    public List<PetModel> getAllPets() {
        List<PetModel> pets = fileManage.readObjectFile(petsFilePath); // Lê a lista de pets do arquivo
        if (pets == null) {
            pets = new ArrayList<>(); // Se não houver nenhum pet, cria uma lista vazia
        }
        return pets;
    }

    // Método para verificar se o pet já está cadastrado
    public boolean isPetRegistered(PetModel pet) {
        List<PetModel> pets = getAllPets();
        for (PetModel existingPet : pets) {
            // Você pode usar algum critério único, como o nome, para verificar se o pet já está registrado
            if (existingPet.getName().equals(pet.getName())) {
                return true;
            }
        }
        return false;
    }
}
