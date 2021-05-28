package eu.farmingpool.farmingwallet.transactions;

import java.util.Observable;

public class ObservableTransactionRecords extends Observable {
    private static ObservableTransactionRecords instance;
    private TransactionRecords transactions;

    private ObservableTransactionRecords() {
        transactions = new TransactionRecords();
    }

    public static ObservableTransactionRecords getInstance() {
        if (instance == null) {
            synchronized (ObservableTransactionRecords.class) {
                if (instance == null) {
                    instance = new ObservableTransactionRecords();
                }
            }
        }

        return instance;
    }

    public void insertTransactions(TransactionRecords transactions) {
        this.transactions.insert(transactions);

        onChanged();
    }

    public void insertTransaction(TransactionRecord transaction) {
        this.transactions.insert(transaction);

        onChanged();
    }

    public TransactionRecords getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionRecords transactions) {
        this.transactions = transactions;

        onChanged();
    }

    private void onChanged() {
        setChanged();
        notifyObservers();
    }
}
