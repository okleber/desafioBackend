package picpay.com.br.desafioBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picpay.com.br.desafioBackend.entity.EntityUser;

import java.util.Optional;

public interface RepositoryUser extends JpaRepository<EntityUser, Integer> {
    Optional<EntityUser> findByCpf(String cpf);
}
