package eu.farmingpool.farmingwallet;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.keywords.Keywords;
import eu.farmingpool.farmingwallet.keywords.KeywordsGenerator;
import eu.farmingpool.farmingwallet.ui.wallet.creation.KeywordsCheckFragmentDirections;
import eu.farmingpool.farmingwallet.ui.wallet.creation.KeywordsCreationFragment;
import eu.farmingpool.farmingwallet.ui.wallet.creation.KeywordsCreationFragmentDirections;
import eu.farmingpool.farmingwallet.ui.wallet.creation.KeywordsViewModel;
import eu.farmingpool.farmingwallet.ui.wallet.creation.WalletCreatedFragment;

import static eu.farmingpool.farmingwallet.utils.Utils.openActivity;

public class NewWalletActivity extends AppCompatActivity implements
        KeywordsCreationFragment.KeywordsCreationFragmentInterface,
        WalletCreatedFragment.WalletCreatedFragmentInterface {
    public static final int N_KEYWORDS_TO_GENERATE = 16;
    private static final int N_KEYWORDS_TO_CHECK = 6;

    private NavController navController;
    private KeywordsViewModel keywordsViewModel;
    private ImageView back;
    private ImageView close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_wallet);

        keywordsViewModel = new ViewModelProvider(this).get(KeywordsViewModel.class);
        keywordsViewModel.getAllKeywordsCorrect().observe(this, allKeywordsCorrect -> {
            if (allKeywordsCorrect) {
                navController.navigate(KeywordsCheckFragmentDirections.actionKeywordsCheckFragmentToWalletCreatedFragment());
                back.setVisibility(View.INVISIBLE);
                close.setVisibility(View.INVISIBLE);
            }
        });


        generateRandomKeywords();

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
        openSplashActivity();
    }

    @Override
    public void onNextPressed() {
        navController.navigate(KeywordsCreationFragmentDirections.actionKeywordsCreationFragmentToKeywordsCheckFragment());
        keywordsViewModel.generateKeywordsToCheck(N_KEYWORDS_TO_CHECK);
        back.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAccountNameChosen(String accountName) {
        Account account = new Account(0);
        account.setName(accountName);
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.activity_new_wallet_nav_host_fragment);
    }

    private void setupCloseButton() {
        close = findViewById(R.id.iv_activity_new_wallet_close);

        close.setOnClickListener(v -> openSplashActivity());
    }

    private void setupBackButton() {
        back = findViewById(R.id.iv_activity_new_wallet_back);

        back.setVisibility(View.INVISIBLE);
        back.setOnClickListener(v -> {
            navController.navigateUp();
            back.setVisibility(View.INVISIBLE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        });
    }

    private void openSplashActivity() {
        openActivity(this, SplashActivity.class, true, true, true, true);
    }

    private void generateRandomKeywords() {
        Keywords keywords = KeywordsGenerator.generate(N_KEYWORDS_TO_GENERATE);

        keywordsViewModel.setKeywords(keywords);
    }
}
