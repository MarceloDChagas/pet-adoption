import model.*;
import repository.PetRepository;
import service.PetService;
import util.Adress;
import util.PetType;
import util.PetSex;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PetService petService = new PetService();
        petService.findAllPets();
        petService.findPet("fLor");
        petService.findPet("Da Silva", "Poodle");
        petService.findPetByDate("20250313");
        String dataFiltro = "20250314";
        String date = "20250314";
        List<PetModel> petsFiltrados = petService.findPetByDate(dataFiltro);

        if (petsFiltrados.isEmpty()) {
            System.out.println("Nenhum pet encontrado para a data: " + dataFiltro);
        } else {
            System.out.println("Pets encontrados para a data " + dataFiltro + ":");
            int index = 1;
            for (PetModel pet : petsFiltrados) {
                System.out.println(index + ". " + pet.toString());
                index++;
            }
        }
        // Teste 1: Filtrar pets pela data e um filtro (por exemplo, filtrar pelo termo "Poodle")
        String filter = "Poodle";
        List<PetModel> petsWithOneFilter = petService.findPetByDateWithFilter(date, filter);

        System.out.println("Resultado de findPetByDateWithFilter(date, filter): ");
        if (petsWithOneFilter.isEmpty()) {
            System.out.println("Nenhum pet encontrado para a data " + date + " com o filtro " + filter);
        } else {
            int index = 1;
            for (PetModel pet : petsWithOneFilter) {
                System.out.println(index + ". " + pet.toString());
                index++;
            }
        }

        // Teste 2: Filtrar pets pela data com dois filtros (por exemplo, filtrar pelo termo "Poodle" e "FEMALE")
        String secondFilter = "FEMALE";
        List<PetModel> petsWithTwoFilters = petService.findPetByDateWithFilter(date, filter, secondFilter);

        System.out.println("\nResultado de findPetByDateWithFilter(date, filter, secondFilter): ");
        if (petsWithTwoFilters.isEmpty()) {
            System.out.println("Nenhum pet encontrado para a data " + date
                    + " com os filtros " + filter + " e " + secondFilter);
        } else {
            int index = 1;
            for (PetModel pet : petsWithTwoFilters) {
                System.out.println(index + ". " + pet.toString());
                index++;
            }
        }
    }
}
