package com.mfan.addressbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfan.addressbook.model.AddressBook;
import com.mfan.addressbook.model.BuddyInfo;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests AddressBookRestController.
 *
 * @author Michael Fan 101029934
 */

@SpringBootTest
@AutoConfigureMockMvc
public class AddressBookTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testAddAddressBook() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                .post("/addressbook/new")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(2)
    public void testGetAddressBook() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/addressbook/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(3)
    public void addBuddyInfo() throws Exception {
        BuddyInfo buddyInfo = new BuddyInfo("buddy", "phoneNumber", "address");

        this.mockMvc.perform( MockMvcRequestBuilders
                .post("/addressbook/1/addBuddy")
                .content(asJsonString(buddyInfo))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(4)
    public void removeBuddyInfo() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                .delete("/addressbook/1/removeBuddy/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
