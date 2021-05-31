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
import eu.farmingpool.farmingwallet.ui.account.AccountFragment;
import eu.farmingpool.farmingwallet.ui.account.AddWalletAdapter;
import eu.farmingpool.farmingwallet.ui.account.AddWalletDialog;
import eu.farmingpool.farmingwallet.ui.account.ChangeAccountDialog;
import eu.farmingpool.farmingwallet.ui.account.WalletsAdapter;
import eu.farmingpool.farmingwallet.ui.wallet.TransactionRecordsAdapter;
import eu.farmingpool.farmingwallet.utils.Utils;
import eu.farmingpool.farmingwallet.wallet.Coin;

public class MainActivity extends AppCompatActivity implements
        TransactionRecordsAdapter.OnClickListener,
        AccountFragment.Interface,
        ChangeAccountDialog.Interface,
        WalletsAdapter.OnClickListener,
        AddWalletAdapter.OnClickListener {
    private ArrayList<String> bottomNavFragments;

    private NavController navController;
    private BottomNavigationView navView;
    private Animation animShow;
    private Animation animHide;
    private MasterService masterService;

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

        fetchTransactionRecords();
    }

    // AccountFragment.Interface
    @Override
    public void onAccountNameClicked() {
        ChangeAccountDialog changeAccountDialog = new ChangeAccountDialog(this);
        changeAccountDialog.show(getSupportFragmentManager(), "changeAccountDialog");
    }

    // ChangeAccountAdapter.OnClickListener
    @Override
    public void onAccountClicked(int i) {
        setCurrentAccount(i);
    }

    @Override
    public void onAddAccountClicked() {
        openNewAccountActivity();
    }

    @Override
    public void onImportAccountClicked() {

    }

    // AddWalletAdapter.OnClickListener
    @Override
    public void onWalletClicked(int i) {
        Account currentAccount = Accounts.getInstance().getCurrentAccount();
        Coin coin = currentAccount.getCoins().get(i);

        Bundle args = new Bundle();
        args.putSerializable("coin", coin);

        navController.navigate(R.id.walletDetailFragment, args);
    }

    @Override
    public void onAddWalletClicked() {
        Account currentAccount = Accounts.getInstance().getCurrentAccount();
        ArrayList<Coin> coins = Coin.getAvailableCoins(currentAccount);
        AddWalletDialog addWalletDialog = new AddWalletDialog(coins, this);
        addWalletDialog.show(getSupportFragmentManager(), "addCoinDialog");
    }

    // WalletsAdapter.OnClickListener
    @Override
    public void onWalletToAddSelected(Coin coin) {
        Account currentAccount = Accounts.getInstance().getCurrentAccount();
        currentAccount.addCoin(coin);
    }


    // TransactionRecordsAdapter.OnClickListener
    @Override
    public void onTransactionClicked(int i) {
        navController.navigate(R.id.transactionRecordDialog);
    }
    // AccountFragment.Interface

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
                getString(R.string.navigation_title_swap),
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

    private void openNewAccountActivity() {
        Utils.openActivity(this, NewAccountActivity.class, true, true, false, true);
    }

    private void fetchTransactionRecords() {
        Account currentAccount = Accounts.getInstance().getCurrentAccount();

        if (currentAccount != null)
            masterService.fetchTransactionRecords(currentAccount);
    }

    private void setCurrentAccount(int accountId) {
        Account currentAccount = Accounts.getInstance().getCurrentAccount();

        if (currentAccount.getId() == accountId)
            return;

        Accounts.getInstance().setCurrentAccount(accountId);
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