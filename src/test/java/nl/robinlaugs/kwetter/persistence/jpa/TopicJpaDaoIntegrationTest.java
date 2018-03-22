package nl.robinlaugs.kwetter.persistence.jpa;

/**
 * @author Robin Laugs
 */
//@RunWith(Arquillian.class)
//@UsingDataSet("empty-dataset.yml")
public class TopicJpaDaoIntegrationTest {

//    @Inject
//    @JpaDao
//    private TopicDao dao;
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
//    public void readByName_validName_readsTopic() {
//        Topic topic = new Topic("name");
//
//        dao.create(topic);
//
//        assertThat(dao.readByName("name"), is(topic));
//    }
//
//    @Test
//    public void readByName_invalidName_readsNull() {
//        Topic topic = new Topic("name");
//
//        dao.create(topic);
//
//        assertThat(dao.readByName("invalid"), is(nullValue()));
//    }

}