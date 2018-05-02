package nl.robinlaugs.kwetter.api.v1.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.User;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

/**
 * @author Robin Laugs
 */
@Getter
public class UserDto extends BaseEntityDto {

    private String name;
    private String avatar;
    private String location;
    private String website;
    private String bio;

    private AccountDto account;
    private Collection<UserDto> followings;
    private Collection<UserDto> followers;
    private Collection<MessageDto> messages;
    private Collection<MessageDto> liked;
    private Collection<MessageDto> mentioned;

    UserDto(User user) {
        super(user.getId(), user.getTimestamp());

        name = user.getName();
        avatar = user.getAvatar();
        location = user.getLocation();
        website = user.getWebsite();
        bio = user.getBio();
    }

    public UserDto(User user, boolean fat) {
        this(user);

        if (fat) {
            account = new AccountDto(user.getAccount());
            followings = user.getFollowings().stream().map(UserDto::new).collect(toSet());
            followers = user.getFollowers().stream().map(UserDto::new).collect(toSet());
            messages = user.getMessages().stream().map(MessageDto::new).collect(toSet());
            liked = user.getLiked().stream().map(MessageDto::new).collect(toSet());
            mentioned = user.getMentioned().stream().map(MessageDto::new).collect(toSet());
        }
    }

}
