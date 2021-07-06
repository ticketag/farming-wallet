package eu.farmingpool.farmingwallet.blockchain;


import com.android.volley.toolbox.JsonObjectRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.keys.ChiaKeysManager;
import eu.farmingpool.farmingwallet.transactions.Transaction;
import eu.farmingpool.farmingwallet.transactions.TransactionRecord;
import eu.farmingpool.farmingwallet.transactions.TransactionRecordEventHandler;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.MOCK_RECEIVING_ADDRESS;

class WalletRequestParams {
    public String[] puzzle_hashes;

    public WalletRequestParams(String[] puzzle_hashes) {
        this.puzzle_hashes = puzzle_hashes;
    }
}

public class ChiaBlockchainClient extends BlockchainClient {
    private static ChiaBlockchainClient instance;

    private ChiaBlockchainClient() {
        super(Coin.XCH);
    }

    public static ChiaBlockchainClient getInstance() {
        if (instance == null) {
            synchronized (ChiaBlockchainClient.class) {
                if (instance == null) {
                    instance = new ChiaBlockchainClient();
                }
            }
        }

        return instance;
    }

    @Override
    protected void postTransaction(Transaction transaction) {

    }

    @NotNull
    @Override
    protected TransactionRecords fetchTransactionRecords(Account account, TransactionRecordEventHandler handler) {
        String[] puzzleHashes = ChiaKeysManager.getInstance().getPuzzleHashes(account);
        TransactionRecords transactionRecords = new TransactionRecords();
        JSONObject body = new JSONObject();
        JSONArray puzzleHashesJson = new JSONArray();
        for (String puzzleHash : puzzleHashes) {
            puzzleHashesJson.put(puzzleHash);
        }
        try {
            body.put("puzzle_hashes", puzzleHashesJson);
        } catch (org.json.JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest req = new JsonObjectRequest("http://192.168.2.2:5012/api/wallet/transactions", body, response -> {
            try {
                long balance = response.getLong("balance");
                JSONArray transactions = response.getJSONArray("transactions");
                for (int i = 0; i < transactions.length(); i++ ) {
                    JSONObject txnRecord = transactions.getJSONObject(i);
                    transactionRecords.insert(new TransactionRecord(coin, new Timestamp(txnRecord.getLong("timestamp")*1000), txnRecord.getLong("amount"), MOCK_RECEIVING_ADDRESS, txnRecord.getLong("block_height")));
                }
                handler.onTransactionRecordsLoaded(transactionRecords, balance);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            //
        }, error -> {
            throw new RuntimeException(error.getMessage());
        });
        requestQueue.add(req);

        return transactionRecords;
    }
}
