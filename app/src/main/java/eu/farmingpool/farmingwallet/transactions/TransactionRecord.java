package eu.farmingpool.farmingwallet.transactions;

import java.sql.Timestamp;

import eu.farmingpool.farmingwallet.coins.Coin;

public class TransactionRecord {
    private final Coin coin;
    private final Timestamp timestamp;
    private final double amount;
    private final String sender;

    public TransactionRecord(Coin coin, Timestamp timestamp, double amount, String sender) {
        this.coin = coin;
        this.timestamp = timestamp;
        this.amount = amount;
        this.sender = sender;
    }

    public Coin getCoin() {
        return coin;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getSender() {
        return sender;
    }
}
