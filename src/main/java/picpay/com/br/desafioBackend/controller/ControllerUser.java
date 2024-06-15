package picpay.com.br.desafioBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import picpay.com.br.desafioBackend.business.UsuarioComum;
import picpay.com.br.desafioBackend.business.UsuarioLojista;
import picpay.com.br.desafioBackend.entity.EntityUser;
import picpay.com.br.desafioBackend.entity.EntityWallet;
import picpay.com.br.desafioBackend.repository.RepositoryUser;
import picpay.com.br.desafioBackend.repository.RepositoryWallet;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class ControllerUser {
    private final RepositoryUser repositoryUser;
    private final RepositoryWallet repositoryWallet;
    private final UsuarioComum usuarioComum;
    private final UsuarioLojista usuarioLojista;

    public ControllerUser(RepositoryUser repositoryUser, RepositoryWallet repositoryWallet, UsuarioComum usuarioComum, UsuarioLojista usuarioLojista) {
        this.repositoryUser = repositoryUser;
        this.repositoryWallet = repositoryWallet;
        this.usuarioComum = usuarioComum;
        this.usuarioLojista = usuarioLojista;

    }

    @GetMapping
    Optional<List<EntityUser>> getUsers(@RequestParam(value="cpf", required=false) String cpf,
                                        @RequestParam(value = "cnpj",required=false) String cnpj ){
        List<EntityUser> users;
        if(cpf!=null){
            if (! usuarioComum.setCpf(cpf)) return Optional.empty();
            users=repositoryUser.findByCpf(usuarioComum.getCpf());
        }else if(cnpj!=null) {
            if (! usuarioLojista.setCnpj(cnpj)) return Optional.empty();
            users=repositoryUser.findByCnpj(usuarioLojista.getCnpj());
        }else{
            return Optional.of(repositoryUser.findAll());
        }
        return users.isEmpty()?Optional.empty():Optional.of(users);
    }

    @PostMapping
    HttpStatus postUser(@RequestBody List<EntityUser> users){
        for(EntityUser user : users) {
            if (user.getCpf() != null) {
                if (!usuarioComum.setCpf(user.getCpf())) return HttpStatus.NOT_ACCEPTABLE;
                user.setCpf(usuarioComum.getCpf());
                user.setUserType(usuarioComum.getUserType());
            } else if (user.getCnpj() != null) {
                if (!usuarioLojista.setCnpj(user.getCnpj())) return HttpStatus.NOT_ACCEPTABLE;
                user.setCnpj(usuarioLojista.getCnpj());
                user.setUserType(usuarioLojista.getUserType());
            } else {
                return HttpStatus.BAD_REQUEST;
            }
            EntityUser userObject = repositoryUser.save(user);
            EntityWallet wallet = new EntityWallet();
            wallet.setBalance(0f);
            wallet.setUserId(userObject.getId());
            repositoryWallet.save(wallet);
        }
        return HttpStatus.CREATED;
    }
}
