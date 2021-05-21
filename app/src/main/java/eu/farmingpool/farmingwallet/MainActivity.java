package eu.farmingpool.farmingwallet;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
import java.util.ArrayList;
import java.util.Arrays;

import eu.farmingpool.farmingwallet.coins.Coin;
import eu.farmingpool.farmingwallet.transactions.TransactionRecord;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.ui.wallet.CoinBalancesAdapter;
import eu.farmingpool.farmingwallet.ui.wallet.TransactionRecordsAdapter;
import eu.farmingpool.farmingwallet.ui.wallet.TransactionRecordsViewModel;

public class MainActivity extends AppCompatActivity implements
        TransactionRecordsAdapter.OnClickListener,
        CoinBalancesAdapter.OnClickListener {
    private TransactionRecordsViewModel transactionRecordsViewModel;

    protected ArrayList<String> fullScreenFragments;
    protected ArrayList<String> bottomNavFragments;
    private NavController navController;
    private BottomNavigationView navView;
    private Animation animShow;
    private Animation animHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupAnimations();
        setupFragments();
        setupNavigation();
        setupViewModels();
    }

    @Override
    protected void onResume() {
        super.onResume();

        fetchTransactionRecords();
    }

    @Override
    public void onCoinBalanceClicked(int i) {
        navController.navigate(R.id.navigation_wallet_detail);
    }

    @Override
    public void onTransactionClicked(int i) {
        navController.navigate(R.id.navigation_transaction_record_dialog);
    }

    private void setupAnimations() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);
    }

    private void setupFragments() {
        fullScreenFragments = new ArrayList<>(Arrays.asList(
                getString(R.string.navigation_title_wallet_detail)
        ));

        bottomNavFragments = new ArrayList<>(Arrays.asList(
                getString(R.string.navigation_title_wallet),
                getString(R.string.navigation_title_dapps),
                getString(R.string.navigation_title_pool)
        ));
    }

    private void setupNavigation() {
        getSupportActionBar().hide();
        navView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String label = String.valueOf(destination.getLabel());

            if (bottomNavFragments.contains(label))
                showBottomNavView();
            else
                hideBottomNavView();
        });

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_wallet, R.id.navigation_d_apps, R.id.navigation_pool)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void setupViewModels() {
        transactionRecordsViewModel = new ViewModelProvider(this).get(TransactionRecordsViewModel.class);
    }

    private void hideBottomNavView() {
        if (navView.getVisibility() != View.GONE) {
            navView.startAnimation(animHide);
            navView.setVisibility(View.GONE);
        }
    }

    private void showBottomNavView() {
        if (navView.getVisibility() != View.VISIBLE) {
            navView.setVisibility(View.VISIBLE);
            navView.startAnimation(animShow);
        }
    }

    private void fetchTransactionRecords() {
        TransactionRecords transactionRecords = new TransactionRecords();

        String testSender = "xch1wh88x8m47dqkw5wuselqs4t0un5ns3k6z5cf92fpju27jwnpx3fsx2lsha";

        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis()), 1.2, testSender));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 5), 2.1, testSender));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 9), -3.45, testSender));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 5), 0.1, testSender));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 7), -0.4, testSender));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 3), 3.7, testSender));
        transactionRecords.insert(new TransactionRecord(Coin.XCH, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 2), -4.1, testSender));

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