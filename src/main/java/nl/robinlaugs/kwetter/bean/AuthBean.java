package nl.robinlaugs.kwetter.bean;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 * @author Robin Laugs
 */
@Named(value = "authBean")
@Dependent
public class AuthBean {

    private static final String MODERATOR = "Moderator";
    private static final String ADMINISTRATOR = "Administrator";

    public String logOut() {
        ((HttpSession) getCurrentInstance().getExternalContext().getSession(false)).invalidate();

        return "/auth/login.xhtml?faces-redirect=true";
    }

    public boolean loggedIn() {
        return nonNull(getCurrentInstance().getExternalContext().getUserPrincipal());
    }

    public boolean isModerator() {
        return getCurrentInstance().getExternalContext().isUserInRole(MODERATOR);
    }

    public boolean isAdministrator() {
        return getCurrentInstance().getExternalContext().isUserInRole(ADMINISTRATOR);
    }

}
