import model.*;
import repository.PetRepository;
import util.Adress;
import util.PetType;
import util.PetSex;

public class Main {
    public static void main(String[] args) {
        PetModel pet = new PetModel("Florzinha", "Da Silva", PetType.DOG, PetSex.FEMALE, 2, 4.5f, new Adress("123", "Belo jardim", "Centro"), "Poodle");
        PetRepository petRepository = new PetRepository();
        petRepository.savePetToFile(pet); // Salva o pet em um arquivo .txt com o nome formatado

    }
}
