package nl.robinlaugs.kwetter.persistence;

import nl.robinlaugs.kwetter.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Robin Laugs
 */
public interface GenericDao<T extends BaseEntity> {

    void create(T entity);

    T update(T entity);

    void delete(T entity);

    void delete(Long id);

    T read(Long id);

    Collection<T> readAll();

    Collection<T> readFromTimestamp(LocalDateTime timestamp);

}
