package nl.robinlaugs.kwetter.persistence.jpa;

import nl.robinlaugs.kwetter.domain.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.lang.String.format;

/**
 * @author Robin Laugs
 */
public abstract class BaseJpaDao<T extends BaseEntity> {

    @PersistenceContext
    private EntityManager manager;

    private Class<T> implementation;

    public void create(T entity) {
        manager.persist(entity);
    }

    public void update(T entity) {
        manager.merge(entity);
    }

    public void delete(T entity) {
        manager.remove(entity);
    }

    public void delete(Long id) {
        T entity = read(id);
        manager.remove(entity);
    }

    public T read(Long id) {
        return manager.find(implementation, id);
    }

    public Collection<T> readAll() {
        String query = format("FROM %s", implementation.getName());
        return manager.createQuery(query, implementation).getResultList();
    }

    public Collection<T> readFromTimestamp(LocalDateTime timestamp) {
        String query = format("FROM %s WHERE timestamp >= %s", implementation.getName(), timestamp);
        return manager.createQuery(query, implementation).getResultList();
    }

    EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

    void setImplementation(Class<T> implementation) {
        this.implementation = implementation;
    }

}
