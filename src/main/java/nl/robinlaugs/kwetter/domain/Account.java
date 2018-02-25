package nl.robinlaugs.kwetter.domain;

import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static nl.robinlaugs.kwetter.domain.Role.USER;

/**
 * @author Robin Laugs
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "Account.getByUsername",
                query = "SELECT a FROM Account AS a WHERE a.username = :username"),
        @NamedQuery(
                name = "Account.getByCredentials",
                query = "SELECT a FROM Account AS a WHERE a.username = :username AND a.password = :password")
})
@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Account extends BaseEntity {

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated
    @Default
    private Role role = USER;

    @OneToOne(mappedBy = "account")
    @Default
    private User user = User.builder().build();

}
