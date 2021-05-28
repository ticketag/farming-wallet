package eu.farmingpool.farmingwallet.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.blockchain.BlockchainClient;
import eu.farmingpool.farmingwallet.blockchain.BlockchainClientFactory;
import eu.farmingpool.farmingwallet.keys.Key;
import eu.farmingpool.farmingwallet.keys.KeysManager;
import eu.farmingpool.farmingwallet.keys.KeysManagerFactory;
import eu.farmingpool.farmingwallet.transactions.ObservableTransactionRecords;
import eu.farmingpool.farmingwallet.transactions.Transaction;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.utils.SingleTimerTask;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.MILLISECONDS_IN_SECOND;

public class MasterService extends Service {
    private static final long TRANSACTION_UPDATE_PERIOD = 60 * MILLISECONDS_IN_SECOND;

    private static final ObservableTransactionRecords transactionRecords = ObservableTransactionRecords.getInstance();
    private final IBinder binder = new LocalBinder();
    private final SingleTimerTask transactionsUpdateTimerTask = new SingleTimerTask(this::fetchTransactionRecords, 0, TRANSACTION_UPDATE_PERIOD);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void fetchTransactionRecords() {
    }

    public void fetchTransactionRecords(Account account) {
        for (Coin coin : account.getCoins())
            fetchTransactionRecords(account, coin);
    }

    public void fetchTransactionRecords(Account account, Coin coin) {
        BlockchainClient blockchainClient = BlockchainClientFactory.get(coin);
        TransactionRecords transactions = blockchainClient.fetchAndCacheTransactionRecords(account);

        transactionRecords.insertTransactions(transactions);
    }


    public void executeTransaction(Account account, Coin coin, double amount, Key receivingAddress) {
        if (!account.hasCoin(coin))
            throw new IllegalStateException("Unexpected value: " + coin);

        KeysManager keysManager = KeysManagerFactory.get(coin);
        BlockchainClient blockchainClient = BlockchainClientFactory.get(coin);

        Transaction transaction = keysManager.generateTransaction(account, amount, receivingAddress);
        blockchainClient.executeTransaction(transaction);
    }

    public class LocalBinder extends Binder {
        public MasterService getServiceInstance() {
            return MasterService.this;
        }
    }
}
