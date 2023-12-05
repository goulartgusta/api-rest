package br.com.anhembi.apiresttests.services.impl;

import br.com.anhembi.apiresttests.domain.Usuario;
import br.com.anhembi.apiresttests.domain.dto.UserDTO;
import br.com.anhembi.apiresttests.repositories.UserRepository;
import br.com.anhembi.apiresttests.services.exceptions.DataIntegratyViolationException;
import br.com.anhembi.apiresttests.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

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
        Mockito.when(repository.findById(anyInt())).thenReturn(optionalUser);

        Usuario response = service.findById(ID);// chamando método

        Assertions.assertNotNull(response);
        // assegure que vou receber usuario
        Assertions.assertEquals(Usuario.class, response.getClass());
        // Assegure que o ID, NAME e EMAIL criado seja igual a resposta recebida
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        // quando o método for chamado por qualquer int, retorna mensagem
        Mockito.when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado!"));

        try{
            // tente chamar o método
            service.findById(ID);
        }catch (Exception ex){
            // Assegure que essa exceção seja do tipo correto e se a mensagem é igual
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Objeto não encontrado!", ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        Mockito.when(repository.findAll()).thenReturn(List.of(user));

        List<Usuario> response = service.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(Usuario.class, response.get(0).getClass());

        Assertions.assertEquals(ID, response.get(0).getId());
        Assertions.assertEquals(NAME, response.get(0).getName());
        Assertions.assertEquals(EMAIL, response.get(0).getEmail());
        Assertions.assertEquals(PASSWORD, response.get(0).getPassword());

    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(repository.save(any())).thenReturn(user);

        Usuario response = service.create(userDTO); // Mockei o método e chamei ele aqui

        // Ess parte é onde asseguramos que o teste terá os resultados que queremos
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Usuario.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        Mockito.when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            // para assegurar se o e-mail será diferente, validamos quando diverge
            optionalUser.get().setId(2);
            service.create(userDTO);
        }catch (Exception ex){
            Assertions.assertEquals(DataIntegratyViolationException.class, ex.getClass());
            Assertions.assertEquals("E-mail já cadastrado no sistema", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(repository.save(any())).thenReturn(user);

        Usuario response = service.update(userDTO); // Mockei o método e chamei ele aqui

        // Ess parte é onde asseguramos que o teste terá os resultados que queremos
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Usuario.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        Mockito.when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            // para assegurar se o e-mail será diferente, validamos quando diverge
            optionalUser.get().setId(2);
            service.update(userDTO);
        }catch (Exception ex){
            Assertions.assertEquals(DataIntegratyViolationException.class, ex.getClass());
            Assertions.assertEquals("E-mail já cadastrado no sistema", ex.getMessage());
        }
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