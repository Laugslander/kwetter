package nl.robinlaugs.kwetter.service.auth.main;

import nl.robinlaugs.kwetter.service.auth.KeyService;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.security.Key;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * @author Robin Laugs
 */
@Singleton
@Startup
public class MainKeyService implements KeyService {

    private UUID key;

    @PostConstruct
    private void setUp() {
        key = randomUUID();
    }

    @Override
    public Key generate() {
        byte[] bytes = key.toString().getBytes();
        return new SecretKeySpec(bytes, 0, bytes.length, "DES");
    }

}