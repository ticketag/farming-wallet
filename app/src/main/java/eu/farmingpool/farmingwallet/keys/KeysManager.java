package eu.farmingpool.farmingwallet.keys;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.coins.Coin;
import eu.farmingpool.farmingwallet.transactions.Transaction;

public abstract class KeysManager {
    protected final Coin coin;

    protected KeysManager(Coin coin) {
        this.coin = coin;
    }

    public abstract Transaction generateTransaction(Account account, double amount, Key receivingAddress);

    public abstract Key getMasterKey(Account account);

    public abstract Key getPublicKey(Account account, String tag);
}
