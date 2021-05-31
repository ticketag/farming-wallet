package eu.farmingpool.farmingwallet.wallet;

import eu.farmingpool.farmingwallet.transactions.TransactionRecord;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;

public class Wallet {
    public final Coin coin;
    public final TransactionRecords transactions = new TransactionRecords();

    private double balance;

    public Wallet(Coin coin) {
        this.coin = coin;
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
}
