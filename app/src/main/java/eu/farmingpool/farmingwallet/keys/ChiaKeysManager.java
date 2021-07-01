package eu.farmingpool.farmingwallet.keys;


import org.jetbrains.annotations.NotNull;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.transactions.Transaction;
import eu.farmingpool.farmingwallet.wallet.Coin;
import eu.farmingwallet.bls_android.ChiaKeyManager;

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
    public String getWalletAddress(@NotNull Account account) {
        String keywords = account.retrieveKeywords().toPlainString();
        return ChiaKeyManager.getAddress(keywords);
    }

    @Override
    public Key getMasterKey(Account account) {
        byte[] masterKey = ChiaKeyManager.getMasterKey(account.retrieveKeywords().toPlainString());
        return new Key(masterKey);
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
