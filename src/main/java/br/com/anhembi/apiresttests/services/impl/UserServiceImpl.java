package br.com.anhembi.apiresttests.services.impl;

import br.com.anhembi.apiresttests.domain.User;
import br.com.anhembi.apiresttests.services.UserService;
import br.com.anhembi.apiresttests.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElse(null);
    }
}
