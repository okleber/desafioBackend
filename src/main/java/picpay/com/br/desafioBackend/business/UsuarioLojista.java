package picpay.com.br.desafioBackend.business;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import picpay.com.br.desafioBackend.definitions.EnumUserType;
import picpay.com.br.desafioBackend.definitions.InterfaceUser;
@Service
@Getter
public class UsuarioLojista implements InterfaceUser {

    String cnpj;
    String userType = EnumUserType.LOJISTA.toString();

    public float sendMoney(float value, InterfaceUser user) {
        return 0;
    }
    public float receiveMoney(float value, InterfaceUser user){
        return 0;
    }

    public boolean setCnpj(String cnpj){
        String cnpjFormatted = formatCnpj(cnpj);
        if(validateCnpj(cnpjFormatted)){
            this.cnpj=cnpjFormatted;
            return true;
        }
        return false;
    }

    private static String formatCnpj(String cnpj) {

        return cnpj.replaceAll("[^\\d]", "");
    }

    private static boolean validateCnpj(String cnpj) {
        if (cnpj.length() != 14) {
            return false;
        }

        // Check if the CNPJ is not made up of the same digit repeated 14 times
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            // Calculate the first verification digit
            int sum = 0;
            String strNumber = "543298765432";
            for (int i = 0; i < 12; i++) {
                sum += (cnpj.charAt(i) - '0') * (strNumber.charAt(i) - '0');
            }
            int remainder = sum % 11;
            int firstDigitVerifier = (remainder < 2) ? 0 : 11 - remainder;

            // Check if the first verification digit matches the 13th digit of the CNPJ
            if (cnpj.charAt(12) - '0' != firstDigitVerifier) {
                return false;
            }

            // Calculate the second verification digit
            sum = 0;
            strNumber = "6543298765432";
            for (int i = 0; i < 13; i++) {
                sum += (cnpj.charAt(i) - '0') * (strNumber.charAt(i) - '0');
            }
            remainder = sum % 11;
            int secondDigitVerifier = (remainder < 2) ? 0 : 11 - remainder;

            // Check if the second verification digit matches the 14th digit of the CNPJ
            return cnpj.charAt(13) - '0' == secondDigitVerifier;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
