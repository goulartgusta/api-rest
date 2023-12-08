package br.com.anhembi.apiresttests.resources;

import br.com.anhembi.apiresttests.domain.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    int userId = 1;

    @Test
    void testFindById() throws Exception {


        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));
    }
    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreate() throws Exception {
        UserDTO userDTO = new UserDTO(null, "Teste", "Teste@example.com", "Teste123");

        MvcResult result = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertNotNull(location);

        // Extrair o ID do usuário do cabeçalho Location e realizar assertivas adicionais
        String[] segments = location.split("/");
        int createdUserId = Integer.parseInt(segments[segments.length - 1]);

        // Realizar assertivas adicionais, se necessário
        mockMvc.perform(get("/user/{id}", createdUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdUserId))
                .andExpect(jsonPath("$.name").value(userDTO.getName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));
    }

    @Test
    void testUpdate() throws Exception {
        // Crie um usuário para ser atualizado
        UserDTO userDTO = new UserDTO(null, "John Doe", "john.doe@example.com", "password123");
        MvcResult createResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String location = createResult.getResponse().getHeader("Location");
        assertNotNull(location);

        // Extrair o ID do usuário do cabeçalho Location
        String[] segments = location.split("/");
        int createdUserId = Integer.parseInt(segments[segments.length - 1]);

        // Atualizar o usuário criado
        UserDTO updatedUserDTO = new UserDTO(createdUserId, "Updated Name", "updated.email@example.com", "newpassword");

        mockMvc.perform(put("/user/{id}", createdUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdUserId))
                .andExpect(jsonPath("$.name").value(updatedUserDTO.getName()))
                .andExpect(jsonPath("$.email").value(updatedUserDTO.getEmail()));

        // Verificar se o usuário foi realmente atualizado
        mockMvc.perform(get("/user/{id}", createdUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdUserId))
                .andExpect(jsonPath("$.name").value(updatedUserDTO.getName()))
                .andExpect(jsonPath("$.email").value(updatedUserDTO.getEmail()));
    }

    @Test
    void testDelete() throws Exception {


        mockMvc.perform(delete("/user/{id}", userId))
                .andExpect(status().isNoContent());
    }
}