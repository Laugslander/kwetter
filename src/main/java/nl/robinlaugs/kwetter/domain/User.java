package nl.robinlaugs.kwetter.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

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
public class User extends BaseEntity {

    public static final int MAX_BIO_CHARACTERS = 160;

    private String name;

    private String avatar;

    private String location;

    private String website;

    @Column(length = 160)
    private String bio;

    @OneToOne
    private Account account;

    @ManyToMany(mappedBy = "followers")
    @Singular
    private Collection<User> followings;

    @ManyToMany
    @Singular
    private Collection<User> followers;

    @OneToMany(mappedBy = "author")
    @Singular
    private Collection<Message> messages;

    @ManyToMany(mappedBy = "likes")
    @Singular(value = "like")
    private Collection<Message> liked;

    @ManyToMany(mappedBy = "mentions")
    @Singular(value = "mention")
    private Collection<Message> mentioned;

}
