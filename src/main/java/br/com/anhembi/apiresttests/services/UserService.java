package br.com.anhembi.apiresttests.services;

import br.com.anhembi.apiresttests.domain.Usuario;
import br.com.anhembi.apiresttests.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    Usuario findById(Integer id);
    List<Usuario> findAll();
    Usuario create(UserDTO obj);
    Usuario update(UserDTO obj);
    void delete(Integer id);
}
