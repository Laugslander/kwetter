package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.Message;
import nl.robinlaugs.kwetter.domain.Topic;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Robin Laugs
 */
public interface TopicService extends GenericService<Topic> {

    /**
     * Reads an existing {@link Topic}.
     *
     * @param name The {@code name} of the existing {@link Topic} that should be read.
     * @return The {@link Topic} that was read.
     */
    Topic read(String name);

    /**
     * Reads all existing trending {@link Topic Topics}.
     *
     * @param from  The {@link LocalDateTime} from when all existing {@link Topic Topics} should be read.
     * @param limit The maximum number of {@link Message Messages} that should be read.
     * @return The {@link Message Messages} that were read.
     */
    Collection<Topic> readTrendingTopics(LocalDateTime from, int limit);

}
