package br.com.anhembi.apiresttests.services;

import br.com.anhembi.apiresttests.domain.User;
public interface UserService {

    User findById(Integer id);
}
