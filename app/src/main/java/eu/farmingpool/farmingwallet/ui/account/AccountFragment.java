package eu.farmingpool.farmingwallet.ui.account;

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
import java.util.Observable;
import java.util.Observer;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.ui.ObservableChanges;
import eu.farmingpool.farmingwallet.wallet.Wallet;

public class AccountFragment extends Fragment implements WalletsAdapter.OnClickListener, Observer {
    WalletsAdapter.OnClickListener onClickListener;
    RecyclerView rvWallets;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

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
    public void onResume() {
        super.onResume();

        ObservableChanges.getInstance().addObserver(this);
        setRvWalletsAdapter();
    }

    @Override
    public void onPause() {
        super.onPause();

        ObservableChanges.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object observation) {
        if (observable instanceof ObservableChanges)
            if (observation == ObservableChanges.EntityChanged.ACCOUNT)
                setRvWalletsAdapter();
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
        rvWallets = view.findViewById(R.id.rv_fragment_wallet_wallets);
        rvWallets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void setRvWalletsAdapter() {
        Account account = Accounts.getInstance().getCurrentAccount();
        ArrayList<Wallet> wallets = account.getWallets();
        WalletsAdapter adapter = new WalletsAdapter(wallets, this);
        rvWallets.setAdapter(adapter);
    }
}