package nl.robinlaugs.kwetter.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static nl.robinlaugs.kwetter.domain.Role.USER;

/**
 * @author Robin Laugs
 */
@Entity(name = "t_account")
@NamedQuery(
        name = "Account.getByUsername",
        query = "SELECT a FROM t_account AS a WHERE a.username = :username")
@NamedQuery(
        name = "Account.getByCredentials",
        query = "SELECT a FROM t_account AS a WHERE a.username = :username AND a.password = :password")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {"username", "password", "role"})
@ToString(callSuper = true, of = {"username", "password", "role"})
public class Account extends BaseEntity {

    @Column(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;

    @Enumerated(STRING)
    @NonNull
    private Role role = USER;

    @OneToOne(mappedBy = "account", cascade = ALL)
    @NonNull
    private User user = new User();

}
