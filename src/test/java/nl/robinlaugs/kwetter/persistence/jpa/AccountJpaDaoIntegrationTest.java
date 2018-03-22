package nl.robinlaugs.kwetter.persistence.jpa;

/**
 * @author Robin Laugs
 */
//@RunWith(Arquillian.class)
//@UsingDataSet("empty-dataset.yml")
public class AccountJpaDaoIntegrationTest {

//    @Inject
//    @JpaDao
//    private AccountDao dao;
//
//    @Deployment
//    public static WebArchive deploy() {
//        File[] files = resolver().loadPomFromFile("pom.xml")
//                .importRuntimeDependencies()
//                .resolve().withTransitivity().asFile();
//
//        return ShrinkWrap.create(WebArchive.class)
//                .addPackages(true, exclude(TestDataService.class), "nl.robinlaugs.kwetter")
//                .addAsResource("persistence.xml", "META-INF/persistence.xml")
//                .addAsWebInfResource(INSTANCE, "WEB-INF/beans.xml")
//                .addAsLibraries(files);
//    }
//
//    @Test
//    public void readByUsername_validUsername_readsAccount() {
//        Account account = new Account("username", "password");
//
//        dao.create(account);
//
//        assertThat(dao.readByUsername("username"), is(account));
//    }
//
//    @Test
//    public void readByCredentials_validCredentials_readsAccount() {
//        Account account = new Account("username", "password");
//
//        dao.create(account);
//
//        assertThat(dao.readByCredentials("username", "password"), is(account));
//    }
//
//    @Test
//    public void readByCredentials_invalidUsername_readsNull() {
//        Account account = new Account("username", "password");
//
//        dao.create(account);
//
//        assertThat(dao.readByCredentials("invalid", "password"), is(nullValue()));
//    }
//
//
//    @Test
//    public void readByCredentials_invalidPassword_readsNull() {
//        Account account = new Account("username", "password");
//
//        dao.create(account);
//
//        assertThat(dao.readByCredentials("username", "invalid"), is(nullValue()));
//    }

}