package eu.farmingpool.farmingwallet.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import eu.farmingpool.farmingwallet.R;

public class ChangeAccountDialog extends BottomSheetDialogFragment implements ChangeAccountAdapter.OnClickListener {
    private final Interface changeAccountDialogInterface;

    public ChangeAccountDialog(Interface changeAccountDialogInterface) {
        this.changeAccountDialogInterface = changeAccountDialogInterface;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_change_account, container, false);

        setupRecyclerView(root);
        setupButtons(root);

        return root;
    }

    @Override
    public void onAccountClicked(int i) {
        changeAccountDialogInterface.onAccountClicked(i);
        dismiss();
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvAccountsList = view.findViewById(R.id.rv_dialog_change_account_accounts_list);
        rvAccountsList.setAdapter(new ChangeAccountAdapter(this));
        rvAccountsList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void setupButtons(View view) {
        TextView tvAdd = view.findViewById(R.id.tv_dialog_change_account_add);
        TextView tvImport = view.findViewById(R.id.tv_dialog_change_account_import);

        tvAdd.setOnClickListener(v -> changeAccountDialogInterface.onAddAccountClicked());
        tvImport.setOnClickListener(v -> changeAccountDialogInterface.onImportAccountClicked());
    }

    public interface Interface {
        void onAccountClicked(int i);

        void onAddAccountClicked();

        void onImportAccountClicked();
    }
}
