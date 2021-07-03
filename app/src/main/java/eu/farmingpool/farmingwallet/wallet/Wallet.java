package eu.farmingpool.farmingwallet.wallet;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.keys.ChiaKeysManager;
import eu.farmingpool.farmingwallet.transactions.TransactionRecord;
import eu.farmingpool.farmingwallet.transactions.TransactionRecordEventHandler;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;

import java.util.Vector;


public class Wallet {

    public final Coin coin;
    public final TransactionRecords transactions = new TransactionRecords();
    private final ChiaKeysManager keysManager;
    private Vector<TransactionRecordEventHandler> eventHandlers = new Vector<>();



    private long balance;

    public Wallet(Coin coin) {
        super();
        this.coin = coin;
        this.keysManager = ChiaKeysManager.getInstance();
        eventHandlers = new Vector<>();
    }

    public void insertTransactions(TransactionRecords transactions) {
        this.transactions.insert(transactions);
        if (eventHandlers != null) {
            eventHandlers.forEach(handler -> handler.onTransactionRecordsLoaded(this.transactions,this.balance ));
        }
    }

    public void insertTransaction(TransactionRecord transaction) {
        this.transactions.insert(transaction);
    }

    public TransactionRecords getTransactions() {
        return transactions;
    }

    public void listenEvents(TransactionRecordEventHandler handler) {
        if (eventHandlers == null) {
            eventHandlers = new Vector<>();
        }
        eventHandlers.add(handler);
    }

    public void setTransactions(TransactionRecords transactions) {
        this.transactions.clear();
        this.transactions.insert(transactions);
        if (eventHandlers != null) {
            eventHandlers.forEach(handler -> handler.onTransactionRecordsLoaded(this.transactions, this.balance));
        }
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getMainAddress(Account account) {
        return ChiaKeysManager.getInstance().getWalletAddress(account);
    }

}
