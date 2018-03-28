package nl.robinlaugs.kwetter.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import java.util.Collection;
import java.util.TreeSet;

import static java.lang.Integer.compare;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

/**
 * @author Robin Laugs
 */
@Entity(name = "t_topic")
@NamedQuery(
        name = "Topic.getByName",
        query = "SELECT t FROM t_topic AS t WHERE t.name = :name")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true, of = "name")
@ToString(callSuper = true, of = "name")
public class Topic extends BaseEntity {

    @NonNull
    private String name;

    @ManyToMany(mappedBy = "topics", cascade = {PERSIST, MERGE})
    private Collection<Message> messages = new TreeSet<>();

    @Override
    public int compareTo(BaseEntity o) {
        int compare = compare(((Topic) o).messages.size(), messages.size());
        return compare == 0 ? super.compareTo(o) : compare;
    }

}
