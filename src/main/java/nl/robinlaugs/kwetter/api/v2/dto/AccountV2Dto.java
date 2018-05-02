package nl.robinlaugs.kwetter.api.v2.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.Account;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.net.URI;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static javax.json.Json.createObjectBuilder;

/**
 * @author Robin Laugs
 */
@Getter
public class AccountV2Dto extends BaseEntityV2Dto {

    private transient Account account;

    public AccountV2Dto(Account account, URI uri, boolean fat) {
        super(account.getId(), uri);

        this.account = account;

        data = generateData(Account.class, fat);
    }

    @Override
    JsonObject getRelationship() {
        return createObjectBuilder()
                .add("id", id)
                .add("link", format("%saccountsv2/%d", uri, id))
                .add("attributes", generateAttributes())
                .build();
    }

    @Override
    JsonObject generateAttributes() {
        String username = account.getUsername();
        String role = account.getRole().toString();

        JsonObjectBuilder builder = createObjectBuilder()
                .add("timestamp", account.getTimestamp().toString());

        if (nonNull(username)) builder.add("username", username);
        if (nonNull(role)) builder.add("role", role);

        return builder.build();
    }

    @Override
    JsonObject generateRelationships() {
        UserV2Dto user = new UserV2Dto(account.getUser(), uri, false);

        return createObjectBuilder()
                .add("user", user.getRelationship())
                .build();
    }

}

