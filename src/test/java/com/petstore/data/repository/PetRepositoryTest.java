package com.petstore.data.repository;

import com.petstore.data.model.Gender;
import com.petstore.data.model.Pet;
import com.petstore.data.model.Store;
import com.petstore.web.exceptions.PetDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Sql(scripts = {"classpath:db/insert.sql"})
@Slf4j
@SpringBootTest
class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Autowired
    StoreRepository storeRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Rollback(value = false)
    @Transactional
    public void whenStoreIsMappedToPet_thenForeignKeyIsPresent(){

        Pet pet = new Pet();
        pet.setName("Jack");
        pet.setAge(2);
        pet.setBreed("Dog");
        pet.setColor("Black");
        pet.setPetSex(Gender.MALE);

        log.info("pet instance before adding --> {}", pet);

        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("09099887766");

        pet.setStore(store);

        petRepository.save(pet);

        log.info("Pet instance after adding --> {}", pet);
        log.info("Store instance after adding --> {}", store);

        assertThat(pet.getId()).isNotNull();
        assertThat(store.getId()).isNotNull();
        assertThat(pet.getStore()).isNotNull();
    }


    @Test
    @Rollback(value = false)
    @Transactional
    void mapANewPetToAnExistingStore(){

        Store store = storeRepository.findById(5).orElse(null);
        assertNotNull(store);

        Pet pet = new Pet();
        pet.setName("JAYBACK");
        pet.setAge(4);
        pet.setBreed("Rabbit");
        pet.setColor("Blue");
        pet.setPetSex(Gender.FEMALE);
        pet.setStore(store);
        petRepository.save(pet);

        assertThat(pet.getId()).isNotNull();
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void whenIAddPetsToAStore_thenICanFetchAListOfPetsFromStore(){

        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("09099887766");

        Pet jack = new Pet();
        jack.setName("Jack");
        jack.setAge(5);
        jack.setBreed("Dog");
        jack.setColor("Black");
        jack.setPetSex(Gender.MALE);
        jack.setStore(store);

        Pet sally = new Pet();
        sally.setName("Sally");
        sally.setAge(2);
        sally.setBreed("Dog");
        sally.setColor("Brown");
        sally.setPetSex(Gender.FEMALE);
        sally.setStore(store);

        log.info("Pet instances after adding --> {}", jack, sally);

        store.addPets(jack);
        store.addPets(sally);

        storeRepository.save(store);

        log.info("Store instance after saving --> {}", store);

        assertThat(jack.getId()).isNotNull();

        assertThat(sally.getId()).isNotNull();

        assertThat(store.getPetList());

    }


    @Test
    public void whenFindAllPetIsCalled_thenReturnAllPetsInStore(){

        List<Pet> savedPets = petRepository.findAll();
        log.info("Pets list --> {}", savedPets);
        assertThat(savedPets).isNotNull() ;
        assertThat(savedPets.size()).isEqualTo(7);

    }

    @Test
    public void updateExistingPetDetailsTest(){

      Pet sally = petRepository.findById(34).orElse(null);
        assertThat(sally).isNotNull();
        assertThat(sally.getColor()).isEqualTo("brown");

        sally.setColor("purple");

        petRepository.save(sally);

        log.info("After updating pet object --> {}", sally);
        assertThat(sally.getColor()).isEqualTo("purple");
    }


    @Test
    public void whenIDeletePetFromDatabase_thenPetIsDeleted(){

        boolean sally = petRepository.existsById(35);

        assertThat(sally).isTrue();

        petRepository.deleteById(35);
        assertThat(petRepository.existsById(35)).isFalse();


    }

    @Test
    public void whenIDeletePetFromDatabaseAndPetDoesntExist_thenPetIsNotDeleted(){
        try {
            petRepository.deleteById(39);
            assertThat(petRepository.existsById(39)).isFalse();
        } catch (Exception ex){
            log.info("Pet doesnt not exist exception was thrown --> {}", ex.getMessage());
        }


    }
    }


