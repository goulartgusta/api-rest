package br.com.anhembi.apiresttests.services.impl;

import br.com.anhembi.apiresttests.domain.Usuario;
import br.com.anhembi.apiresttests.domain.dto.UserDTO;
import br.com.anhembi.apiresttests.repositories.UserRepository;
import br.com.anhembi.apiresttests.services.UserService;
import br.com.anhembi.apiresttests.services.exceptions.DataIntegratyViolationException;
import br.com.anhembi.apiresttests.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Usuario findById(Integer id) {
        Optional<Usuario> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    public List<Usuario> findAll(){
        return repository.findAll();
    }

    @Override
    public Usuario create(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, Usuario.class));
    }

    public void findByEmail(UserDTO obj) {
        Optional<Usuario> user = repository.findByEmail(obj.getEmail());
        if(user.isPresent()) {
            throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
        }
    }
}
