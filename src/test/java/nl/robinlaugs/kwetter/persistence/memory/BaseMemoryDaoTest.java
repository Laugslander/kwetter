package nl.robinlaugs.kwetter.persistence.memory;

import nl.robinlaugs.kwetter.domain.BaseEntity;
import nl.robinlaugs.kwetter.tester.BaseEntityTester;
import nl.robinlaugs.kwetter.tester.BaseMemoryDaoTester;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collection;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * @author Robin Laugs
 */
public class BaseMemoryDaoTest {

    private BaseMemoryDao dao;

    @Before
    public void setUp() {
        dao = new BaseMemoryDaoTester();
    }

    @Test
    public void initialize_initializesEntities() {
        assertThat(dao.getEntities(), is(not(nullValue())));
    }

    @Test
    public void create_validEntity_createsEntity() {
        BaseEntity entity = new BaseEntityTester();

        dao.create(entity);

        assertThat(dao.read(entity.getId()), is(entity));
    }

    @Test
    public void update_validExistingEntity_updatesEntity() {
        BaseEntity entity = new BaseEntityTester();

        dao.create(entity);

        entity = dao.read(entity.getId());
        entity.setId(2L);

        dao.update(entity);

        assertThat(dao.read(entity.getId()).getId(), is(2L));
    }

    @Test
    public void update_validExistingEntity_readsEntity() {
        BaseEntity entity = new BaseEntityTester();

        dao.create(entity);

        assertThat(dao.update(entity), is(entity));
    }

    @Test
    public void delete_validExistingEntity_deletesEntity() {
        BaseEntity entity = new BaseEntityTester();

        dao.create(entity);
        dao.delete(entity.getId());

        assertThat(dao.read(entity.getId()), is(nullValue()));
    }

    @Test
    public void delete_validExistingId_deletesEntity() {
        BaseEntity entity = new BaseEntityTester();

        dao.create(entity);
        Long id = entity.getId();

        dao.delete(id);

        assertThat(dao.read(id), is(nullValue()));
    }

    @Test
    public void read_validExistingId_readsEntity() {
        BaseEntity entity = new BaseEntityTester();

        dao.create(entity);
        Long id = entity.getId();

        assertThat(dao.read(id), is(entity));
    }

    @Test
    public void readAll_readsAllEntities() {
        BaseEntity entity1 = new BaseEntityTester();
        BaseEntity entity2 = new BaseEntityTester();
        BaseEntity entity3 = new BaseEntityTester();

        dao.create(entity1);
        dao.create(entity2);
        dao.create(entity3);

        assertThat(dao.readAll().size(), is(3));
    }

    @Test
    public void readAll_readsCorrectEntities() {
        BaseEntity entity1 = new BaseEntityTester();
        BaseEntity entity2 = new BaseEntityTester();
        BaseEntity entity3 = new BaseEntityTester();

        dao.create(entity1);
        dao.create(entity2);
        dao.create(entity3);

        assertThat((Collection<BaseEntity>) dao.readAll(), containsInAnyOrder(entity1, entity2, entity3));
    }

    @Test
    public void readFromTimestamp_validTimestamp_readsCorrectEntities() {
        BaseEntity entity1 = new BaseEntityTester();
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        entity1.setTimestamp(timestamp1);

        BaseEntity entity2 = new BaseEntityTester();
        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        entity2.setTimestamp(timestamp2);

        BaseEntity entity3 = new BaseEntityTester();
        LocalDateTime timestamp3 = of(2020, JANUARY, 1, 0, 0);
        entity3.setTimestamp(timestamp3);

        dao.create(entity1);
        dao.create(entity2);
        dao.create(entity3);

        LocalDateTime timestamp = of(2019, JANUARY, 1, 0, 0);

        assertThat((Collection<BaseEntity>) dao.readFromTimestamp(timestamp), containsInAnyOrder(entity2, entity3));
    }

}