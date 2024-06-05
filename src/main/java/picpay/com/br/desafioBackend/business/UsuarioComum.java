package picpay.com.br.desafioBackend.business;

import org.springframework.stereotype.Component;
import picpay.com.br.desafioBackend.definitions.InterfaceUser;

@Component
public class UsuarioComum implements InterfaceUser {
    public float sendMoney(float value, InterfaceUser user) {
        return 0;
    }
    public float receiveMoney(float value, InterfaceUser user){
        return 0;
    }
    public boolean validaCpf(String cpf){
        return cpf.length() == 11;
    }
}
