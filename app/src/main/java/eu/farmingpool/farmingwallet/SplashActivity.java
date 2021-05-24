package eu.farmingpool.farmingwallet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import eu.farmingpool.farmingwallet.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    private static final int PROCEED_DELAY_MILLIS = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::proceed, PROCEED_DELAY_MILLIS);
    }

    private void proceed() {
        if (existsAtLeasOneAccount())
            openActivity(MainActivity.class);
        else
            showButtons();
    }

    private void openActivity(Class<?> className) {
        Utils.openActivity(this, className, true, true, false, true);
    }

    private void showButtons() {
        AppCompatButton btCreateNewWallet = findViewById(R.id.bt_create_new_wallet);
        AppCompatButton btImportExistingWallet = findViewById(R.id.bt_import_existing_wallet);

        btCreateNewWallet.setVisibility(View.VISIBLE);
        btImportExistingWallet.setVisibility(View.VISIBLE);

        btCreateNewWallet.setOnClickListener(v -> openActivity(NewWalletActivity.class));
        btImportExistingWallet.setOnClickListener(v -> openActivity(ImportWalletActivity.class));
    }

    private boolean existsAtLeasOneAccount() {
        return false;
    }
}
