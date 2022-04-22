package me.injusttice.neutron.ui.UIs.account;

import java.util.Collection;
import java.util.Set;

public interface AccountManager {

    void save();

    void load();

    void addAccount(final Account account);

    void deleteAccount(final Account account);

    Collection<Account> getAccounts();

    Collection<Account> getWorkingAccounts();

    Collection<Account> getUnbannedAccounts();

}
