package eu.farmingpool.farmingwallet;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import eu.farmingpool.farmingwallet.keywords.Keywords;
import eu.farmingpool.farmingwallet.keywords.KeywordsGenerator;
import eu.farmingpool.farmingwallet.ui.wallet.creation.KeywordsCreationFragment;
import eu.farmingpool.farmingwallet.ui.wallet.creation.KeywordsCreationFragmentDirections;
import eu.farmingpool.farmingwallet.ui.wallet.creation.KeywordsViewModel;

import static eu.farmingpool.farmingwallet.utils.Utils.openActivity;

public class NewWalletActivity extends AppCompatActivity implements
        KeywordsCreationFragment.KeywordsCreationFragmentInterface {
    public static final int KEYWORDS_TO_GENERATE = 16;

    private NavController navController;
    private KeywordsViewModel keywordsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_wallet);

        keywordsViewModel = new ViewModelProvider(this).get(KeywordsViewModel.class);

        generateRandomKeywords();

        setupNavigation();
        setupCloseButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onNextPressed() {
        navController.navigate(KeywordsCreationFragmentDirections.actionKeywordsCreationFragmentToKeywordsCheckFragment());
        keywordsViewModel.generateKeywordsToCheck();
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.activity_new_wallet_nav_host_fragment);
    }

    private void setupCloseButton() {
        View close = findViewById(R.id.iv_close);

        close.setOnClickListener(v -> openSplashActivity());
    }

    private void openSplashActivity() {
        openActivity(this, SplashActivity.class, true, true, true, true);
    }

    private void generateRandomKeywords() {
        Keywords keywords = KeywordsGenerator.generate(KEYWORDS_TO_GENERATE);

        keywordsViewModel.setKeywords(keywords);
    }
}
