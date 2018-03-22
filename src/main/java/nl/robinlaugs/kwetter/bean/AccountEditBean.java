package nl.robinlaugs.kwetter.bean;

import lombok.Getter;
import lombok.Setter;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Role;
import nl.robinlaugs.kwetter.service.AccountService;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static nl.robinlaugs.kwetter.domain.Role.values;

/**
 * @author Robin Laugs
 */
@Named(value = "accountEditBean")
@ViewScoped
public class AccountEditBean implements Serializable {

    private static final String ACCOUNT_INDEX = "index";

    @Inject
    private AccountService accountService;

    @Getter
    @Setter
    private Account account;

    @Getter
    @Setter
    private Account update;

    @Getter
    @Setter
    private Long id;

    public void read() throws Exception {
        account = accountService.read(id);
        update = accountService.read(id);
    }

    public String update() {
        Account update = getUpdates();

        try {
            account = accountService.update(id, update);

            return ACCOUNT_INDEX;
        } catch (Exception e) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_WARN, e.getMessage(), null));

            return null;
        }
    }

    public String delete() {
        accountService.delete(account.getId());

        return ACCOUNT_INDEX;
    }

    public Role[] getRoles() {
        return values();
    }

    private Account getUpdates() {
        Account update = new Account();

        String username = this.update.getUsername();
        String password = this.update.getPassword();

        if (!account.getUsername().equals(username)) update.setUsername(username);
        if (!account.getPassword().equals(password)) update.setPassword(password);

        update.setRole(this.update.getRole());

        return update;
    }

}
