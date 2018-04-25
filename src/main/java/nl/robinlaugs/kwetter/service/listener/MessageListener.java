package nl.robinlaugs.kwetter.service.listener;

import nl.robinlaugs.kwetter.domain.Message;

/**
 * @author Robin Laugs
 */
public interface MessageListener {

    void onMessage(Message message);

}
