package nl.robinlaugs.kwetter.service.util;

import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * @author Robin Laugs
 */
@Singleton
@Startup
@Log
public class TestDataService {

    @PostConstruct
    private void setUp() {
        // TODO set up test data
    }

    @PreDestroy
    private void cleanUp() {
        // TODO clean up test data
    }

}
