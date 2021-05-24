package eu.farmingpool.farmingwallet;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.keywords.KeywordsGenerator;
import eu.farmingpool.farmingwallet.ui.wallet.creation.KeywordsViewModel;

import static eu.farmingpool.farmingwallet.utils.Utils.openActivity;

public class NewWalletActivity extends AppCompatActivity {
    public static final int KEYWORDS_TO_GENERATE = 16;
    private NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_wallet);

        setupNavigation();
        setupCloseButton();
        generateRandomKeywords();
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
        KeywordsViewModel keywordsViewModel = new ViewModelProvider(this).get(KeywordsViewModel.class);
        ArrayList<String> keywords = KeywordsGenerator.generate(KEYWORDS_TO_GENERATE);

        keywordsViewModel.setKeywords(keywords);
    }
}
