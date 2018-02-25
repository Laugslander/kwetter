package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toSet;

/**
 * @author Robin Laugs
 */
public abstract class BaseMemoryDao<T extends BaseEntity> {

    private Map<Long, T> entities = new ConcurrentHashMap<>();

    private Long id = 1L;

    public void create(T entity) {
        entity.setId(id);
        entities.put(id++, entity);
    }

    public void update(T entity) {
        entities.put(entity.getId(), entity);
    }

    public void delete(T entity) {
        entities.remove(entity.getId());
    }

    public void delete(Long id) {
        entities.remove(id);
    }

    public T read(Long id) {
        return entities.get(id);
    }

    public Collection<T> readAll() {
        return entities.values();
    }

    public Collection<T> readFromTimestamp(LocalDateTime timestamp) {
        return entities.values().stream()
                .filter(e -> e.getTimestamp().compareTo(timestamp) >= 0)
                .collect(toSet());
    }

    Map<Long, T> getEntities() {
        return entities;
    }

}
