package nl.robinlaugs.kwetter.bean;

import lombok.Getter;
import lombok.Setter;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Role;
import nl.robinlaugs.kwetter.service.AccountService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static nl.robinlaugs.kwetter.domain.Role.values;

/**
 * @author Robin Laugs
 */
@Named(value = "accountCreateBean")
@RequestScoped
public class AccountCreateBean {

    private static final String ACCOUNT_INDEX = "index";

    @Inject
    private AccountService service;

    @Getter
    @Setter
    private Account account;

    @PostConstruct
    public void init() {
        account = new Account();
    }

    public String create() {
        try {
            service.create(account);

            return ACCOUNT_INDEX;
        } catch (Exception e) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_WARN, e.getMessage(), null));

            return null;
        }
    }

    public Role[] getRoles() {
        return values();
    }

}
