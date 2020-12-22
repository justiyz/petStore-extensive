package com.petstore.services.store;


import com.petstore.data.model.Store;

import com.petstore.web.exceptions.StoreDoesNotExistException;

import java.util.List;

public interface StoreService {

    Store saveStore(Store store);

    Store updateStore(Store store) throws StoreDoesNotExistException;

    Store findStoreById(Integer id);
    void deleteStoreById(Integer id);
    List<Store> findAllStores();
}
