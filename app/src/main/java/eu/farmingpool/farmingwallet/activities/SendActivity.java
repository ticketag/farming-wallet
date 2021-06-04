package eu.farmingpool.farmingwallet.activities;

import android.Manifest;
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
import eu.farmingpool.farmingwallet.keys.Key;
import eu.farmingpool.farmingwallet.permissions.PermissionsHandler;
import eu.farmingpool.farmingwallet.ui.send.ScanReceiverQRCodeDialog;
import eu.farmingpool.farmingwallet.ui.send.SelectContactDialog;
import eu.farmingpool.farmingwallet.ui.send.SelectReceiverFragment;
import eu.farmingpool.farmingwallet.ui.send.SendViewModel;
import eu.farmingpool.farmingwallet.utils.Contact;
import eu.farmingpool.farmingwallet.utils.DBManager;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.KEY_SERIALIZABLE_COIN;
import static eu.farmingpool.farmingwallet.utils.Utils.MOCK_RECEIVING_ADDRESS;

public class SendActivity extends AppCompatActivity implements
        SelectReceiverFragment.Interface,
        SelectContactDialog.Interface,
        ScanReceiverQRCodeDialog.Interface,
        PermissionsHandler.Interface {
    private NavController navController;
    private SendViewModel sendViewModel;
    private DBManager dbManager;
    private final PermissionsHandler permissionsHandler = new PermissionsHandler(this, this);

    private static final String[] mandatoryPermissions = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = GlobalApplication.getDBManager();

        setContentView(R.layout.activity_send);

        setupViewModel();
        setupAccountName();
        setupNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        permissionsHandler.check(mandatoryPermissions, null);
    }

    // SelectReceiverFragment.Interface
    @Override
    public void onQRCodeClicked() {
        ScanReceiverQRCodeDialog scanReceiverQRCodeDialog = new ScanReceiverQRCodeDialog(this);
        scanReceiverQRCodeDialog.show(getSupportFragmentManager(), "scanReceiverQRCodeDialog");
    }

    @Override
    public void onContactClicked() {
        Coin coin = sendViewModel.getCoin().getValue();

        if (coin == null)
            return;

        ArrayList<Contact> contacts = dbManager.getContacts(coin);
        Contact fakeContact = new Contact();
        fakeContact.setName("Gino");
        fakeContact.setSurname("Pino");
        fakeContact.setReceivingAddress(Coin.XCH, MOCK_RECEIVING_ADDRESS);
        contacts.add(fakeContact);

        SelectContactDialog selectContactDialog = new SelectContactDialog(coin, contacts, this);
        selectContactDialog.show(getSupportFragmentManager(), "selectContactDialog");
    }

    @Override
    public void onNextClicked() {

    }


    // ScanReceiverQRCodeDialog.Interface
    @Override
    public void onQrCodeScanned(Key key) {

    }

    // SelectContactDialog.Interface
    @Override
    public void onAddContactClicked() {

    }

    @Override
    public void onContactSelected(Contact contact) {
        sendViewModel.setContact(contact);
    }

    // PermissionsHandler.Interface
    @Override
    public void onMandatoryPermissionsGranted() {

    }

    @Override
    public void onMandatoryPermissionsDenied() {

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
