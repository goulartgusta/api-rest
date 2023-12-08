package br.com.anhembi.apiresttests.services.impl;
import br.com.anhembi.apiresttests.domain.Usuario;
import br.com.anhembi.apiresttests.domain.dto.UserDTO;
import br.com.anhembi.apiresttests.repositories.UserRepository;
import br.com.anhembi.apiresttests.services.UserService;
import br.com.anhembi.apiresttests.services.exceptions.DataIntegratyViolationException;
import br.com.anhembi.apiresttests.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional

public class UserServiceImplIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        // Crie um usuário de teste
        Usuario user = new Usuario();
        user.setName("Test User");
        user.setEmail("test@example.com");
        userRepository.save(user);

        // Execute o método findById
        Usuario foundUser = userService.findById(user.getId());

        // Verifique se o usuário foi encontrado corretamente
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testFindAll() {
        // Crie alguns usuários de teste
        Usuario user1 = new Usuario();
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        userRepository.save(user1);

        Usuario user2 = new Usuario();
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        userRepository.save(user2);

        // Execute o método findAll
        List<Usuario> userList = userService.findAll();

        // Verifique se a lista não está vazia e contém os usuários criados
        assertFalse(userList.isEmpty());
        assertTrue(userList.contains(user1));
        assertTrue(userList.contains(user2));
    }

    @Test
    public void testCreate() {
        // Crie um DTO de usuário de teste
        UserDTO userDTO = new UserDTO();
        userDTO.setName("New User");
        userDTO.setEmail("newuser@example.com");

        // Execute o método create
        Usuario createdUser = userService.create(userDTO);

        // Verifique se o usuário foi criado corretamente
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(userDTO.getName(), createdUser.getName());
        assertEquals(userDTO.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testUpdate() {
        // Crie um usuário de teste
        Usuario user = new Usuario();
        user.setName("Test User");
        user.setEmail("test@example.com");
        userRepository.save(user);

        // Atualize as informações do usuário
        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setId(user.getId());
        updatedUserDTO.setName("Updated User");
        updatedUserDTO.setEmail("updateduser@example.com");

        // Execute o método update
        Usuario updatedUser = userService.update(updatedUserDTO);

        // Verifique se o usuário foi atualizado corretamente
        assertNotNull(updatedUser);
        assertEquals(updatedUserDTO.getName(), updatedUser.getName());
        assertEquals(updatedUserDTO.getEmail(), updatedUser.getEmail());
    }

    @Test
    public void testDelete() {
        // Crie um usuário de teste
        Usuario user = new Usuario();
        user.setName("Test User");
        user.setEmail("test@example.com");
        userRepository.save(user);

        // Execute o método delete
        userService.delete(user.getId());

        // Verifique se o usuário foi excluído
        assertThrows(ObjectNotFoundException.class, () -> userService.findById(user.getId()));
    }

    @Test
    public void testFindByEmail() {
        // Crie um usuário de teste
        Usuario user = new Usuario();
        user.setName("Test User");
        user.setEmail("test@example.com");
        userRepository.save(user);

        // Crie um DTO de usuário com o mesmo e-mail (deve lançar exceção)
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");

        // Verifique se a exceção DataIntegratyViolationException é lançada
        assertThrows(DataIntegratyViolationException.class, () -> userService.findByEmail(userDTO));
    }
}
