package picpay.com.br.desafioBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picpay.com.br.desafioBackend.entity.EntityUser;

public interface RepositoryUser extends JpaRepository<EntityUser, Integer> {
}
