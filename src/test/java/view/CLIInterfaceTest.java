package view;

import DI.DependencyContainer;
import model.IE.PetSpec;
import model.PetModel;
import model.VO.*;
import service.interfaces.IPetService;

import java.util.List;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CLIInterfaceTest {
    private static IPetService petService;
    private PetModel testPet;

    @BeforeAll
    static void setup() {
        petService = DependencyContainer.getPetService();
    }

    @BeforeEach
    void addTestPet() {
        testPet = createTestPet();
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
        petService.deletePetByNameAndLastName(testPet.getName(), testPet.getLastName());
    }

    @Test
    @Order(1)
    void testAddPet() {
        List<PetModel> pets = petService.findPetBySpec(buildPetSpec("Rex"));
        assertFalse(pets.isEmpty(), "Pet should be added successfully.");
        assertEquals("Rex", pets.getFirst().getName(), "The pet name should be Rex.");
    }

    @Test
    @Order(2)
    void testFindAllPets() {
        List<PetModel> pets = petService.findAllPets();
        assertFalse(pets.isEmpty(), "There should be at least one pet in the system.");
    }

    @Test
    @Order(3)
    void testFindPetByFilter() {
        List<PetModel> pets = petService.findPetBySpec(buildPetSpec("UNNAMED PET"));
        assertTrue(pets.isEmpty(), "No pet should be found with the non-existing name.");
    }

    @Test
    @Order(4)
    void testDeletePet() {
        boolean deleted = petService.deletePetByNameAndLastName(testPet.getName(), testPet.getLastName());
        assertTrue(deleted, "Pet should be deleted successfully.");
    }

    private PetModel createTestPet() {
        return new PetModel(
                new Name("Rex"),
                new LastName("Silva"),
                PetType.DOG,
                PetSex.MALE,
                new Age(3.0),
                new Weight(15.2),
                new Address("12", "São Paulo", "Rua A"),
                new Breed("Labrador")
        );
    }

    private PetSpec buildPetSpec(String name) {
        return new PetSpec.Builder().setName(new Name(name)).build();
    }
}
