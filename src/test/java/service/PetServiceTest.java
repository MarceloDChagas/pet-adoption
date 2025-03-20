package service;

import model.PetModel;
import model.VO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import repository.File.PetFileDAO;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @InjectMocks
    private PetFileDAO petFileDAO;

    PetModel pet;

    @BeforeEach
    void setUp() {
        pet = new PetModel(new Name("Rex"), new LastName("Silva"), PetType.DOG, PetSex.MALE, new Age(2.0), new Weight(5), new Address("123", "São Paulo", "Rua A"), new Breed("Caramelo") );
    }

    @Test
    void shouldCreatePet() {
        petService.createPet(pet);
    }
}
