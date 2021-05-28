package eu.farmingpool.farmingwallet.ui.wallet.detail;

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
import eu.farmingpool.farmingwallet.transactions.ObservableTransactionRecords;

public class TransactionRecordsFragment extends Fragment implements
        Observer,
        TransactionRecordsAdapter.OnClickListener {
    private RecyclerView rvTransactionRecords;
    private TransactionRecordsAdapter.OnClickListener onClickListener;
    private final ObservableTransactionRecords transactionRecords = ObservableTransactionRecords.getInstance();

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

        transactionRecords.addObserver(this);
        setTransactionRecordsAdapter();
    }

    @Override
    public void onPause() {
        super.onPause();

        transactionRecords.deleteObserver(this);
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
        if (observable instanceof ObservableTransactionRecords)
            onObservableTransactionRecordsObservation();

    }

    private void onObservableTransactionRecordsObservation() {
        setTransactionRecordsAdapter();
    }

    private void setupRecyclerView(View view) {
        rvTransactionRecords = view.findViewById(R.id.rv_transaction_records);
    }

    private void setTransactionRecordsAdapter() {
        TransactionRecordsAdapter adapter = new TransactionRecordsAdapter(transactionRecords.getTransactions(), this);
        rvTransactionRecords.setAdapter(adapter);
        rvTransactionRecords.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}