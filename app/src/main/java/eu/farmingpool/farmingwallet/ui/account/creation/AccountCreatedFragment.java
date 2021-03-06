package eu.farmingpool.farmingwallet.ui.account.creation;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Accounts;

import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class AccountCreatedFragment extends Fragment {
    private AppCompatButton btDone;
    private AppCompatEditText etAccountName;
    private WalletCreatedFragmentInterface walletCreatedFragmentInterface;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account_created, container, false);

        setupDoneButton(root);
        setupAccountNameEditText(root);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            walletCreatedFragmentInterface = (WalletCreatedFragmentInterface) context;
        } catch (Exception ignored) {
        }
    }

    private void setupDoneButton(View view) {
        btDone = view.findViewById(R.id.bt_fragment_account_created_done);
        btDone.setOnClickListener(v -> createAccountAndProceed());
    }

    private void setupAccountNameEditText(View view) {
        String accountName = getNextAccountName();

        etAccountName = view.findViewById(R.id.et_fragment_account_created_account_name);
        etAccountName.setText(accountName);
        etAccountName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkAccountNameValidity(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @NotNull
    private String getNextAccountName() {
        int nAccount = Accounts.getInstance().getCount() + 1;
        String placeholder = getString(R.string.fragment_account_created_account);
        return String.format(getLocale(), placeholder, nAccount);
    }

    private void checkAccountNameValidity(String accountName) {
        btDone.setEnabled(isValidAccountName(accountName));
    }

    private boolean isValidAccountName(String accountName) {
        if (accountName.length() == 0)
            return false;

        return !Accounts.getInstance().getNames().contains(accountName);
    }

    private void createAccountAndProceed() {
        Editable editable = etAccountName.getText();

        assert editable != null;

        String accountName = editable.toString();

        walletCreatedFragmentInterface.onAccountNameChosen(accountName);
    }

    public interface WalletCreatedFragmentInterface {
        void onAccountNameChosen(String accountName);
    }
}
