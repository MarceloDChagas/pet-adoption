import model.*;
import repository.PetRepository;
import service.PetService;
import util.Adress;
import util.PetType;
import util.PetSex;

public class Main {
    public static void main(String[] args) {
        PetService petService = new PetService();
        petService.findAllPets();
        petService.findPet("fLor");
    }
}
