package picpay.com.br.desafioBackend.definitions;

public interface InterfaceUser {
    abstract float sendMoney(float value, InterfaceUser user);
    abstract float receiveMoney(float value, InterfaceUser user);
}
