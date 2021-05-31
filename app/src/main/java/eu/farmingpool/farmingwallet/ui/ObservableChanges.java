package eu.farmingpool.farmingwallet.ui;

import java.util.Observable;

public class ObservableChanges extends Observable {
    private static ObservableChanges instance;

    private ObservableChanges() {
    }

    public static ObservableChanges getInstance() {
        if (instance == null) {
            synchronized (ObservableChanges.class) {
                if (instance == null)
                    instance = new ObservableChanges();
            }
        }

        return instance;
    }

    public void onAccountsChanged() {
        onChanged(EntityChanged.ACCOUNTS);
    }

    public void onAccountChanged() {
        onChanged(EntityChanged.ACCOUNT);
    }

    public void onCurrentAccountChanged() {
        onChanged(EntityChanged.CURRENT_ACCOUNT);
    }

    public void onCurrentWalletChanged() {
        onChanged(EntityChanged.CURRENT_WALLET);
    }

    public void onCurrentCoinsChanged() {
        onChanged(EntityChanged.CURRENT_COINS);
    }

    private void onChanged(EntityChanged currentAccount) {
        setChanged();
        notifyObservers(currentAccount);
    }

    public enum EntityChanged {
        CURRENT_ACCOUNT,
        ACCOUNTS,
        ACCOUNT,
        CURRENT_WALLET,
        CURRENT_COINS
    }
}
