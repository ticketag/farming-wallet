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
import eu.farmingpool.farmingwallet.wallet.Coin;
import eu.farmingpool.farmingwallet.wallet.Wallet;

public class SelectWalletDialog extends BottomSheetDialogFragment implements WalletsAdapter.OnClickListener {
    private final ArrayList<Wallet> wallets;
    private final Interface selectWalletDialogInterface;

    public SelectWalletDialog(ArrayList<Wallet> wallets, Interface selectWalletDialogInterface) {
        this.wallets = wallets;
        this.selectWalletDialogInterface = selectWalletDialogInterface;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_select_wallet, container, false);

        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onWalletClicked(int i) {
        Coin coin = wallets.get(i).coin;
        selectWalletDialogInterface.onWalletClicked(coin);
        dismiss();
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvCoinsList = view.findViewById(R.id.rv_dialog_select_wallet_coins_list);
        rvCoinsList.setAdapter(new WalletsAdapter(wallets, this));
        rvCoinsList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    public interface Interface {
        void onWalletClicked(Coin coin);
    }
}
