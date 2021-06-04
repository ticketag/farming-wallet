package eu.farmingpool.farmingwallet.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.application.GlobalApplication;
import eu.farmingpool.farmingwallet.ui.send.SelectContactDialog;
import eu.farmingpool.farmingwallet.ui.send.SelectReceiverFragment;
import eu.farmingpool.farmingwallet.ui.send.SendViewModel;
import eu.farmingpool.farmingwallet.utils.Contact;
import eu.farmingpool.farmingwallet.utils.DBManager;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.KEY_SERIALIZABLE_COIN;

public class SendActivity extends AppCompatActivity implements
        SelectReceiverFragment.Interface,
        SelectContactDialog.Interface {
    private NavController navController;
    private SendViewModel sendViewModel;
    private DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = GlobalApplication.getDBManager();

        setContentView(R.layout.activity_send);

        setupViewModel();
        setupAccountName();
        setupNavigation();
    }

    // SelectReceiverFragment.Interface
    @Override
    public void onQRCodeClicked() {

    }

    @Override
    public void onContactClicked() {
        Coin coin = sendViewModel.getCoin().getValue();

        if (coin == null)
            return;

        ArrayList<Contact> contacts = dbManager.getContacts(coin);

        SelectContactDialog selectContactDialog = new SelectContactDialog(coin, contacts, this);
        selectContactDialog.show(getSupportFragmentManager(), "selectContactDialog");
    }

    @Override
    public void onNextClicked() {

    }

    // SelectContactDialog.Interface
    @Override
    public void onAddContactClicked() {

    }

    @Override
    public void onContactSelected(Contact contact) {

    }

    private void setupAccountName() {
        String accountName = Accounts.getInstance().getCurrentAccount().getName();
        TextView tvAccountName = findViewById(R.id.tv_activity_send_account);
        tvAccountName.setText(accountName);
    }

    private void setupViewModel() {
        sendViewModel = new ViewModelProvider(this).get(SendViewModel.class);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Coin coin = (Coin) extras.getSerializable(KEY_SERIALIZABLE_COIN);
            sendViewModel.setCoin(coin);
        }
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.activity_send_nav_host_fragment);
    }
}
