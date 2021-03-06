package nl.robinlaugs.kwetter.service;

import nl.robinlaugs.kwetter.domain.Account;

import java.util.Collection;

/**
 * @author Robin Laugs
 */
public interface AccountService extends GenericService<Account> {

    /**
     * Reads an existing {@link Account}.
     *
     * @param username The {@code username} of the {@link Account} that should be read.
     * @return The {@link Account} that was read.
     */
    Account read(String username);

    /**
     * Reads an existing {@link Account}.
     *
     * @param username The {@code username} of the {@link Account} that should be read.
     * @param password The {@code password of the {@link Account} that should be read.
     * @return The {@link Account} that was read.
     * @throws Exception When the {@code username} and / or {@code password} does not belong to an existing {@link Account}.
     */
    Account read(String username, String password) throws Exception;

    /**
     * Searches all existing {@link Account Accounts}.
     *
     * @param text The search query which should (partially) match the {@code username} of existing {@link Account Account}.
     * @return The {@link Account Accounts} that were found.
     */
    Collection<Account> search(String text);

}
