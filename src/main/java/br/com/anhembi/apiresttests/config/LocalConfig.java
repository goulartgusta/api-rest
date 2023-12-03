package br.com.anhembi.apiresttests.config;

import br.com.anhembi.apiresttests.domain.Usuario;
import br.com.anhembi.apiresttests.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public List<Usuario> startBD() {
        Usuario u1 = new Usuario(null, "Gus", "gus@gmail.com", "123");
        Usuario u2 = new Usuario(null, "Higu", "higu@gmail.com", "123");

        return repository.saveAll(List.of(u1, u2));
    }

}