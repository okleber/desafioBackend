package picpay.com.br.desafioBackend.business;

import lombok.Getter;
import org.springframework.stereotype.Service;
import picpay.com.br.desafioBackend.definitions.EnumUserType;
import picpay.com.br.desafioBackend.definitions.InterfaceUser;

@Service
@Getter
public class UsuarioComum implements InterfaceUser {
    String cpf;
    String userType = EnumUserType.COMUM.toString();

    public float sendMoney(float value, InterfaceUser user) {
        return 0;
    }
    public float receiveMoney(float value, InterfaceUser user){
        return 0;
    }

    public boolean setCpf(String cpf){
        String cpfFormatted = formatCpf(cpf);
        if(validateCpf(cpfFormatted)){
            this.cpf=cpfFormatted;
            return true;
        }
        return false;
    }

    private String formatCpf(String cpf) {
        return cpf.replaceAll("[^\\d]", "");
    }

    private boolean validateCpf(String cpf){

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Calcula o primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * (10 - i);
            }
            int firstDigitVerifier = 11 - (sum % 11);
            if (firstDigitVerifier == 10 || firstDigitVerifier == 11) {
                firstDigitVerifier = 0;
            }

            // Calcula o segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * (11 - i);
            }
            int secondDigitVerifier = 11 - (sum % 11);
            if (secondDigitVerifier == 10 || secondDigitVerifier == 11) {
                secondDigitVerifier = 0;
            }

            // Verifica se os dígitos verificadores são válidos
            return cpf.charAt(9) - '0' == firstDigitVerifier && cpf.charAt(10) - '0' == secondDigitVerifier;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
