package nl.robinlaugs.kwetter.api.v2.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.User;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.net.URI;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static javax.json.Json.createArrayBuilder;
import static javax.json.Json.createObjectBuilder;

/**
 * @author Robin Laugs
 */
@Getter
public class UserV2Dto extends BaseEntityV2Dto {

    private transient User user;

    public UserV2Dto(User user, URI uri, boolean fat) {
        super(user.getId(), uri);

        this.user = user;

        data = generateData(User.class, fat);
    }

    @Override
    JsonObject getRelationship() {
        return createObjectBuilder()
                .add("id", id)
                .add("link", format("%susersv2/%d", uri, id))
                .add("attributes", generateAttributes())
                .build();
    }

    @Override
    JsonObject generateAttributes() {
        String name = user.getName();
        String avatar = user.getAvatar();
        String location = user.getLocation();
        String website = user.getWebsite();
        String bio = user.getBio();

        JsonObjectBuilder builder = createObjectBuilder()
                .add("timestamp", user.getTimestamp().toString());

        if (nonNull(name)) builder.add("name", name);
        if (nonNull(avatar)) builder.add("avatar", avatar);
        if (nonNull(location)) builder.add("location", location);
        if (nonNull(website)) builder.add("website", website);
        if (nonNull(bio)) builder.add("bio", bio);

        return builder.build();
    }

    @Override
    JsonObject generateRelationships() {
        AccountV2Dto account = new AccountV2Dto(user.getAccount(), uri, false);

        JsonArrayBuilder followingsBuilder = createArrayBuilder();
        JsonArrayBuilder followersBuilder = createArrayBuilder();
        JsonArrayBuilder messagesBuilder = createArrayBuilder();
        JsonArrayBuilder likedBuilder = createArrayBuilder();
        JsonArrayBuilder mentionedBuilder = createArrayBuilder();

        user.getFollowings().stream()
                .map(f -> new UserV2Dto(f, uri, false).getRelationship())
                .forEach(followingsBuilder::add);

        user.getFollowers().stream()
                .map(f -> new UserV2Dto(f, uri, false).getRelationship())
                .forEach(followersBuilder::add);

        user.getMessages().stream()
                .map(m -> new MessageV2Dto(m, uri, false).getRelationship())
                .forEach(messagesBuilder::add);

        user.getLiked().stream()
                .map(m -> new MessageV2Dto(m, uri, false).getRelationship())
                .forEach(likedBuilder::add);

        user.getMentioned().stream()
                .map(m -> new MessageV2Dto(m, uri, false).getRelationship())
                .forEach(mentionedBuilder::add);

        return createObjectBuilder()
                .add("account", account.getRelationship())
                .add("followings", followingsBuilder.build())
                .add("followers", followersBuilder.build())
                .add("messages", messagesBuilder.build())
                .add("liked", likedBuilder.build())
                .add("mentioned", mentionedBuilder.build())
                .build();
    }

}
