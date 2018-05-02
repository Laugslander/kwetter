package nl.robinlaugs.kwetter.api.v2.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.Account;

import javax.json.JsonObject;
import java.net.URI;

import static java.lang.String.format;
import static javax.json.Json.createObjectBuilder;

/**
 * @author Robin Laugs
 */
@Getter
public class AccountV2Dto extends BaseEntityV2Dto {

    private transient Account account;

    public AccountV2Dto(Account account, URI uri) {
        super(account.getId(), uri);

        this.account = account;

        data = generateData();
    }

    private JsonObject generateData() {
        return createObjectBuilder()
                .add("type", account.getClass().getSimpleName().toLowerCase())
                .add("id", id)
                .add("liks", getLinks())
                .add("attributes", getAttributes())
                .add("relationships", getRelationships())
                .build();
    }

    private JsonObject getLinks() {
        return createObjectBuilder()
                .add("self", format("%saccountsv2/%d", uri, id))
                .build();
    }

    private JsonObject getAttributes() {
        return createObjectBuilder()
                .add("timestamp", account.getTimestamp().toString())
                .add("username", account.getUsername())
                .add("role", account.getRole().toString())
                .build();
    }

    private JsonObject getRelationships() {
        UserV2Dto user = new UserV2Dto(account.getUser(), uri);

        return createObjectBuilder()
                .add("user", user.getRelation())
                .build();
    }

}

