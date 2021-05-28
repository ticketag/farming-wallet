package eu.farmingpool.farmingwallet.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.blockchain.BlockchainClient;
import eu.farmingpool.farmingwallet.blockchain.BlockchainClientFactory;
import eu.farmingpool.farmingwallet.coins.Coin;
import eu.farmingpool.farmingwallet.keys.Key;
import eu.farmingpool.farmingwallet.keys.KeysManager;
import eu.farmingpool.farmingwallet.keys.KeysManagerFactory;

public class MasterService extends Service {
    private final IBinder binder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void executeTransaction(Account account, Coin coin, double amount, Key receivingAddress) {
        if (!account.hasCoin(coin))
            throw new IllegalStateException("Unexpected value: " + coin);

        KeysManager keysManager = KeysManagerFactory.get(coin);
        BlockchainClient blockchainClient = BlockchainClientFactory.get(coin);

        String transaction = keysManager.generateTransaction(account, amount, receivingAddress);
        blockchainClient.executeTransaction(transaction);
    }

    public class LocalBinder extends Binder {
        public MasterService getServiceInstance() {
            return MasterService.this;
        }
    }
}
