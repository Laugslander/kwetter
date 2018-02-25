package nl.robinlaugs.kwetter.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import java.util.Collection;

import static java.lang.Integer.compare;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

/**
 * @author Robin Laugs
 */
@Entity
@NamedQuery(
        name = "Topic.getByName",
        query = "SELECT t FROM Topic AS t WHERE t.name = :name")
@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Topic extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "topics")
    @Singular
    private Collection<Message> messages;

    @Override
    public int compareTo(BaseEntity o) {
        int compare = compare(messages.size(), ((Topic) o).messages.size());
        return compare == 0 ? super.compareTo(o) : compare;
    }

}
