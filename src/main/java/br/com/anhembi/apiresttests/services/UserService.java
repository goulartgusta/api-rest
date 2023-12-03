package br.com.anhembi.apiresttests.services;

import br.com.anhembi.apiresttests.domain.Usuario;
public interface UserService {

    Usuario findById(Integer id);
}
