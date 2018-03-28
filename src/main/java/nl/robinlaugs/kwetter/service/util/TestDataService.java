package nl.robinlaugs.kwetter.service.util;

import lombok.extern.java.Log;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.AccountService;
import nl.robinlaugs.kwetter.service.MessageService;
import nl.robinlaugs.kwetter.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import static java.time.LocalDateTime.of;
import static java.time.Month.JANUARY;
import static java.util.logging.Level.WARNING;
import static nl.robinlaugs.kwetter.domain.Role.ADMINISTRATOR;
import static nl.robinlaugs.kwetter.domain.Role.MODERATOR;

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

    @Inject
    private UserService userService;

    @PostConstruct
    private void setUp() {
        try {
            Account account1 = new Account("Laugslander", "welkom");
            Account account2 = new Account("bras1223", "welkom");
            Account account3 = new Account("Ranray", "welkom");
            Account account4 = new Account("BeeHiveJava", "welkom");
            Account account5 = new Account("Isylwin", "welkom");

            account1.setRole(ADMINISTRATOR);
            account2.setRole(MODERATOR);

            accountService.create(account1);
            accountService.create(account2);
            accountService.create(account3);
            accountService.create(account4);
            accountService.create(account5);

            User user1 = account1.getUser();
            user1.setName("Robin Laugs");
            user1.setAvatar("https://avatars0.githubusercontent.com/u/16139317?s=460&v=4");
            user1.setLocation("Ohé en Laak");
            user1.setWebsite("www.robinlaugs.nl");
            user1.setBio("Ontwikkelaar bij Kwetter");

            User user2 = account2.getUser();
            user2.setName("Luuk Hermans");
            user2.setAvatar("https://avatars3.githubusercontent.com/u/11488443?s=460&v=4");
            user2.setLocation("Weert");

            User user3 = account3.getUser();
            user3.setName("Raymond Limpens");
            user3.setAvatar("https://avatars3.githubusercontent.com/u/21307259?s=460&v=4");
            user3.setLocation("Elsloo");

            User user4 = account4.getUser();
            user4.setName("Lesley Vente");
            user4.setAvatar("https://avatars2.githubusercontent.com/u/5957362?s=460&v=4");
            user4.setLocation("Brandevoort");

            User user5 = account5.getUser();
            user5.setName("Oscar de Leeuw");
            user5.setAvatar("https://avatars3.githubusercontent.com/u/4661294?s=460&v=4");
            user5.setLocation("Eindhoven");

            userService.follow(user1, user2);
            userService.follow(user1, user3);
            userService.follow(user1, user4);
            userService.follow(user1, user5);

            userService.follow(user2, user1);
            userService.follow(user2, user3);

            userService.follow(user3, user1);
            userService.follow(user3, user2);

            userService.follow(user4, user1);
            userService.follow(user4, user3);
            userService.follow(user4, user5);

            userService.follow(user5, user1);
            userService.follow(user5, user3);
            userService.follow(user5, user4);

            Message message1 = new Message("Ik vind #kwetter vet cool @bras1223");
            message1.setAuthor(user1);
            message1.setTimestamp(of(2018, JANUARY, 1, 0, 0));

            Message message2 = new Message("Ik denk dat ik #kwetter vaker ga gebruiken!");
            message2.setAuthor(user1);
            message2.setTimestamp(of(2018, JANUARY, 2, 0, 0));

            Message message3 = new Message("@Laugslander #kwetter is inderdaad #super");
            message3.setAuthor(user2);
            message3.setTimestamp(of(2018, JANUARY, 3, 0, 0));

            Message message4 = new Message("Kwetter topics zijn #top");
            message4.setAuthor(user3);
            message4.setTimestamp(of(2018, JANUARY, 4, 0, 0));

            Message message5 = new Message("Vandaag flink aan de #proftaak gewerkt!");
            message5.setAuthor(user4);
            message5.setTimestamp(of(2018, JANUARY, 5, 0, 0));

            Message message6 = new Message("Bijna vakantie @BeeHiveJava #eindelijk");
            message6.setAuthor(user5);
            message6.setTimestamp(of(2018, JANUARY, 6, 0, 0));

            messageService.create(message1);
            messageService.create(message2);
            messageService.create(message3);
            messageService.create(message4);
            messageService.create(message5);
            messageService.create(message6);

            messageService.like(message1, user2);
            messageService.like(message3, user3);
            messageService.like(message5, user1);
            messageService.like(message5, user2);
            messageService.like(message5, user3);
        } catch (Exception e) {
            log.log(WARNING, "An error occurred while setting up test data", e);
        }
    }

    @PreDestroy
    private void cleanUp() {
        // TODO clean up test data
    }

}
