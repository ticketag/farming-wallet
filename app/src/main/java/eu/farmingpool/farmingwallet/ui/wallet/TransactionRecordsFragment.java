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

import java.util.Observable;
import java.util.Observer;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.transactions.TransactionRecordEventHandler;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.ui.ObservableChanges;
import eu.farmingpool.farmingwallet.wallet.Coin;
import eu.farmingpool.farmingwallet.wallet.Wallet;

public class TransactionRecordsFragment extends Fragment implements
        Observer,
        TransactionRecordsAdapter.OnClickListener, TransactionRecordEventHandler {
    private RecyclerView rvTransactionRecords;
    private TransactionRecordsAdapter.OnClickListener onClickListener;
    private Wallet wallet;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transaction_records, container, false);
        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        setTransactionRecordsAdapter();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onTransactionClicked(int i) {
        onClickListener.onTransactionClicked(i);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            onClickListener = (TransactionRecordsAdapter.OnClickListener) context;
        } catch (Exception ignored) {
        }
    }

    @Override
    public void update(Observable observable, Object observation) {
        if (observable instanceof ObservableChanges)
            onObservableCurrentAccountObservation();
    }

    private Observer transactionRecordsChanged = (o, arg) -> {

    };

    private void onObservableCurrentAccountObservation() {
        setTransactionRecordsAdapter();
    }


    private void setupRecyclerView(View view) {
        rvTransactionRecords = view.findViewById(R.id.rv_transaction_records);
        rvTransactionRecords.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        Account account = Accounts.getInstance().getCurrentAccount();
        wallet = account.getWallet(Coin.XCH);
        TransactionRecordsAdapter adapter = new TransactionRecordsAdapter(wallet.transactions, this);
        rvTransactionRecords.setAdapter(adapter);
    }

    private void setTransactionRecordsAdapter() {
//        Wallet wallet = Accounts.getInstance().getCurrentWallet();
//        TransactionRecords transactionRecords = wallet.transactions;
//
//        TransactionRecordsAdapter adapter = new TransactionRecordsAdapter(transactionRecords, this);
//        rvTransactionRecords.setAdapter(adapter);
//        rvTransactionRecords.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    @Override
    public void onTransactionRecordsLoaded(TransactionRecords records, long balance) {
        wallet.setTransactions(records);
        TransactionRecordsAdapter adapter = new TransactionRecordsAdapter(wallet.transactions, this);
        rvTransactionRecords.setAdapter(adapter);
    }
}