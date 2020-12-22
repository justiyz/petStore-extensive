package com.petstore.data.repository;

import com.petstore.data.model.Store;
import com.petstore.web.exceptions.StoreDoesNotExistException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    default Store saveStore(Store store) throws StoreDoesNotExistException {
        if (isStoreValid(store)){
            save(store);
        }
        return store;
    }


    private boolean isStoreValid(Store store) throws StoreDoesNotExistException {
        if (! storeHasName(store)){
            throw new StoreDoesNotExistException("Name can not be null");
        }
        if (! storeHasLocation(store)){
            throw new StoreDoesNotExistException("Location can not be null");
        }
        if (! storeHasContact(store)){
            throw new StoreDoesNotExistException("Contact can not be null");
        }
        return true;
    }

    private boolean storeHasName(Store store){
        if (store.getName() == null){
            return false;
        }
        return true;
    }

    private boolean storeHasLocation(Store store){
        if (store.getLocation() == null){
            return false;
        }
        return true;
    }

    private boolean storeHasContact(Store store){
        if (store.getContactNo() == null){
            return false;
        }
        return true;
    }


    Store findByName(String name);
}
