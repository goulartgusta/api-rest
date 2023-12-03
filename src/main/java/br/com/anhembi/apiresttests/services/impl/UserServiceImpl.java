package br.com.anhembi.apiresttests.services.impl;

import br.com.anhembi.apiresttests.domain.Usuario;
import br.com.anhembi.apiresttests.services.UserService;
import br.com.anhembi.apiresttests.repositories.UserRepository;
import br.com.anhembi.apiresttests.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Usuario findById(Integer id) {
        Optional<Usuario> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }
}
