package eu.farmingpool.farmingwallet.transactions;

public interface TransactionRecordEventHandler {
    void onTransactionRecordsLoaded(TransactionRecords records, long balance);
}
