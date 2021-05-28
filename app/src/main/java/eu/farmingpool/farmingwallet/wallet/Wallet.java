package eu.farmingpool.farmingwallet.wallet;

import eu.farmingpool.farmingwallet.transactions.TransactionRecords;

public class Wallet {
    public final Coin coin;
    public final TransactionRecords transactions = new TransactionRecords();

    private double balance;

    public Wallet(Coin coin) {
        this.coin = coin;
    }

    public double getBalance() {
        return balance;
    }
}
