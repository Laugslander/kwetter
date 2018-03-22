package nl.robinlaugs.kwetter.tester;

import nl.robinlaugs.kwetter.service.main.BaseMainService;

import javax.ejb.Stateless;

/**
 * @author Robin Laugs
 */
@Stateless
public class BaseMainServiceTester extends BaseMainService<BaseEntityTester> {

    @Override
    public void create(BaseEntityTester entity) {
        // Do nothing
    }

    @Override
    public BaseEntityTester update(Long id, BaseEntityTester update) {
        // Do nothing

        return null;
    }

    @Override
    public BaseEntityTester read(Long id) {
        // Do nothing

        return null;
    }

}
