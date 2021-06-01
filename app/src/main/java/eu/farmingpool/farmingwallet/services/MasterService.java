package eu.farmingpool.farmingwallet.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.blockchain.BlockchainClient;
import eu.farmingpool.farmingwallet.blockchain.BlockchainClientFactory;
import eu.farmingpool.farmingwallet.keys.Key;
import eu.farmingpool.farmingwallet.keys.KeysManager;
import eu.farmingpool.farmingwallet.keys.KeysManagerFactory;
import eu.farmingpool.farmingwallet.transactions.Transaction;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.utils.SingleTimerTask;
import eu.farmingpool.farmingwallet.wallet.Coin;
import eu.farmingpool.farmingwallet.wallet.Wallet;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;
import static eu.farmingpool.farmingwallet.utils.Utils.MILLISECONDS_IN_SECOND;

public class MasterService extends Service {
    private static final long TRANSACTION_UPDATE_PERIOD = 60 * MILLISECONDS_IN_SECOND;
    private static final int ID_SERVICE = 101;

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

        startForeground(ID_SERVICE, getServiceNotification(null));
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
        Wallet wallet = account.getWallet(coin);

        wallet.insertTransactions(transactions);
    }


    public void executeTransaction(Account account, Coin coin, double amount, Key receivingAddress) {
        if (!account.hasCoin(coin))
            throw new IllegalStateException("Unexpected value: " + coin);

        KeysManager keysManager = KeysManagerFactory.get(coin);
        BlockchainClient blockchainClient = BlockchainClientFactory.get(coin);

        Transaction transaction = keysManager.generateTransaction(account, amount, receivingAddress);
        blockchainClient.executeTransaction(transaction);
    }

    private Notification getServiceNotification(String text) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);

        return notificationBuilder.setOngoing(true)
                .setPriority(PRIORITY_MIN)
                .setContentTitle("")
                .setContentText("")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text == null ? "" : text))
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager) {
        String channelId = "Motus_MasterService_channel";
        String channelName = "Motus Smart Background Service";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        // omitted the LED color
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }

    public class LocalBinder extends Binder {
        public MasterService getServiceInstance() {
            return MasterService.this;
        }
    }
}
