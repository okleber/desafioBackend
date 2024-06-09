package picpay.com.br.desafioBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picpay.com.br.desafioBackend.entity.EntityUser;

import java.util.List;
import java.util.Optional;

public interface RepositoryUser extends JpaRepository<EntityUser, Integer> {
    List<EntityUser> findByCpf(String cpf);

    List<EntityUser> findByCnpj(String cnpj);
}
