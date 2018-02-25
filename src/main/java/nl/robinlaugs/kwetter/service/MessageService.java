package nl.robinlaugs.kwetter.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.persistence.MessageDao;
import nl.robinlaugs.kwetter.persistence.jpa.JpaDao;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;

/**
 * @author Robin Laugs
 */
@Stateless
@AllArgsConstructor(onConstructor = @__(@Inject))
@NoArgsConstructor
public class MessageService extends BaseService<Message> {

    @JpaDao
    private MessageDao dao;

    @PostConstruct
    private void setUp() {
        super.setDao(dao);
    }

    public Collection<Message> search(String text) {
        return dao.readAll().stream()
                .filter(m -> m.getText().toLowerCase().contains(text.toLowerCase()))
                .sorted()
                .collect(toSet());
    }

}
