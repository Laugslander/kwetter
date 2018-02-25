package nl.robinlaugs.kwetter.tester;

import nl.robinlaugs.kwetter.persistence.GenericDao;
import nl.robinlaugs.kwetter.persistence.memory.BaseMemoryDao;

/**
 * @author Robin Laugs
 */
public class BaseMemoryDaoTester extends BaseMemoryDao<BaseEntityTester> implements GenericDao<BaseEntityTester> {
}
