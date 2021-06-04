package eu.farmingpool.farmingwallet.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.application.GlobalApplication;
import eu.farmingpool.farmingwallet.ui.send.SelectContactAdapter;
import eu.farmingpool.farmingwallet.ui.send.SelectContactDialog;
import eu.farmingpool.farmingwallet.ui.send.SelectReceiverFragment;
import eu.farmingpool.farmingwallet.ui.send.SendViewModel;
import eu.farmingpool.farmingwallet.utils.Contact;
import eu.farmingpool.farmingwallet.utils.DBManager;
import eu.farmingpool.farmingwallet.wallet.Coin;
import eu.farmingpool.farmingwallet.wallet.Wallet;

public class SendActivity extends AppCompatActivity implements
        SelectReceiverFragment.Interface,
        SelectContactAdapter.OnClickListener {
    private NavController navController;
    private SendViewModel sendViewModel;
    private DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = GlobalApplication.getDBManager();

        setContentView(R.layout.activity_send);
        setupViewModel();
        setupNavigation();
    }

    // SelectReceiverFragment.Interface
    @Override
    public void onQRCodeClicked() {

    }

    @Override
    public void onContactClicked() {
        Wallet wallet = sendViewModel.getWallet().getValue();

        if (wallet == null)
            return;

        ArrayList<Contact> contacts = dbManager.getContacts(wallet.coin);

        Coin coin = wallet.coin;
        SelectContactDialog selectContactDialog = new SelectContactDialog(coin, contacts, this);
        selectContactDialog.show(getSupportFragmentManager(), "selectContactDialog");
    }

    @Override
    public void onNextClicked() {

    }

    // SelectContactAdapter.OnClickListener
    @Override
    public void onContactSelected(Contact contact) {

    }

    private void setupViewModel() {
        sendViewModel = new ViewModelProvider(this).get(SendViewModel.class);
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.activity_send_nav_host_fragment);
    }
}
