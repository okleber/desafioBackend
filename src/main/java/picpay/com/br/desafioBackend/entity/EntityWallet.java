package picpay.com.br.desafioBackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EntityWallet {
    @Id
    @GeneratedValue
    private int id;
    private float balance;
    private int userId;
}
