package picpay.com.br.desafioBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import picpay.com.br.desafioBackend.entity.EntityUser;
import picpay.com.br.desafioBackend.entity.EntityWallet;

public interface RepositoryWallet extends JpaRepository<EntityWallet, Integer> {
}
