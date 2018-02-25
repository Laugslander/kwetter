package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.BaseEntity;
import nl.robinlaugs.kwetter.persistence.GenericDao;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Robin Laugs
 */
public abstract class BaseService<T extends BaseEntity> {

    private GenericDao<T> dao;

    public void create(T entity) throws Exception {
        dao.create(entity);
    }

    public void update(T entity) throws Exception {
        dao.update(entity);
    }

    public void delete(T entity) {
        dao.delete(entity);
    }

    public void delete(Long id) {
        dao.delete(id);
    }

    public T read(Long id) {
        return dao.read(id);
    }

    public Collection<T> readAll() {
        return dao.readAll();
    }

    public Collection<T> readFromTimestamp(LocalDateTime timestamp) {
        return dao.readFromTimestamp(timestamp);
    }

    public GenericDao<T> getDao() {
        return dao;
    }

    public void setDao(GenericDao<T> dao) {
        this.dao = dao;
    }

}
