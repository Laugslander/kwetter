package nl.robinlaugs.kwetter.persistence;

import nl.robinlaugs.kwetter.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Robin Laugs
 */
public interface GenericDao<T extends BaseEntity> {

    void create(T instance);

    void update(T instance);

    void delete(T instance);

    void delete(Long id);

    T read(Long id);

    Collection<T> readAll();

    Collection<T> readFromTimestamp(LocalDateTime timestamp);

}
