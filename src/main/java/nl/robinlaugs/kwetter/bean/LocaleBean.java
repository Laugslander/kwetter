package nl.robinlaugs.kwetter.bean;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 * @author Robin Laugs
 */
@Named(value = "localeBean")
@SessionScoped
public class LocaleBean implements Serializable {

    @Getter
    @Setter
    private Locale locale;

    @PostConstruct
    public void init() {
        locale = getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        getCurrentInstance().getViewRoot().setLocale(locale);
    }

}