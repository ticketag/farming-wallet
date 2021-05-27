package eu.farmingpool.farmingwallet.keys;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.coins.Coin;

public abstract class KeysManager {
    protected final Coin coin;

    protected KeysManager(Coin coin) {
        this.coin = coin;
    }

    public abstract String generateTransaction(Account account, double amount, Key receivingAddress);

    public abstract Key getMasterKey(Account account);

    public abstract Key getPublicKey(Account account, String tag);
}
