package eu.farmingpool.farmingwallet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.sql.Timestamp;

import eu.farmingpool.farmingwallet.coins.Coin;
import eu.farmingpool.farmingwallet.transactions.TransactionRecord;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.ui.wallet.TransactionRecordsViewModel;

public class MainActivity extends AppCompatActivity {
    private TransactionRecordsViewModel transactionRecordsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation();
        setupViewModels();
    }

    private void setupNavigation() {
        getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_wallet, R.id.navigation_d_apps, R.id.navigation_pool)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void setupViewModels() {
        transactionRecordsViewModel = new ViewModelProvider(this).get(TransactionRecordsViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fetchTransactionRecords();
    }

    private void fetchTransactionRecords() {
        TransactionRecords transactionRecords = new TransactionRecords();

        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis()), 1.2));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 5), 2.1));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 9), -3.45));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 5), 0.1));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 7), -0.4));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 3), 3.7));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 2), -4.1));

        transactionRecordsViewModel.setTransactionRecords(transactionRecords);
    }

    void test() {
        KeyStore keyStore = null;
        char[] password = "password".toCharArray();

        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        assert keyStore != null;

        try (FileInputStream fis = new FileInputStream((String) null)) {
            keyStore.load(fis, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PrivateKey privateKey = (PrivateKey) keyStore.getKey("keyName", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}