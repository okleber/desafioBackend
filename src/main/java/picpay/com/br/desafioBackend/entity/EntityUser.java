package picpay.com.br.desafioBackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class EntityUser {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Column(unique = true)
    private String cpf;
    @Column(unique = true)
    private String mail;
    private String password;
    private String userType;

}
