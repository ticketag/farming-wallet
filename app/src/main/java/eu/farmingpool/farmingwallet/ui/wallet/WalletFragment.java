package eu.farmingpool.farmingwallet.ui.wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.wallet.Wallet;

public class WalletFragment extends Fragment implements WalletsAdapter.OnClickListener {
    WalletsAdapter.OnClickListener onClickListener;
    RecyclerView rvCoins;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            onClickListener = (WalletsAdapter.OnClickListener) context;
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onWalletClicked(int i) {
        onClickListener.onWalletClicked(i);
    }

    @Override
    public void onAddWalletClicked() {
        onClickListener.onAddWalletClicked();
    }

    private void setupRecyclerView(View view) {
        Account account = Accounts.getInstance().getCurrentAccount();
        ArrayList<Wallet> wallets = account.getWallets();

        WalletsAdapter adapter = new WalletsAdapter(wallets, this);

        rvCoins = view.findViewById(R.id.rv_fragment_wallet_coins);
        rvCoins.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvCoins.setAdapter(adapter);
    }
}