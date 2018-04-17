package nl.robinlaugs.kwetter.websocket;

import nl.robinlaugs.kwetter.domain.User;
import nl.robinlaugs.kwetter.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.lang.Thread.sleep;
import static java.util.logging.Level.INFO;
import static java.util.logging.Logger.getLogger;

/**
 * @author Robin Laugs
 */
@Singleton
@Startup
@ServerEndpoint("/socket/{id}")
public class WebSocket {

    private static final Logger logger = getLogger(WebSocket.class.getName());

    @Inject
    private UserService userService;

    private Map<String, Session> sessions;

    @PostConstruct
    private void setUp() {
        sessions = new HashMap<>();
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        sessions.put(id, session);

        logger.log(INFO, format("Session opened by used with id %s", id));
    }

    @OnClose
    public void onClose(Session session, @PathParam("id") String id) {
        sessions.remove(id);

        logger.log(INFO, format("Session closed by used with id %s", id));
    }

    @OnMessage
    public void onMessage(String id) throws Exception {
        logger.log(INFO, format("Message received by used with id %s", id));

        User user = userService.read(parseLong(id));

        sleep(500L);

        sessions.forEach((k, v) -> user.getFollowers().forEach(f -> {
            if (f.getId().equals(parseLong(k))) {
                v.getAsyncRemote().sendText(id);

                logger.log(INFO, format("Session of id %s  notified", k));
            }
        }));
    }

}
