package nl.robinlaugs.kwetter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.NONE;

/**
 * @author Robin Laugs
 */
@MappedSuperclass
@Data
@NoArgsConstructor(access = NONE)
public abstract class BaseEntity implements Serializable, Comparable<BaseEntity> {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime timestamp = now();

    @Override
    public int compareTo(BaseEntity o) {
        return o.timestamp.compareTo(timestamp);
    }

}
