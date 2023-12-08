package br.com.anhembi.apiresttests.exceptions;
import br.com.anhembi.apiresttests.services.exceptions.DataIntegratyViolationException;
import br.com.anhembi.apiresttests.services.exceptions.ObjectNotFoundException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest
@AutoConfigureMockMvc
public class ResourceExceptionHandlerIntegrationTest {

    @Autowired
    private ResourceExceptionHandler exceptionHandler;

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {

        // Executar o método que está sendo testado
        ResponseEntity<StandardError> response = exceptionHandler.ObjectNotFound(
                new ObjectNotFoundException("Objeto não encontrado!"),
                new MockHttpServletRequest());

        // Verificar se a resposta é a esperada
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Objeto não encontrado!", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void dataIntegrityViolationException() {

        // Executar o método que está sendo testado
        ResponseEntity<StandardError> response = exceptionHandler.dataIntegrityViolationException(
                new DataIntegratyViolationException("E-mail já cadastrado!"),
                new MockHttpServletRequest());

        // Verificar se a resposta é a esperada
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("E-mail já cadastrado!", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }


}
