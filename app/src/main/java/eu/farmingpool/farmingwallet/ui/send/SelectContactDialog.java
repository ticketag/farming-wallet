package eu.farmingpool.farmingwallet.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.utils.Contact;
import eu.farmingpool.farmingwallet.wallet.Coin;

public class SelectContactDialog extends BottomSheetDialogFragment implements SelectContactAdapter.OnClickListener {
    private final Coin coin;
    private final ArrayList<Contact> contacts;
    private final SelectContactAdapter.OnClickListener onClickListener;

    public SelectContactDialog(Coin coin, ArrayList<Contact> contacts, SelectContactAdapter.OnClickListener onClickListener) {
        this.coin = coin;
        this.contacts = contacts;
        this.onClickListener = onClickListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_select_contact, container, false);

        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onContactSelected(Contact contact) {
        onClickListener.onContactSelected(contact);
        dismiss();
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvCoinsList = view.findViewById(R.id.rv_dialog_add_wallet_coins_list);
        rvCoinsList.setAdapter(new SelectContactAdapter(coin, contacts, this));
        rvCoinsList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
