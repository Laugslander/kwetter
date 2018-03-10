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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jboss.shrinkwrap.api.Filters.exclude;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.jboss.shrinkwrap.resolver.api.maven.Maven.resolver;

/**
 * @author Robin Laugs
 */
@RunWith(Arquillian.class)
@UsingDataSet("empty-dataset.yml")
public class AccountJpaDaoIntegrationTest {

    @Inject
    @JpaDao
    private AccountDao dao;

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
    public void readByUsername_validUsername_readsAccount() {
        Account account = new Account("username", "password");

        dao.create(account);

        assertThat(dao.readByUsername("username"), is(account));
    }

    @Test
    public void readByCredentials_validCredentials_readsAccount() {
        Account account = new Account("username", "password");

        dao.create(account);

        assertThat(dao.readByCredentials("username", "password"), is(account));
    }

    @Test
    public void readByCredentials_invalidUsername_readsNull() {
        Account account = new Account("username", "password");

        dao.create(account);

        assertThat(dao.readByCredentials("invalid", "password"), is(nullValue()));
    }


    @Test
    public void readByCredentials_invalidPassword_readsNull() {
        Account account = new Account("username", "password");

        dao.create(account);

        assertThat(dao.readByCredentials("username", "invalid"), is(nullValue()));
    }

}