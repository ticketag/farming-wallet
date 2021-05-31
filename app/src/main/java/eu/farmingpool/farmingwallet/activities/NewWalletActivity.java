package eu.farmingpool.farmingwallet.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.keywords.Keywords;
import eu.farmingpool.farmingwallet.keywords.KeywordsGenerator;
import eu.farmingpool.farmingwallet.ui.account.creation.AccountCreatedFragment;
import eu.farmingpool.farmingwallet.ui.account.creation.KeywordsCheckFragmentDirections;
import eu.farmingpool.farmingwallet.ui.account.creation.KeywordsCreationFragment;
import eu.farmingpool.farmingwallet.ui.account.creation.KeywordsCreationFragmentDirections;
import eu.farmingpool.farmingwallet.ui.account.creation.KeywordsViewModel;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.openActivity;

public class NewWalletActivity extends AppCompatActivity implements
        KeywordsCreationFragment.KeywordsCreationFragmentInterface,
        AccountCreatedFragment.WalletCreatedFragmentInterface {
    public static final int N_KEYWORDS_TO_GENERATE = 16;
    private static final int N_KEYWORDS_TO_CHECK = 6;

    private NavController navController;
    private KeywordsViewModel keywordsViewModel;
    private ImageView ivBack;
    private ImageView ivClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        keywordsViewModel = new ViewModelProvider(this).get(KeywordsViewModel.class);
        setupKeywordsViewModelObserver();

        generateRandomKeywords();
        setContentView(R.layout.activity_new_wallet);

        setupNavigation();
        setupBackButton();
        setupCloseButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onNextPressed() {
        navController.navigate(KeywordsCreationFragmentDirections.actionKeywordsCreationFragmentToKeywordsCheckFragment());
        keywordsViewModel.generateKeywordsToCheck(N_KEYWORDS_TO_CHECK);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAccountNameChosen(String accountName) {
        Keywords keywords = keywordsViewModel.getKeywords().getValue();

        Accounts accounts = Accounts.getInstance();
        Account account = accounts.addAccount(keywords);
        account.setName(accountName);
        account.addCoin(Coin.XCH);
        accounts.setCurrentAccount(account.getId());

        openMainActivity();
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.activity_new_wallet_nav_host_fragment);
    }

    private void setupCloseButton() {
        ivClose = findViewById(R.id.iv_activity_new_wallet_close);

        ivClose.setOnClickListener(v -> openSplashActivity());
    }

    private void setupBackButton() {
        ivBack = findViewById(R.id.iv_activity_new_wallet_back);

        ivBack.setVisibility(View.INVISIBLE);
        ivBack.setOnClickListener(v -> {
            hideKeyboard(v);
            navController.navigateUp();
            ivBack.setVisibility(View.INVISIBLE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        });
    }

    private void openSplashActivity() {
        openActivity(this, SplashActivity.class, true, true, true, true);
    }

    private void openMainActivity() {
        openActivity(this, MainActivity.class, true, true, false, true);
    }

    private void setupKeywordsViewModelObserver() {
        keywordsViewModel.getAllKeywordsCorrect().observe(this, allKeywordsCorrect -> {
            if (allKeywordsCorrect) {
                navController.navigate(KeywordsCheckFragmentDirections.actionKeywordsCheckFragmentToWalletCreatedFragment());
                ivBack.setVisibility(View.INVISIBLE);
                ivClose.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void generateRandomKeywords() {
        Keywords keywords = KeywordsGenerator.generate(this, N_KEYWORDS_TO_GENERATE);

        keywordsViewModel.setKeywords(keywords);
    }


    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }
}
