package com.petstore.services.store;

import com.petstore.data.model.Store;
import com.petstore.data.repository.StoreRepository;
import com.petstore.services.store.StoreService;
import com.petstore.web.exceptions.StoreDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Override
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Store store) throws StoreDoesNotExistException {
        if (confirmStoreBeforeUpdating(store)){
            storeRepository.save(store);
        }
        return store;
    }

    public Boolean confirmStoreBeforeUpdating(Store store) throws StoreDoesNotExistException {
        Store existingStore = storeRepository.findById(store.getId()).orElse(null);

        if (store.getContactNo() != null){
            existingStore.setContactNo(store.getContactNo());
        }
        if (store.getLocation() != null){
            existingStore.setLocation(store.getLocation());
        }
        if (store.getName() != null){
            existingStore.setName(store.getName());
        }
        storeRepository.save(existingStore);
        return true;
    }


    @Override
    public Store findStoreById(Integer id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteStoreById(Integer id) {
        storeRepository.deleteById(id);
    }

    @Override
    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }
}
