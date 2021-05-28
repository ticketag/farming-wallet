package eu.farmingpool.farmingwallet.accounts;

import androidx.annotation.NonNull;

import java.util.HashMap;

import eu.farmingpool.farmingwallet.keywords.Keywords;
import eu.farmingpool.farmingwallet.utils.EncryptedSharedDataManager;

public class Accounts {
    private static Accounts instance;
    private final HashMap<Integer, Account> accounts = new HashMap<>();
    private int currentAccountId;

    private Accounts() {
    }

    public static Accounts getInstance() {
        if (instance == null) {
            synchronized (Accounts.class) {
                if (instance == null) {
                    instance = EncryptedSharedDataManager.getAccounts();

                    if (instance == null)
                        instance = new Accounts();
                }
            }
        }

        return instance;
    }

    public Account addAccount(Keywords keywords) {
        int accountId = accounts.size();
        Account account = new Account(accountId, keywords);

        accounts.put(account.getId(), account);
        consolidate();

        return account;
    }

    public void removeAccount(int accountId) {
        accounts.remove(accountId);
    }

    @NonNull
    public Account getAccount(int accountId) {
        Account account = accounts.get(accountId);

        if (account == null)
            throw new IllegalStateException("Account " + accountId + "does not exist");

        return account;
    }

    public Account getCurrentAccount() {
        return accounts.get(currentAccountId);
    }

    public void setCurrentAccount(int accountId) {
        currentAccountId = accountId;

        consolidate();
    }

    public int getCount() {
        return accounts.size();
    }

    private void consolidate() {
        EncryptedSharedDataManager.putAccounts(instance);
    }
}
