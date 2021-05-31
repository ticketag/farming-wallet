package eu.farmingpool.farmingwallet.ui.account;

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
import eu.farmingpool.farmingwallet.wallet.Coin;

public class AddWalletDialog extends BottomSheetDialogFragment implements AddWalletAdapter.OnClickListener {
    private final ArrayList<Coin> coins;
    private final AddWalletAdapter.OnClickListener onClickListener;

    public AddWalletDialog(ArrayList<Coin> coins, AddWalletAdapter.OnClickListener onClickListener) {
        this.coins = coins;
        this.onClickListener = onClickListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_add_coin, container, false);

        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onCoinToAddSelected(Coin coin) {
        onClickListener.onCoinToAddSelected(coin);
        dismiss();
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvCoinsList = view.findViewById(R.id.rv_iac_coins_list);
        rvCoinsList.setAdapter(new AddWalletAdapter(coins, this));
        rvCoinsList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
