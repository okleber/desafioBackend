package picpay.com.br.desafioBackend.controller;

import org.springframework.data.util.Optionals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import picpay.com.br.desafioBackend.business.UsuarioComum;
import picpay.com.br.desafioBackend.entity.EntityUser;
import picpay.com.br.desafioBackend.entity.EntityWallet;
import picpay.com.br.desafioBackend.repository.RepositoryUser;
import picpay.com.br.desafioBackend.repository.RepositoryWallet;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class ControllerUser {
    private final RepositoryUser repositoryUser;
    private final RepositoryWallet repositoryWallet;
    private final UsuarioComum usuarioComum;

    public ControllerUser(RepositoryUser repositoryUser, RepositoryWallet repositoryWallet, UsuarioComum usuarioComum) {
        this.repositoryUser = repositoryUser;
        this.repositoryWallet = repositoryWallet;
        this.usuarioComum=usuarioComum;
    }

    @GetMapping
    Iterable<EntityUser> getUsers(){
        return repositoryUser.findAll();
    }

    @PostMapping
    EntityUser postUser(@RequestBody EntityUser user){
        EntityUser userObject = repositoryUser.save(user);
        EntityWallet wallet = new EntityWallet();
        wallet.setBalance(0f);
        wallet.setUserId(userObject.getId());
        repositoryWallet.save(wallet);
        return user;
    }

    @GetMapping("/byCpf")
    Optional<EntityUser> getUsersByCpf(@RequestParam(value="cpf", required=false) String cpf){
        if (! usuarioComum.validaCpf(cpf)) return Optional.empty();
        return repositoryUser.findByCpf(cpf);
    }
}
