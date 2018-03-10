package nl.robinlaugs.kwetter.api.dto;

import lombok.Getter;
import nl.robinlaugs.kwetter.domain.Account;
import nl.robinlaugs.kwetter.domain.Role;

/**
 * @author Robin Laugs
 */
@Getter
public class AccountDto extends BaseEntityDto {

    private String username;
    private Role role;

    private UserDto user;

    AccountDto(Account account) {
        super(account.getId(), account.getTimestamp());

        username = account.getUsername();
        role = account.getRole();
    }

    public AccountDto(Account account, boolean fat) {
        this(account);

        if (fat) {
            user = new UserDto(account.getUser());
        }
    }

}

