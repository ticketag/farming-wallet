package eu.farmingpool.farmingwallet.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import eu.farmingpool.farmingwallet.R;

public class ChangeAccountDialog extends BottomSheetDialogFragment implements ChangeAccountAdapter.OnClickListener {
    private final ChangeAccountAdapter.OnClickListener onClickListener;

    public ChangeAccountDialog(ChangeAccountAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_change_account, container, false);

        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onAccountClicked(int i) {
        onClickListener.onAccountClicked(i);
        dismiss();
    }

    @Override
    public void onAddAccountClicked() {
        onClickListener.onAddAccountClicked();
        dismiss();
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvAccountsList = view.findViewById(R.id.rv_dialog_change_account_accounts_list);
        rvAccountsList.setAdapter(new ChangeAccountAdapter(this));
        rvAccountsList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
