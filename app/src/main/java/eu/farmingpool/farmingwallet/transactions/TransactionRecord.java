package eu.farmingpool.farmingwallet.transactions;

import java.sql.Timestamp;

import eu.farmingpool.farmingwallet.wallet.Coin;

public class TransactionRecord {
    public final Coin coin;
    public final Timestamp timestamp;
    public final double amount;
    public final String sender;
    public final long blockNumber;
    private long confirmationCounter;
    private TransactionStatus transactionStatus;


    public TransactionRecord(Coin coin, Timestamp timestamp, double amount, String sender, long blockNumber) {
        this.coin = coin;
        this.timestamp = timestamp;
        this.amount = amount;
        this.sender = sender;
        this.blockNumber = blockNumber;
        this.transactionStatus = TransactionStatus.UNKNOWN;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setConfirmationCounter(long confirmationCounter) {
        this.confirmationCounter = confirmationCounter;
    }
}
