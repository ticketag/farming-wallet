package eu.farmingpool.farmingwallet.keys;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.transactions.Transaction;
import eu.farmingpool.farmingwallet.wallet.Coin;

public class ChiaKeysManager extends KeysManager {
    private static ChiaKeysManager instance;

    private ChiaKeysManager() {
        super(Coin.XCH);
    }

    public static ChiaKeysManager getInstance() {
        if (instance == null) {
            synchronized (ChiaKeysManager.class) {
                if (instance == null) {
                    instance = new ChiaKeysManager();
                }
            }
        }

        return instance;
    }

    @Override
    public Transaction generateTransaction(Account account, double amount, Key receivingAddress) {
        return null;
    }

    @Override
    public Key getMasterKey(Account account) {
        return null;
    }

    @Override
    public Key getPublicKey(Account account, String tag) {
        return null;
    }

    public Key getFarmingKey(Account account) {
        return null;
    }

    public Key getPoolKey(Account account) {
        return null;
    }
}
