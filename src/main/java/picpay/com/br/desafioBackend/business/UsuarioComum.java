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

    public static String formatCpf(String cpfArg) {
        // Remove caracteres não numéricos
        String cpf = cpfArg.replaceAll("[^\\d]", "");
        return cpf;
    }

    public static boolean validateCpf(String cpfArg){
        // Remove caracteres não numéricos
        String cpf=UsuarioComum.formatCpf(cpfArg);

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
