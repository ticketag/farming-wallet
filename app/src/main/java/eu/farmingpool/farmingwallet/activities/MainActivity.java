package eu.farmingpool.farmingwallet.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.application.GlobalApplication;
import eu.farmingpool.farmingwallet.services.MasterService;
import eu.farmingpool.farmingwallet.ui.wallet.CoinBalancesAdapter;
import eu.farmingpool.farmingwallet.ui.wallet.detail.TransactionRecordsAdapter;
import eu.farmingpool.farmingwallet.utils.EncryptedSharedDataManager;

public class MainActivity extends AppCompatActivity implements
        TransactionRecordsAdapter.OnClickListener,
        CoinBalancesAdapter.OnClickListener {
    private ArrayList<String> bottomNavFragments;

    private NavController navController;
    private BottomNavigationView navView;
    private Animation animShow;
    private Animation animHide;
    private MasterService masterService;
    private Account currentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setupMasterService();
        setupAnimations();
        setupFragments();
        setupNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        currentAccount = Accounts.getInstance().getCurrentAccount();

        fetchTransactionRecords();
    }

    @Override
    public void onCoinBalanceClicked(int i) {
        navController.navigate(R.id.walletDetailFragment);
    }

    @Override
    public void onTransactionClicked(int i) {
        navController.navigate(R.id.transactionRecordDialog);
    }

    private void setupMasterService() {
        masterService = GlobalApplication.getMasterService();
    }

    private void setupAnimations() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show_from_bottom);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide_to_botom);
    }

    private void setupFragments() {
        bottomNavFragments = new ArrayList<>(Arrays.asList(
                getString(R.string.navigation_title_wallet),
                getString(R.string.navigation_title_dapps),
                getString(R.string.navigation_title_pool)
        ));
    }

    private void setupNavigation() {
        navView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String label = String.valueOf(destination.getLabel());

            if (bottomNavFragments.contains(label))
                showBottomNavView();
            else
                hideBottomNavView();
        });

        NavigationUI.setupWithNavController(navView, navController);
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
        if (currentAccount != null)
            masterService.fetchTransactionRecords(currentAccount);
    }

    private void switchCurrentAccount(int accountId) {
        if (currentAccount.getId() == accountId)
            return;

        Accounts accounts = EncryptedSharedDataManager.getAccounts();
        currentAccount = accounts.getAccount(accountId);
    }

//    void test() {
//        KeyStore keyStore = null;
//        char[] password = "password".toCharArray();
//
//        try {
//            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//
//        assert keyStore != null;
//
//        try (FileInputStream fis = new FileInputStream((String) null)) {
//            keyStore.load(fis, password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            PrivateKey privateKey = (PrivateKey) keyStore.getKey("keyName", password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}