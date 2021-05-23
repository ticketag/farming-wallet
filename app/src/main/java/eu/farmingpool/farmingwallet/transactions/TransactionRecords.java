package eu.farmingpool.farmingwallet.transactions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionRecords {
    private final ArrayList<TransactionRecord> transactionRecords = new ArrayList<>();

    public int size() {
        return transactionRecords.size();
    }

    public void clear() {
        transactionRecords.clear();
    }

    public void insert(TransactionRecord transactionRecord) {
        transactionRecords.add(transactionRecord);
    }

    public void insert(Collection<TransactionRecord> transactionRecords) {
        this.transactionRecords.addAll(transactionRecords);
    }

    public void insert(TransactionRecords transactionRecords) {
        this.transactionRecords.addAll(transactionRecords.getTransactionRecords());
    }

    public TransactionRecord get(int i) {
        return this.transactionRecords.get(i);
    }

    public ArrayList<TransactionRecord> getTransactionRecords() {
        return transactionRecords;
    }

    public Set<TransactionRecord> getTransactionRecords(Timestamp timestampFrom, Timestamp timestampTo) {
        Predicate<TransactionRecord> predicate = getPredicate(timestampFrom, timestampTo);

        return transactionRecords.stream().filter(predicate).collect(Collectors.toSet());
    }

    private Predicate<TransactionRecord> getPredicate(Timestamp timestampFrom, Timestamp timestampTo) {

        return transactionRecord -> {
            boolean valid = true;

            if (timestampFrom != null)
                valid = transactionRecord.timestamp.after(timestampFrom);

            if (timestampFrom != null)
                valid = valid && transactionRecord.timestamp.before(timestampTo);

            return valid;
        };
    }
}
