package eu.farmingpool.farmingwallet.transactions;

import androidx.collection.ArraySet;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionRecords {
    private final ArraySet<TransactionRecord> transactionRecords = new ArraySet<>();

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

    public Set<TransactionRecord> getTransactionRecords() {
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
                valid = transactionRecord.getTimestamp().after(timestampFrom);

            if (timestampFrom != null)
                valid = transactionRecord.getTimestamp().before(timestampTo);

            return valid;
        };
    }
}
