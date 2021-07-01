package eu.farmingpool.farmingwallet.wallet;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.keys.ChiaKeysManager;
import eu.farmingpool.farmingwallet.keys.KeysManager;
import eu.farmingpool.farmingwallet.transactions.TransactionRecord;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;

public class Wallet {
    public final Coin coin;
    public final TransactionRecords transactions = new TransactionRecords();
    private final ChiaKeysManager keysManager;

    private double balance;

    public Wallet(Coin coin) {
        this.coin = coin;
        this.keysManager = ChiaKeysManager.getInstance();
    }

    public void insertTransactions(TransactionRecords transactions) {
        this.transactions.insert(transactions);
    }

    public void insertTransaction(TransactionRecord transaction) {
        this.transactions.insert(transaction);
    }

    public TransactionRecords getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionRecords transactions) {
        this.transactions.clear();
        this.transactions.insert(transactions);
    }

    public double getBalance() {
        return balance;
    }

    public String getMainAddress(Account account) {
        return ChiaKeysManager.getInstance().getWalletAddress(account);
    }
}
