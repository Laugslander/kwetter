package nl.robinlaugs.kwetter.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.TreeSet;

import static javax.persistence.CascadeType.*;

/**
 * @author Robin Laugs
 */
@Entity(name = "t_user")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {"name", "avatar", "location", "website", "bio"})
@ToString(callSuper = true, of = {"name", "avatar", "location", "website", "bio"})
public class User extends BaseEntity {

    public static final int MAX_BIO_CHARACTERS = 160;

    private String name;

    private String avatar;

    private String location;

    private String website;

    @Column(length = MAX_BIO_CHARACTERS)
    private String bio;

    @OneToOne
    private Account account;

    @ManyToMany(mappedBy = "followers", cascade = {PERSIST, MERGE})
    private Collection<User> followings = new TreeSet<>();

    @ManyToMany
    private Collection<User> followers = new TreeSet<>();

    @OneToMany(mappedBy = "author", cascade = ALL)
    private Collection<Message> messages = new TreeSet<>();

    @ManyToMany(mappedBy = "likes", cascade = {PERSIST, MERGE})
    private Collection<Message> liked = new TreeSet<>();

    @ManyToMany(mappedBy = "mentions", cascade = {PERSIST, MERGE})
    private Collection<Message> mentioned = new TreeSet<>();

}
