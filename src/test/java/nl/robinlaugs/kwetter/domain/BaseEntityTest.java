package nl.robinlaugs.kwetter.domain;

import nl.robinlaugs.kwetter.tester.BaseEntityTester;
import org.junit.Test;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

/**
 * @author Robin Laugs
 */
public class BaseEntityTest {

    @Test
    public void initialize_assignsTimestamp() {
        BaseEntity entity = new BaseEntityTester();

        assertThat(entity.getTimestamp(), is(lessThanOrEqualTo(now())));
    }

    @Test
    public void setId_validId_setsId() {
        BaseEntity entity = new BaseEntityTester();
        entity.setId(1L);

        assertThat(entity.getId(), is(1L));
    }

    @Test
    public void compareTo_validBaseEntity_comparesTimestamps() {
        BaseEntity entity1 = new BaseEntityTester();
        entity1.setTimestamp(of(2018, JANUARY, 1, 0, 0));

        BaseEntity entity2 = new BaseEntityTester();
        entity2.setTimestamp(of(2019, JANUARY, 1, 0, 0));

        assertThat(entity1.compareTo(entity1), is(0));
        assertThat(entity1.compareTo(entity2), is(1));
        assertThat(entity2.compareTo(entity1), is(-1));
    }

}