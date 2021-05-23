package eu.farmingpool.farmingwallet.transactions;

import java.sql.Timestamp;

import eu.farmingpool.farmingwallet.coins.Coin;

public class TransactionRecord {
    public final Coin coin;
    public final Timestamp timestamp;
    public final double amount;
    public final String sender;

    public TransactionRecord(Coin coin, Timestamp timestamp, double amount, String sender) {
        this.coin = coin;
        this.timestamp = timestamp;
        this.amount = amount;
        this.sender = sender;
    }
}
