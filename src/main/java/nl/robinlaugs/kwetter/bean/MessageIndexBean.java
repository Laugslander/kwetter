package nl.robinlaugs.kwetter.bean;

import lombok.Getter;
import lombok.Setter;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.service.MessageService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

import static java.util.Objects.nonNull;

/**
 * @author Robin Laugs
 */
@Named(value = "messageIndexBean")
@RequestScoped
public class MessageIndexBean implements Serializable {

    @Inject
    private MessageService service;

    @Getter
    @Setter
    private String filter;

    public Collection<Message> getMessages() {
        return nonNull(filter) && filter.trim().length() > 0 ? service.search(filter) : service.readAll();
    }

    public void delete(Long id) {
        service.delete(id);
    }

}
