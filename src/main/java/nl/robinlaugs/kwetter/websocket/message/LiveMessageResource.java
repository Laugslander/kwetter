package nl.robinlaugs.kwetter.websocket.message;

import nl.robinlaugs.kwetter.api.dto.MessageDto;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.service.MessageService;
import nl.robinlaugs.kwetter.service.listener.MessageListener;
import nl.robinlaugs.kwetter.websocket.HttpSessionProvider;
import nl.robinlaugs.kwetter.websocket.message.codec.MessageJsonDecoder;
import nl.robinlaugs.kwetter.websocket.message.codec.MessageJsonEncoder;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.logging.Level.INFO;

/**
 * @author Robin Laugs
 */
@ServerEndpoint(
        value = "/socket/{id}",
        encoders = MessageJsonEncoder.class,
        decoders = MessageJsonDecoder.class,
        configurator = HttpSessionProvider.class
)
public class LiveMessageResource implements MessageListener {

    private static final Logger logger = Logger.getLogger(LiveMessageResource.class.getName());

    @Inject
    private MessageService messageService;

    private Session session;
    private String id;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        this.session = session;
        this.id = id;

        messageService.addListener(this);

        logger.log(INFO, format("Session opened by id %s", id));
    }

    @OnClose
    public void onClose(@PathParam("id") String id) {
        this.session = null;
        this.id = id;

        messageService.removeListener(this);

        logger.log(INFO, format("Session closed by id %s", id));
    }

    @Override
    public void onMessage(Message message) {
        message.getAuthor().getFollowers().stream()
                .filter(f -> f.getId().toString().equals(id))
                .forEach(f -> session.getAsyncRemote().sendObject(new MessageDto(message)));
    }
}
