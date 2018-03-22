package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.domain.BaseEntity;
import nl.robinlaugs.kwetter.persistence.GenericDao;

import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

/**
 * @author Robin Laugs
 */
public abstract class BaseMainService<T extends BaseEntity> {

    private GenericDao<T> dao;

    public abstract void create(T entity) throws Exception;

    public abstract T update(Long id, T update) throws Exception;

    public void delete(T entity) {
        dao.delete(entity);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public abstract T read(Long id) throws Exception;

    public Collection<T> readAll() {
        return dao.readAll().stream().sorted().collect(toList());
    }

    public Collection<T> readFromTimestamp(LocalDateTime timestamp) {
        return dao.readFromTimestamp(timestamp).stream().sorted().collect(toList());
    }

    public GenericDao<T> getDao() {
        return dao;
    }

    public void setDao(GenericDao<T> dao) {
        this.dao = dao;
    }

}
