package nl.robinlaugs.kwetter.bean;

import lombok.Getter;
import lombok.Setter;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.service.AccountService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

import static java.util.Objects.nonNull;

/**
 * @author Robin Laugs
 */
@Named(value = "accountIndexBean")
@RequestScoped
public class AccountIndexBean {

    @Inject
    private AccountService service;

    @Getter
    @Setter
    private String filter;

    public Collection<Account> getAccounts() {
        return nonNull(filter) && filter.trim().length() > 0 ? service.search(filter) : service.readAll();
    }

    public void delete(Long id) {
        service.delete(id);
    }

}
