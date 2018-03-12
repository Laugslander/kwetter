package nl.robinlaugs.kwetter.service.util;

import lombok.extern.java.Log;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.AccountService;
import nl.robinlaugs.kwetter.service.MessageService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static java.util.logging.Level.WARNING;
import static nl.robinlaugs.kwetter.domain.Role.ADMINISTRATOR;

/**
 * @author Robin Laugs
 */
@Singleton
@Startup
@Log
public class TestDataService {

    @Inject
    private MessageService messageService;

    @Inject
    private AccountService accountService;

    @PostConstruct
    private void setUp() {
        try {
            Account account1 = new Account("Laugslander", "welkom");
            account1.setRole(ADMINISTRATOR);

            Account account2 = new Account("bras1223", "welkom");

            accountService.create(account1);
            accountService.create(account2);

            User user1 = account1.getUser();
            user1.setName("Robin Laugs");
            user1.setLocation("Ohé en Laak");

            User user2 = account2.getUser();
            user2.setName("Luuk Hermans");
            user2.setLocation("Weert");

            user1.getFollowers().add(user2);
            user1.getFollowings().add(user2);
            user2.getFollowers().add(user1);
            user2.getFollowings().add(user1);

            Message message1 = new Message();
            message1.setText("#kwetter is vet cool @bras1223");
            message1.setAuthor(user1);
            message1.setTimestamp(of(2018, JANUARY, 1, 0, 0));

            Message message2 = new Message();
            message2.setText("@Laugslander #kwetter is inderdaad #super");
            message2.setAuthor(user2);
            message2.setTimestamp(of(2018, JANUARY, 2, 0, 0));

            messageService.create(message1);
            messageService.create(message2);

            message1.getLikes().add(user2);
            message2.getLikes().add(user1);
        } catch (Exception e) {
            log.log(WARNING, "An error occurred while setting up test data", e);
        }
    }

    @PreDestroy
    private void cleanUp() {
        // TODO clean up test data
    }

}
