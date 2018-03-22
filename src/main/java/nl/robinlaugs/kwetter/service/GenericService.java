package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Robin Laugs
 */
public interface GenericService<T extends BaseEntity> extends Serializable {

    /**
     * Persists a {@link BaseEntity}.
     *
     * @param entity The {@link BaseEntity} that should be persisted.
     * @throws Exception When the {@code entity} violates input / uniqueness constraints.
     */
    void create(T entity) throws Exception;

    /**
     * Updates an existing {@link BaseEntity}.
     *
     * @param id     The {@code id} of the existing {@link BaseEntity} that should be updated.
     * @param update The {@link BaseEntity} that contains the updates.
     * @return The {@link BaseEntity} that was updated.
     * @throws Exception When the {@code entity} violates input / uniqueness constraints.
     */
    T update(Long id, T update) throws Exception;

    /**
     * Deletes an existing {@link BaseEntity}.
     *
     * @param entity The existing {@link BaseEntity} that should be deleted.
     */
    void delete(T entity);

    /**
     * Deletes an existing {@link BaseEntity}.
     *
     * @param id The {@code id} of the existing {@link BaseEntity} that should be deleted.
     */
    void delete(Long id);

    /**
     * Reads an existing {@link BaseEntity}.
     *
     * @param id The {@code id} of the existing {@link BaseEntity} that should be read.
     * @return The {@link BaseEntity} that was read.
     * @throws Exception When the {@code entity} was not found.
     */
    T read(Long id) throws Exception;

    /**
     * Reads all existing {@link BaseEntity BaseEntities}.
     *
     * @return The {@link BaseEntity BaseEntities} that were read.
     */
    Collection<T> readAll();

    /**
     * Reads all existing {@link BaseEntity BaseEntities}.
     *
     * @param timestamp The {@link LocalDateTime} from when all existing {@link BaseEntity BaseEntities} should be read.
     * @return The {@link BaseEntity BaseEntities} that were read.
     */
    Collection<T> readFromTimestamp(LocalDateTime timestamp);

}
