package nl.robinlaugs.kwetter.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * @author Robin Laugs
 */
@Singleton
@Startup
public class StartupService {

    @PostConstruct
    private void setUp() {
        // TODO set up mock data
    }

    @PreDestroy
    private void cleanUp() {
        // TODO clean up mock data
    }

}
