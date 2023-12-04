package br.com.anhembi.apiresttests.services;

import br.com.anhembi.apiresttests.domain.Usuario;

import java.util.List;

public interface UserService {

    Usuario findById(Integer id);
    List<Usuario> findAll();
}
