package eu.farmingpool.farmingwallet.ui.wallet.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;

public class TransactionRecordsFragment extends Fragment implements TransactionRecordsAdapter.OnClickListener {
    private RecyclerView rvTransactionRecords;
    private TransactionRecordsAdapter.OnClickListener onClickListener;

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

        TransactionRecordsViewModel transactionRecordsViewModel = new ViewModelProvider(requireActivity()).get(TransactionRecordsViewModel.class);
        transactionRecordsViewModel.getTransactionRecords().observe(getViewLifecycleOwner(), this::setTransactionRecordsAdapter);
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

    private void setupRecyclerView(View view) {
        rvTransactionRecords = view.findViewById(R.id.rv_transaction_records);
    }

    private void setTransactionRecordsAdapter(TransactionRecords transactionRecords) {
        TransactionRecordsAdapter adapter = new TransactionRecordsAdapter(transactionRecords, this);
        rvTransactionRecords.setAdapter(adapter);
        rvTransactionRecords.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}