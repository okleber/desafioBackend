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
    private Integer id;
    private Float balance;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private EntityUser entityUser;
}
