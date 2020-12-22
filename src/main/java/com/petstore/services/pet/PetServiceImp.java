package com.petstore.services.pet;

import com.petstore.data.model.Pet;
import com.petstore.data.repository.PetRepository;
import com.petstore.web.exceptions.PetDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PetServiceImp implements PetService {


    @Autowired
    PetRepository petRepository;


    @Override
    public Pet savePet(Pet pet) throws PetDoesNotExistException {
        if (pet == null){
            throw new PetDoesNotExistException("Pet Object cannot be null");
        }
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Pet pet) throws PetDoesNotExistException {
        checkPetBeforeUpdating(pet);
        return pet;
    }

    public Boolean checkPetBeforeUpdating(Pet pet) throws PetDoesNotExistException {
        Pet existingPet = petRepository.findById(pet.getId()).orElse(null);

        if (existingPet == null){
            throw new PetDoesNotExistException("Pet with the Id " +pet.getId()+ " does not exist");
        }
        if (pet.getName() != null){
            existingPet.setName(pet.getName());
        }
        if (pet.getAge() != null){
            existingPet.setAge(pet.getAge());
        }
        if (pet.getColor() != null){
            existingPet.setColor(pet.getColor());
        }
        if (pet.getBreed() != null){
            existingPet.setBreed(pet.getBreed());
        }
        if (pet.getPetSex() != null){
            existingPet.setPetSex(pet.getPetSex());
        }
        if (pet.getStore() != null){
            log.info("store -> {}", pet.getStore());
            existingPet.setStore(pet.getStore());
        }
        petRepository.savePet(existingPet);

        return true;
    }

    @Override
    public Pet findPetById(Integer id) throws PetDoesNotExistException {
        Pet pet = petRepository.findById(id).orElse(null);

        if(pet != null){
            return pet;
        } else {
            throw new PetDoesNotExistException("Pet with the Id:"+id+" Does not exist");
        }
    }

    @Override
    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    @Override
    public void deletePetById(Integer id) {
            petRepository.deleteById(id);

    }
}
