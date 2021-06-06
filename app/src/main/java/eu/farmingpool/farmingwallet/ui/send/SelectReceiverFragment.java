package eu.farmingpool.farmingwallet.ui.send;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.keys.Key;
import eu.farmingpool.farmingwallet.logging.Event;
import eu.farmingpool.farmingwallet.utils.Contact;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.logging.Log.E;
import static eu.farmingpool.farmingwallet.logging.Log.logEvent;
import static eu.farmingpool.farmingwallet.logging.Tag.EVENT_ACTIVITY_SEND;

public class SelectReceiverFragment extends Fragment {
    private SendViewModel sendViewModel;
    private EditText etReceiverAddress;
    private Interface selectReceiverFragmentInterface;
    private Button btNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_select_receiver, container, false);

        setupViewModel();

        setupReceiverAddressEditText(root);
        setupQRCodeButton(root);
        setupContactButton(root);
        setupNextButton(root);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            selectReceiverFragmentInterface = (Interface) context;
        } catch (ClassCastException e) {
            logEvent(E, new Event("onAttach", e.getMessage(), EVENT_ACTIVITY_SEND));
        }
    }

    private void setupReceiverAddressEditText(View view) {
        etReceiverAddress = view.findViewById(R.id.et_fragment_select_receiver_receiver_address);
    }

    private void setupQRCodeButton(View view) {
        TextView tvQRCode = view.findViewById(R.id.tv_fragment_select_receiver_qr_code);
        tvQRCode.setOnClickListener(v -> selectReceiverFragmentInterface.onQRCodeClicked());
    }

    private void setupContactButton(View view) {
        TextView tvContact = view.findViewById(R.id.tv_fragment_select_receiver_contact);
        tvContact.setOnClickListener(v -> selectReceiverFragmentInterface.onContactClicked());
    }

    private void setupNextButton(View view) {
        btNext = view.findViewById(R.id.bt_fragment_select_receiver_next);
        btNext.setOnClickListener(v -> selectReceiverFragmentInterface.onNextClicked());

        checkActivateNextButton();
    }

    private void setupViewModel() {
        sendViewModel = new ViewModelProvider(requireActivity()).get(SendViewModel.class);

        sendViewModel.getContact().observe(getViewLifecycleOwner(), this::setReceiverAddress);
        sendViewModel.getCoin().observe(getViewLifecycleOwner(), coin -> checkActivateNextButton());
    }

    private void setReceiverAddress(Contact contact) {
        Key receiverAddress = contact.getReceivingAddress();
        etReceiverAddress.setText(receiverAddress.getCompressedValue());

        checkActivateNextButton();
    }

    private void checkActivateNextButton() {
        Contact contact = sendViewModel.getContact().getValue();
        Coin coin = sendViewModel.getCoin().getValue();

        boolean isCoinSelected = coin != null;
        boolean isReceiverSelected = false;

        if (contact != null)
            isReceiverSelected = contact.getReceivingAddress() != null;

        btNext.setEnabled(isCoinSelected && isReceiverSelected);
    }

    public interface Interface {
        void onQRCodeClicked();

        void onContactClicked();

        void onNextClicked();
    }
}
