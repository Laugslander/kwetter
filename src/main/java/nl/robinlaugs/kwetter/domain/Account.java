package nl.robinlaugs.kwetter.domain;

import lombok.*;
import lombok.Builder.Default;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static nl.robinlaugs.kwetter.domain.Role.USER;

/**
 * @author Robin Laugs
 */
@Entity
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
