package com.petstore.web.controllers.pet;

import com.petstore.data.model.Pet;
import com.petstore.services.pet.PetService;
import com.petstore.web.exceptions.PetDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
@Slf4j
public class PetRestController {

    @Autowired
    PetService petService;

    @PostMapping("/create")
    public ResponseEntity<?> savePet(@RequestBody Pet pet){

        log.info("Request object --> {}", pet);

        try{
            petService.savePet(pet);
        }catch (PetDoesNotExistException exe){
            return ResponseEntity.badRequest().body(exe.getMessage());
        }

        return new ResponseEntity<>(pet, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePet(@RequestBody Pet pet) throws PetDoesNotExistException {
        petService.updatePet(pet);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<?> findAllPets(){
        log.info("Get endpoint called");
        List<Pet> petList = petService.findAllPets();

        log.info("Retrieved pets from database --> {}", petList);
        return ResponseEntity.ok().body(petList);
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<?> findOnePet(@PathVariable("id") Integer id){
        log.info("Id of pets to be found --> {}", id);
        Pet pet;
        try{
            pet = petService.findPetById(id);
        } catch (PetDoesNotExistException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(pet);
    }


    @DeleteMapping("/one/{id}")
    public ResponseEntity<?> deletePet(@PathVariable("id") Integer id){
        try {
            petService.deletePetById(id);
        } catch (PetDoesNotExistException pex){
            return ResponseEntity.badRequest().body(pex.getMessage());
        }
        log.info("Id of pets to be found --> {}", id);
        return ResponseEntity.noContent().build();
    }



}










