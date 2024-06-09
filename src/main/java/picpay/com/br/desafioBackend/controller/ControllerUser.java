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

    public ControllerUser(RepositoryUser repositoryUser, RepositoryWallet repositoryWallet) {
        this.repositoryUser = repositoryUser;
        this.repositoryWallet = repositoryWallet;

    }

    @GetMapping
    Optional<List<EntityUser>> getUsers(@RequestParam(value="cpf", required=false) String cpf,
                                        @RequestParam(value = "cnpj",required=false) String cnpj ){
        if(cpf!=null){
            if (! UsuarioComum.validateCpf(cpf)) return Optional.empty();
            List<EntityUser> users=repositoryUser.findByCpf(UsuarioComum.formatCpf(cpf));
            return users.isEmpty()?Optional.empty():Optional.of(users);
        }else if(cnpj!=null) {
            if (! UsuarioLojista.validateCnpj(cnpj)) return Optional.empty();
            List<EntityUser> users=repositoryUser.findByCnpj(UsuarioLojista.formatCnpj(cnpj));
            return users.isEmpty()?Optional.empty():Optional.of(users);
        }else{
            return Optional.of(repositoryUser.findAll());
        }

    }

    @PostMapping
    HttpStatus postUser(@RequestBody EntityUser user){
        if(UsuarioComum.validateCpf(user.getCpf())){
            user.setCpf(UsuarioComum.formatCpf(user.getCpf()));
        }else if(UsuarioLojista.validateCnpj(user.getCnpj())) {
            user.setCnpj(UsuarioLojista.formatCnpj(user.getCnpj()));
        }else{
            return HttpStatus.BAD_REQUEST;
        }
        EntityUser userObject = repositoryUser.save(user);
        EntityWallet wallet = new EntityWallet();
        wallet.setBalance(0f);
        wallet.setUserId(userObject.getId());
        repositoryWallet.save(wallet);
        return HttpStatus.CREATED;
    }
}
