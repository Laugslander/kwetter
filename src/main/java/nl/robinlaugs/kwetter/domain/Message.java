package nl.robinlaugs.kwetter.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.TreeSet;

/**
 * @author Robin Laugs
 */
@Entity(name = "t_message")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true, of = "text")
@ToString(callSuper = true, of = "text")
public class Message extends BaseEntity {

    public static final int MAX_TEXT_CHARACTERS = 140;

    @Column(length = MAX_TEXT_CHARACTERS)
    @NonNull
    private String text;

    @ManyToOne
    private User author;

    @ManyToMany
    @JoinTable(name = "t_message_t_user_likes",
            joinColumns = @JoinColumn(name = "message_ID"), inverseJoinColumns = @JoinColumn(name = "like_ID"))
    private Collection<User> likes = new TreeSet<>();

    @ManyToMany
    @JoinTable(name = "t_message_t_user_mention",
            joinColumns = @JoinColumn(name = "message_ID"), inverseJoinColumns = @JoinColumn(name = "mention_ID"))
    private Collection<User> mentions = new TreeSet<>();

    @ManyToMany
    private Collection<Topic> topics = new TreeSet<>();

}
