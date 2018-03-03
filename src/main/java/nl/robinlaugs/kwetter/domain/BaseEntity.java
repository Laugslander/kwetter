package nl.robinlaugs.kwetter.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * @author Robin Laugs
 */
@MappedSuperclass
@Data
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
