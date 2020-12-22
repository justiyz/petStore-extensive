package com.petstore.web.controllers.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.data.model.Store;
import com.petstore.services.pet.PetService;
import com.petstore.services.store.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.transaction.Transactional;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/insert.sql"})
class StoreRestControllerTest {

    @Autowired
    StoreService storeService;

    @Autowired
    PetService petService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper;

    Store store;



    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        store = new Store();
    }


    @Test
    @Transactional
    @Rollback(value = false)
    void testThatWeCanCallTheCreateStoreEndpoint() throws Exception {
        store.setName("New Store");
        store.setContactNo("09099887766");
        store.setLocation("Abidjan");
        storeService.saveStore(store);


        this.mockMvc.perform(post("/store/create")
                .contentType("application/json")
                .content(mapper.writeValueAsString(store)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }


    @Test
    void testThatWeCanCallTheFindAllStoresEndpoint() throws Exception {
        this.mockMvc.perform(get("/store/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    void testThatWeCanCallTheFindStoreByIdEndpoint() throws Exception {
        this.mockMvc.perform(get("/store/find/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    void testThatWeCanCallTheUpdateStoreEndpoint() throws Exception {
        store.setId(3);;
        store.setLocation("Cohort7");
        store.setContactNo("+1-345-876-322");

        this.mockMvc.perform(post("/store/update")
                .contentType("application/json")
                .content(mapper.writeValueAsString(store)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    void testThatWeCanCallTheDeleteStoreByIdEndpoint() throws Exception {
        this.mockMvc.perform(delete("/store/delete/2"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
    }


}