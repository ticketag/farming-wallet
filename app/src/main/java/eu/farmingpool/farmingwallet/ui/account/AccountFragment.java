package eu.farmingpool.farmingwallet.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import static eu.farmingpool.farmingwallet.ui.ObservableChanges.EntityChanged.ACCOUNT;
import static eu.farmingpool.farmingwallet.ui.ObservableChanges.EntityChanged.ACCOUNTS;
import static eu.farmingpool.farmingwallet.ui.ObservableChanges.EntityChanged.CURRENT_ACCOUNT;

public class AccountFragment extends Fragment implements WalletsAdapter.OnClickListener, Observer {
    WalletsAdapter.OnClickListener onClickListener;
    RecyclerView rvWalletsPercentage;
    RecyclerView rvWallets;
    TextView tvAccountName;
    Interface accountFragmentInterface;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        setupAccountName(root);
        setupWalletsRecyclerView(root);
        setupWalletsPercentageRecyclerView(root);
        setupSendReceiveButtons(root);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            onClickListener = (WalletsAdapter.OnClickListener) context;
            accountFragmentInterface = (Interface) context;
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ObservableChanges.getInstance().addObserver(this);
        setRvAdapters();
    }

    @Override
    public void onPause() {
        super.onPause();

        ObservableChanges.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object observation) {
        if (observable instanceof ObservableChanges) {
            if (observation == ACCOUNT || observation == ACCOUNTS)
                setRvAdapters();
            if (observation == CURRENT_ACCOUNT)
                refreshAccountName();
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

    private void setupAccountName(View view) {
        tvAccountName = view.findViewById(R.id.tv_fragment_account_account_name);
        tvAccountName.setOnClickListener(v -> accountFragmentInterface.onAccountNameClicked());
        refreshAccountName();
    }

    private void setupWalletsRecyclerView(View view) {
        rvWallets = view.findViewById(R.id.rv_fragment_account_wallets);
        rvWallets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvWallets.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                accountFragmentInterface.onWalletsScroll(dy);
            }
        });
    }

    private void setupWalletsPercentageRecyclerView(View view) {
        rvWalletsPercentage = view.findViewById(R.id.rv_fragment_account_wallets_percentage);
        rvWalletsPercentage.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void setupSendReceiveButtons(View view) {
        Button btSend = view.findViewById(R.id.bt_send);
        btSend.setOnClickListener(v -> accountFragmentInterface.onSendClicked());

        Button btReceive = view.findViewById(R.id.bt_receive);
        btReceive.setOnClickListener(v -> accountFragmentInterface.onReceiveClicked());
    }

    private void setRvAdapters() {
        setRvWalletsAdapter();
        setRvWalletsPercentageAdapter();
    }

    private void setRvWalletsAdapter() {
        Account account = Accounts.getInstance().getCurrentAccount();
        ArrayList<Wallet> wallets = account.getWallets();
        WalletsAdapter adapter = new WalletsAdapter(wallets, this);
        rvWallets.setAdapter(adapter);
    }

    private void setRvWalletsPercentageAdapter() {
        Account account = Accounts.getInstance().getCurrentAccount();
        ArrayList<Wallet> wallets = account.getWallets();
        WalletsPercentageAdapter adapter = new WalletsPercentageAdapter(wallets);
        rvWalletsPercentage.setAdapter(adapter);
    }

    private void refreshAccountName() {
        String accountName = Accounts.getInstance().getCurrentAccount().getName();
        tvAccountName.setText(accountName);
    }

    public interface Interface {
        void onAccountNameClicked();

        void onWalletsScroll(int dy);

        void onSendClicked();

        void onReceiveClicked();
    }
}