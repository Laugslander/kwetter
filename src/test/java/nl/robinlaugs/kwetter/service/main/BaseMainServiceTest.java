package nl.robinlaugs.kwetter.service.main;

import nl.robinlaugs.kwetter.persistence.GenericDao;
import nl.robinlaugs.kwetter.tester.BaseEntityTester;
import nl.robinlaugs.kwetter.tester.BaseMainServiceTester;
import nl.robinlaugs.kwetter.tester.BaseMemoryDaoTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Robin Laugs
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseMainServiceTest {

    private BaseMainService<BaseEntityTester> service;

    @Mock
    private GenericDao<BaseEntityTester> dao;

    @Before
    public void setUp() {
        service = new BaseMainServiceTester();
        service.setDao(dao);
    }

    @Test
    public void create_validEntity_callsDao() throws Exception {
        BaseEntityTester entity = new BaseEntityTester();

        service.create(entity);

        verify(dao, times(1)).create(entity);
    }

    @Test
    public void update_validEntity_callsDao() throws Exception {
        BaseEntityTester entity = new BaseEntityTester();

        service.update(entity);

        verify(dao, times(1)).update(entity);
    }

    @Test
    public void delete_validEntity_callsDao() {
        BaseEntityTester entity = new BaseEntityTester();

        service.delete(entity);

        verify(dao, times(1)).delete(entity);
    }

    @Test
    public void delete_validId_callsDao() {
        Long id = 1L;

        service.delete(id);

        verify(dao, times(1)).delete(id);
    }

    @Test
    public void read_validId_callsDao() {
        Long id = 1L;

        service.read(id);

        verify(dao, times(1)).read(id);
    }

    @Test
    public void readAll_callsDao() {
        service.readAll();

        verify(dao, times(1)).readAll();
    }

    @Test
    public void readFromTimestamp_validTimestamp_callsDao() {
        LocalDateTime timestamp = now();

        service.readFromTimestamp(timestamp);

        verify(dao, times(1)).readFromTimestamp(timestamp);
    }

    @Test
    public void setDao_validDao_assignsDao() {
        GenericDao<BaseEntityTester> dao = new BaseMemoryDaoTester();

        service.setDao(dao);

        GenericDao<BaseEntityTester> actual = service.getDao();
        GenericDao<BaseEntityTester> expected = dao;

        assertThat(actual, is(equalTo(expected)));
    }

}