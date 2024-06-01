package picpay.com.br.desafioBackend.controller;

import org.springframework.web.bind.annotation.*;

import picpay.com.br.desafioBackend.entity.EntityUser;
import picpay.com.br.desafioBackend.entity.EntityWallet;
import picpay.com.br.desafioBackend.repository.RepositoryUser;
import picpay.com.br.desafioBackend.repository.RepositoryWallet;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class ControllerUser {
    private final RepositoryUser repositoryUser;
    private final RepositoryWallet repositoryWallet;

    public ControllerUser(RepositoryUser repositoryUser, RepositoryWallet repositoryWallet) {
        this.repositoryUser = repositoryUser;
        this.repositoryWallet = repositoryWallet;
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
}
