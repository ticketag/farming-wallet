package eu.farmingpool.farmingwallet.blockchain;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.application.GlobalApplication;
import eu.farmingpool.farmingwallet.coins.Coin;
import eu.farmingpool.farmingwallet.transactions.Transaction;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.utils.SharedDataManager;

import static eu.farmingpool.farmingwallet.utils.Utils.KEY_SEPARATOR;

public abstract class BlockchainClient {
    private static final String KEY_TRANSACTIONS = "transactions";
    protected final Coin coin;
    private final RequestQueue requestQueue = Volley.newRequestQueue(GlobalApplication.getAppContext());

    protected BlockchainClient(Coin coin) {
        this.coin = coin;
    }

    protected abstract void postTransaction(Transaction transaction);

    @NonNull
    protected abstract TransactionRecords fetchTransactionRecords(Account account);

    public void executeTransaction(Transaction transaction) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "",
                response -> {
                },
                error -> {
                }) {
        };

        requestQueue.add(stringRequest);
    }

    @NonNull
    public TransactionRecords fetchAndCacheTransactionRecords(Account account) {
        TransactionRecords transactionRecords = fetchTransactionRecords(account);

        cacheTransactionRecords(account, transactionRecords);

        return transactionRecords;
    }

    @NonNull
    private TransactionRecords getCachedTransactionRecords(Account account) {
        String key = getTransactionRecordsCachingKey(account);

        return SharedDataManager.getSharedTransactionRecords(key);
    }

    private void cacheTransactionRecords(Account account, TransactionRecords transactionRecords) {
        String key = getTransactionRecordsCachingKey(account);

        SharedDataManager.putTransactionRecords(key, transactionRecords);
    }

    @NonNull
    private String getTransactionRecordsCachingKey(Account account) {
        return account.getId() + KEY_SEPARATOR + coin + KEY_TRANSACTIONS;
    }
}
