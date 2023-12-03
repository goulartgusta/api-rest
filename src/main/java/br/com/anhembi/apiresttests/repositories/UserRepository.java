package br.com.anhembi.apiresttests.repositories;

import br.com.anhembi.apiresttests.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {

}
