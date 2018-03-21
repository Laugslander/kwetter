package nl.robinlaugs.kwetter.persistence.jpa;

/**
 * @author Robin Laugs
 */
//@RunWith(Arquillian.class)
//@UsingDataSet("empty-dataset.yml")
public class BaseJpaDaoIntegrationTest {

//    @Inject
//    @JpaDao
//    private AccountDao dao; // Testing base behaviour of the BaseJpaDao class with a concrete implementation
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
//    public void create_validAccount_createsAccount() {
//        Account account = new Account();
//
//        dao.create(account);
//
//        assertThat(dao.read(account.getId()), is(account));
//    }
//
//    @Test
//    public void update_validAccount_updatesAccount() {
//        Account account = dao.update(new Account());
//
//        assertThat(dao.read(account.getId()), is(account));
//    }
//
//    @Test
//    public void delete_validAccount_deletesAccount() {
//        Account account = new Account();
//
//        dao.create(account);
//
//        assertThat(dao.read(account.getId()), is(account));
//
//        dao.delete(account);
//
//        assertThat(dao.read(account.getId()), is(nullValue()));
//    }
//
//    @Test
//    public void delete_validAccountId_deletesAccount() {
//        Account account = new Account();
//
//        dao.create(account);
//
//        assertThat(dao.read(account.getId()), is(account));
//
//        dao.delete(account.getId());
//
//        assertThat(dao.read(account.getId()), is(nullValue()));
//    }
//
//    @Test
//    public void read_validAccountId_readAccounts() {
//        Account account = new Account();
//
//        dao.create(account);
//
//        assertThat(dao.read(account.getId()), is(account));
//    }
//
//    @Test
//    public void read_invalidAccountId_readsNull() {
//        Account account = new Account();
//
//        dao.create(account);
//
//        assertThat(dao.read(-1L), is(nullValue()));
//    }
//
//    @Test
//    public void readAll_readsAllAccounts() {
//        Account account1 = new Account();
//        Account account2 = new Account();
//
//        dao.create(account1);
//        dao.create(account2);
//
//        assertThat(dao.readAll(), containsInAnyOrder(account1, account2));
//    }
//
//    @Test
//    public void readAllFromTimestamp_validTimestamp_readAllAccountsFromTimestamp() {
//        Account account1 = new Account();
//        account1.setTimestamp(of(2018, JANUARY, 1, 0, 0));
//
//        Account account2 = new Account();
//        account2.setTimestamp(of(2019, JANUARY, 1, 0, 0));
//
//        dao.create(account1);
//        dao.create(account2);
//
//        assertThat(dao.readFromTimestamp(of(2019, JANUARY, 1, 0, 0)), contains(account2));
//    }

}