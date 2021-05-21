package eu.farmingpool.farmingwallet.ui.wallet;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eu.farmingpool.farmingwallet.transactions.TransactionRecords;

public class TransactionRecordsViewModel extends ViewModel {
    private final MutableLiveData<TransactionRecords> transactionRecords = new MutableLiveData<>();

    public MutableLiveData<TransactionRecords> getTransactionRecords() {
        return transactionRecords;
    }

    public void setTransactionRecords(TransactionRecords transactionRecords) {
        this.transactionRecords.setValue(transactionRecords);
    }
}