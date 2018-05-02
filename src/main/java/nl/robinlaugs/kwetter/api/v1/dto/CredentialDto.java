package nl.robinlaugs.kwetter.api.v1.dto;

/**
 * @author Robin Laugs
 */
public class CredentialDto {

    private Long id;
    private String token;

    public CredentialDto(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
