package nl.robinlaugs.kwetter.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class Message extends BaseEntity {

    public static final int MAX_TEXT_CHARACTERS = 140;

    @Column(length = MAX_TEXT_CHARACTERS)
    private String text;

    @ManyToOne
    private User author;

    @ManyToMany
    @Singular
    private Collection<User> likes;

    @ManyToMany
    @Singular
    private Collection<User> mentions;

    @ManyToMany
    @Singular
    private Collection<Topic> topics;

}
