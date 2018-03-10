package nl.robinlaugs.kwetter.persistence.jpa;

import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.persistence.AccountDao;
import nl.robinlaugs.kwetter.service.util.TestDataService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.jboss.shrinkwrap.api.Filters.exclude;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.jboss.shrinkwrap.resolver.api.maven.Maven.resolver;

/**
 * @author Robin Laugs
 */
@RunWith(Arquillian.class)
@UsingDataSet("empty-dataset.yml")
public class BaseJpaDaoIntegrationTest {

    @Inject
    @JpaDao
    private AccountDao dao; // Testing base behaviour of the BaseJpaDao class with a concrete implementation

    @Deployment
    public static WebArchive deploy() {
        File[] files = resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, exclude(TestDataService.class), "nl.robinlaugs.kwetter")
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(INSTANCE, "WEB-INF/beans.xml")
                .addAsLibraries(files);
    }

    @Test
    public void create_validAccount_createsAccount() {
        Account account = new Account();

        dao.create(account);

        assertThat(dao.read(account.getId()), is(account));
    }

    @Test
    public void update_validAccount_updatesAccount() {
        Account account = dao.update(new Account());

        assertThat(dao.read(account.getId()), is(account));
    }

    @Test
    public void delete_validAccount_deletesAccount() {
        Account account = new Account();

        dao.create(account);

        assertThat(dao.read(account.getId()), is(account));

        dao.delete(account);

        assertThat(dao.read(account.getId()), is(nullValue()));
    }

    @Test
    public void delete_validAccountId_deletesAccount() {
        Account account = new Account();

        dao.create(account);

        assertThat(dao.read(account.getId()), is(account));

        dao.delete(account.getId());

        assertThat(dao.read(account.getId()), is(nullValue()));
    }

    @Test
    public void read_validAccountId_readAccounts() {
        Account account = new Account();

        dao.create(account);

        assertThat(dao.read(account.getId()), is(account));
    }

    @Test
    public void read_invalidAccountId_readsNull() {
        Account account = new Account();

        dao.create(account);

        assertThat(dao.read(-1L), is(nullValue()));
    }

    @Test
    public void readAll_readsAllAccounts() {
        Account account1 = new Account();
        Account account2 = new Account();

        dao.create(account1);
        dao.create(account2);

        assertThat(dao.readAll(), containsInAnyOrder(account1, account2));
    }

    @Test
    public void readAllFromTimestamp_validTimestamp_readAllAccountsFromTimestamp() {
        Account account1 = new Account();
        account1.setTimestamp(of(2018, JANUARY, 1, 0, 0));

        Account account2 = new Account();
        account2.setTimestamp(of(2019, JANUARY, 1, 0, 0));

        dao.create(account1);
        dao.create(account2);

        assertThat(dao.readFromTimestamp(of(2019, JANUARY, 1, 0, 0)), contains(account2));
    }

}