package br.com.anhembi.apiresttests.services.impl;

import br.com.anhembi.apiresttests.domain.Usuario;
import br.com.anhembi.apiresttests.domain.dto.UserDTO;
import br.com.anhembi.apiresttests.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Gus";
    public static final String EMAIL = "gus@gmail.com";
    public static final String PASSWORD = "123";
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;
    private Usuario user;
    private UserDTO userDTO;
    private Optional<Usuario> optionalUser;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this); // se refere a classe que estamos testando
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        // quando o método for chamado por qualquer int, retorna resposta realizada abaixo do usuario
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        Usuario response = service.findById(ID);// chamando método

        assertNotNull(response);
        // assegure que vou receber usuario
        assertEquals(Usuario.class, response.getClass());
        // Assegure que o ID, name e email criado seja igual a resposta recebida
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByEmail() {
    }

    private void startUser(){
        user = new Usuario(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new Usuario(ID, NAME, EMAIL, PASSWORD)); //variável fatorada para constante
    }
}