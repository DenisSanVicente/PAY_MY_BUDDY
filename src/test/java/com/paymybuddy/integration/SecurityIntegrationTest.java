package com.paymybuddy.integration;

import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;


    // ===== DEMARRAGE DE L'APPLICATION ===== //
    @Test
    void contextLoads() {

    }


    // ===== TESTS AVEC UN UTILISATEUR DECONNECTE ===== //
    @Test
    void loginPage_shouldBeAccessibleWithoutAuthentification() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }


    @Test
    void registerPage_shouldBeAccessibleWithoutAuthentification() throws Exception {

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
    }


    @Test
    void transferPage_shouldRedirectToLoginWhenNotAuthenticated() throws Exception {

        mockMvc.perform(get("/transfer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }


    // ===== TESTS AVEC UN UTILISATEUR CONNECTE ===== //
    @Test
    @WithMockUser(username = "denis@test.fr")
    void addConnectionPage_shouldBeAccessibleWhenAuthenticated() throws Exception {

        mockMvc.perform(get("/add-connection"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-connection"));
    }

    @Test
    @WithMockUser(username = "denis@test.fr")
    void profilePage_shouldBeAccessibleWhenAuthenticated() throws Exception {

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }

    @Test
    @WithMockUser(username = "denis@test.fr")
    void transferPage_shouldBeAccessibleWhenAuthenticated() throws Exception {

        mockMvc.perform(get("/transfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("transfer"));
    }
}
