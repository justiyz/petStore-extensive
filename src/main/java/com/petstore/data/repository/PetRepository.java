package com.petstore.data.repository;

import com.petstore.data.model.Pet;
import com.petstore.web.exceptions.PetDoesNotExistException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    default Pet savePet(Pet pet) throws PetDoesNotExistException {
        if (isValid(pet)){
            save(pet);
        }
        return pet;
    }


    private boolean isValid(Pet pet) throws PetDoesNotExistException {
        if (!petHasName(pet)){
            throw new PetDoesNotExistException("Name can not be null");
        }
        if (!petHasAge(pet)){
            throw new PetDoesNotExistException("Age can not be null");
        }
        if (!petHasBreed(pet)){
            throw new PetDoesNotExistException("Breed can not be null");
        }
        if (!petHasColor(pet)){
            throw new PetDoesNotExistException("Color can not be null");
        }
        if (!petHasPetSex(pet)){
            throw new PetDoesNotExistException("Sex can not be null");
        }
        if (!petHasStore(pet)){
            throw new PetDoesNotExistException("Store can not be null");
        }
        return true;
    }


    private boolean petHasName(Pet pet){
        if (pet.getName() == null){
            return false;
        }
        return true;
    }

    private boolean petHasAge(Pet pet){
        if (pet.getAge() == null){
            return false;
        }
        return true;
    }

    private boolean petHasBreed(Pet pet){
        if(pet.getBreed() == null){
            return false;
        }
        return true;
    }

    private boolean petHasColor(Pet pet){
        if (pet.getColor() == null){
            return false;
        }
        return true;
    }

    private boolean petHasPetSex(Pet pet){
        if (pet.getPetSex() == null){
            return false;
        }
        return true;
    }

    private boolean petHasStore(Pet pet){
        if (pet.getStore() == null){
            return false;
        }
        return true;
    }

}

