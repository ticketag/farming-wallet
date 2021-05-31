package eu.farmingpool.farmingwallet.accounts;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.keywords.Keywords;
import eu.farmingpool.farmingwallet.ui.ObservableChanges;
import eu.farmingpool.farmingwallet.utils.EncryptedSharedDataManager;

public class Accounts {
    private static Accounts instance;
    private final ArrayList<Integer> accountIds = new ArrayList<>();
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
        int accountId = accountIds.size();
        Account account = new Account(accountId, keywords);

        accountIds.add(accountId);
        onChanged();

        return account;
    }

    public void removeAccount(int accountId) {
        accountIds.remove(accountId);
        EncryptedSharedDataManager.removeAccount(accountId);

        onChanged();
    }

    @NonNull
    public Account getAccount(int accountId) {
        Account account = EncryptedSharedDataManager.getAccount(accountId);

        if (account == null)
            throw new IllegalStateException("Account " + accountId + "does not exist");

        return account;
    }

    public Account getCurrentAccount() {
        return EncryptedSharedDataManager.getAccount(currentAccountId);
    }

    public void setCurrentAccount(int accountId) {
        currentAccountId = accountId;

        onChanged();
    }

    public int getCount() {
        return accountIds.size();
    }

    private void onChanged() {
        EncryptedSharedDataManager.putAccounts(instance);
        ObservableChanges.getInstance().onAccountsChanged();
    }
}
