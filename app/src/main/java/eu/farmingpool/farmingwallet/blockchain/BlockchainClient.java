package eu.farmingpool.farmingwallet.blockchain;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import eu.farmingpool.farmingwallet.GlobalApplication;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.coins.Coin;
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

    protected abstract void postTransaction(String transaction);

    protected abstract TransactionRecords fetchTransactions(Account account);

    public void executeTransaction(String transaction) {
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

    public TransactionRecords getTransactions(Account account) {
        TransactionRecords transactionRecords = fetchTransactions(account);

        cacheTransactions(account, transactionRecords);

        return transactionRecords;
    }

    private TransactionRecords getCachedTransactions(Account account) {
        String key = getTransactionsCachingKey(account);

        return SharedDataManager.getSharedTransactionRecords(key);
    }

    private void cacheTransactions(Account account, TransactionRecords transactionRecords) {
        String key = getTransactionsCachingKey(account);

        SharedDataManager.putSharedTransactionRecords(key, transactionRecords);
    }

    private String getTransactionsCachingKey(Account account) {
        return account.getId() + KEY_SEPARATOR + coin + KEY_TRANSACTIONS;
    }
}
