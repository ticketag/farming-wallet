package eu.farmingpool.farmingwallet.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.utils.Utils;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (existsAtLeasOneAccount())
            openActivity(MainActivity.class);
        else
            showButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void openActivity(Class<?> className) {
        Utils.openActivity(this, className, true, true, false, true);
    }

    private void showButtons() {
        AppCompatButton btCreateNewWallet = findViewById(R.id.bt_create_new_wallet);
        AppCompatButton btImportExistingWallet = findViewById(R.id.bt_import_existing_wallet);

        btCreateNewWallet.setVisibility(View.VISIBLE);
        btImportExistingWallet.setVisibility(View.VISIBLE);

        btCreateNewWallet.setOnClickListener(v -> openActivity(NewAccountActivity.class));
        btImportExistingWallet.setOnClickListener(v -> openActivity(ImportAccountActivity.class));
    }

    private boolean existsAtLeasOneAccount() {
        return Accounts.getInstance().getCount() > 0;
    }
}
