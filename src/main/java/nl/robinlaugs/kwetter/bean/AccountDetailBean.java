package nl.robinlaugs.kwetter.bean;

import lombok.Getter;
import lombok.Setter;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Role;
import nl.robinlaugs.kwetter.service.AccountService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Robin Laugs
 */
@Named(value = "accountDetailBean")
@SessionScoped
public class AccountDetailBean implements Serializable {

    private static final String ACCOUNT_INDEX = "index";

    @Inject
    private AccountService service;

    @Getter
    private Account account;

    @Getter
    @Setter
    private Long id;

    public void read() throws Exception {
        account = service.read(id);
    }

    public String update() throws Exception {
        Account update = new Account();
        update.setRole(account.getRole());

        account = service.update(id, update);

        return ACCOUNT_INDEX;
    }

    public String delete() {
        service.delete(account.getId());

        return ACCOUNT_INDEX;
    }

    public Role[] getRoles() {
        return Role.values();
    }

}
