package nl.robinlaugs.kwetter.domain;

import nl.robinlaugs.kwetter.tester.BaseEntityTester;
import org.junit.Test;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Robin Laugs
 */
public class BaseEntityTest {

    @Test
    public void initialize_assignsTimestamp() {
        BaseEntity entity = new BaseEntityTester();

        LocalDateTime expected = now();
        LocalDateTime actual = entity.getTimestamp();

        assertThat((actual.isAfter(expected)) || actual.isEqual(expected), is(true));
    }

    @Test
    public void setId_validId_setsId() {
        BaseEntity entity = new BaseEntityTester();
        entity.setId(1L);

        Long actual = entity.getId();
        Long expected = 1L;

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void compareTo_validBaseEntity_comparesTimestamps() {
        BaseEntity entity1 = new BaseEntityTester();
        LocalDateTime timestamp1 = of(2018, JANUARY, 1, 0, 0);
        entity1.setTimestamp(timestamp1);

        BaseEntity entity2 = new BaseEntityTester();
        LocalDateTime timestamp2 = of(2019, JANUARY, 1, 0, 0);
        entity2.setTimestamp(timestamp2);

        assertThat(entity1.compareTo(entity1), is(0));
        assertThat(entity1.compareTo(entity2), is(1));
        assertThat(entity2.compareTo(entity1), is(-1));
    }

}