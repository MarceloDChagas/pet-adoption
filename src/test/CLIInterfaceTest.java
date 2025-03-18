package test;

import DI.DependencyContainer;
import model.PetModel;
import model.VO.*;
import org.junit.jupiter.api.*;
import service.interfaces.IPetService;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CLIInterfaceTest {
    private static IPetService petService;
    private static PetModel testPet;

    @BeforeAll
    static void setup() {
        petService = DependencyContainer.getPetService();
    }

    @BeforeEach
    void addTestPet() {
        // Criação de um pet para ser usado nos testes
        testPet = new PetModel(
                new Name("Rex"),
                new LastName("Silva"),
                PetType.DOG,
                PetSex.MALE,
                new Age(3),
                new Weight(15.2f),
                new Adress("12", "São Paulo", "Rua A"),
                new Breed("Labrador")
        );

        petService.createPet(
                testPet.getName(),
                testPet.getLastName(),
                testPet.getType(),
                testPet.getSex(),
                String.valueOf(testPet.getAge()),
                String.valueOf(testPet.getWeight()),
                testPet.getAdress(),
                testPet.getBreed()
        );
    }

    @AfterEach
    void cleanup() {
        // Remove o pet criado após cada teste para evitar poluição de dados
        petService.deletePetByNameAndLastName(testPet.getName(), testPet.getLastName());
    }

    @Test
    @Order(1)
    void testAddPet() {
        // Testa se o pet foi realmente adicionado
        List<PetModel> pets = petService.findPet("Rex");
        assertFalse(pets.isEmpty(), "Pet should be added successfully.");
        assertEquals("Rex", pets.get(0).getName(), "The pet name should be Rex.");
    }

    @Test
    @Order(2)
    void testFindAllPets() {
        // Testa se há pelo menos um pet cadastrado
        List<PetModel> pets = petService.findAllPets();
        assertFalse(pets.isEmpty(), "There should be at least one pet in the system.");
    }

    @Test
    @Order(3)
    void testFindPetByFilter() {
        // Testa busca por nome e raça
        List<PetModel> pets = petService.findPet("This pet doesnt exist", "This pet doesnt exist");
        System.out.println(pets);
        assertTrue(pets.isEmpty(), "No pet should be found with the non-existing name and breed.");
    }

    @Test
    @Order(4)
    void testDeletePet() {
        // Deleta o pet e verifica se foi realmente removido
        boolean deleted = petService.deletePetByNameAndLastName(testPet.getName(), testPet.getLastName());
        assertTrue(deleted, "Pet should be deleted successfully.");

        List<PetModel> petsAfterDelete = petService.findPet("Rex");
        assertTrue(petsAfterDelete.isEmpty(), "Pet should no longer exist in the system.");
    }
}
