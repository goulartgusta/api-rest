package br.com.anhembi.apiresttests.resources;

import br.com.anhembi.apiresttests.domain.Usuario;
import br.com.anhembi.apiresttests.domain.dto.UserDTO;
import br.com.anhembi.apiresttests.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class UserResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Gus";
    public static final String EMAIL = "gus@gmail.com";
    public static final String PASSWORD = "123";
    private Usuario user;
    private UserDTO userDTO;

    @InjectMocks
    private UserResource resource;
    @Mock
    private UserServiceImpl service;
    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSucces() {
        //mockando quando procurarmos uma ID qualquer retorna o user da ID
        Mockito.when(service.findById(anyInt())).thenReturn(user);
        Mockito.when(mapper.map(any(), any())).thenReturn(userDTO);

        // chama método para procurar id como parametro o id static que temos
        ResponseEntity<UserDTO> response = resource.findById(ID);

        // asseguramos que não teremos respostas nulas nem o corpo da resposta
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        // asseguramos que a resposta do id terá a classe de ResponseEntity e que o corpo seja da calsse UserDTO
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(UserDTO.class, response.getBody().getClass());

        // Asseguramos que minhas instancias são as mesmas do meu ID estatico chamado
        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void WhenFindAllReturnAListOfUserDTO() {
        Mockito.when(service.findAll()).thenReturn(List.of(user));
        Mockito.when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
        Assertions.assertEquals(UserDTO.class, response.getBody().get(0).getClass());

        //assegurando que os primeiros campos se relacionam com o chamado
        Assertions.assertEquals(ID, response.getBody().get(0).getId());
        Assertions.assertEquals(NAME, response.getBody().get(0).getName());
        Assertions.assertEquals(EMAIL, response.getBody().get(0).getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().get(0).getPassword());
    }

    @Test
    void WhenCreateThenReturnCreated() {
        Mockito.when(service.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.create(userDTO);

        //Assegurando que será da classe responseentity, que vai ser um 201 crated e que tenha a uri para o objeto
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void WhenUpdateThenReturnSuccess() {
        Mockito.when(service.update(userDTO)).thenReturn(user);
        Mockito.when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(UserDTO.class, response.getBody().getClass());

        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void WhenDeleteThenReturnSuccess() {
        Mockito.doNothing().when(service).delete(anyInt());

        ResponseEntity<UserDTO> response = resource.delete(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class,response.getClass());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // verifica quantas vezes vou passar o método delete no mock
        Mockito.verify(service, Mockito.times(1)).delete(anyInt());
    }

    private void startUser(){
        user = new Usuario(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }

}