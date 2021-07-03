package eu.farmingpool.farmingwallet.ui.send;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class SelectAmountFragment extends Fragment {
    private Coin coin;

    private SendViewModel sendViewModel;
    private SeekBar sbAmount;

    private EditText etAmount;
    private EditText etCurrency;
    private ChangingQuantity changingQuantity;
    private boolean propagatingAmount;

    private enum ChangingQuantity {AMOUNT, CURRENCY, SEEK_BAR}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_select_amount, container, false);

        setupViewModel();

        setupCoin(root);
        setupSeekBar(root);
        setupAmountEditText(root);
        setupMinMaxValues(root);
        setupMinHalfMaxButtons(root);

        return root;
    }

    private void setupViewModel() {
        sendViewModel = new ViewModelProvider(requireActivity()).get(SendViewModel.class);

        sendViewModel.getAmount().observe(getViewLifecycleOwner(), amount -> {
            propagatingAmount = true;

            if (changingQuantity != ChangingQuantity.SEEK_BAR)
                updateSeekBar(amount);

            if (changingQuantity != ChangingQuantity.AMOUNT)
                updateAmountEditText(amount);

            if (changingQuantity != ChangingQuantity.CURRENCY)
                updateCurrencyEditText(amount);

            propagatingAmount = false;
        });
    }

    private void updateCurrencyEditText(Double amount) {
        etCurrency.setText(String.format(getLocale(), "%.2f", amount));
    }

    private void updateAmountEditText(Double amount) {
        etAmount.setText(String.format(getLocale(), "%.2f", amount));
    }

    private void updateSeekBar(Double amount) {
        sbAmount.setProgress(getProgressFromAmount(amount));
    }

    private void setupCoin(View view) {
        coin = sendViewModel.getCoin().getValue();
        TextView tvCoin = view.findViewById(R.id.tv_fragment_select_amount_coin);
        tvCoin.setText(coin.toString());

    }

    private void setupSeekBar(View view) {
        sbAmount = view.findViewById(R.id.sb_fragment_select_amount_amount);
        sbAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (propagatingAmount)
                    return;

                changingQuantity = ChangingQuantity.SEEK_BAR;

                double amount = getAmountFromProgress(seekBar.getProgress());
                setAmount(amount);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Double amount = sendViewModel.getAmount().getValue();
        assert amount != null;
        updateSeekBar(amount);
    }

    private void setupAmountEditText(View view) {
        etAmount = view.findViewById(R.id.et_fragment_select_amount_amount_coin);
        etCurrency = view.findViewById(R.id.et_fragment_select_amount_amount_currency);

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (propagatingAmount)
                    return;

                changingQuantity = ChangingQuantity.AMOUNT;

                setAmountFromEditable(s);
            }
        });

        etCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (propagatingAmount)
                    return;

                changingQuantity = ChangingQuantity.CURRENCY;

                setAmountFromEditable(s);
            }
        });
    }

    private void setAmountFromEditable(Editable s) {
        try {
            double amount = Double.parseDouble(String.valueOf(s));
            setAmount(amount);
        } catch (Exception ignored) {
            setAmount(0.0);
        }
    }

    private void setupMinMaxValues(View view) {
        double balance = getBalance();

        TextView tvMin = view.findViewById(R.id.tv_fragment_select_amount_amount_min);
        TextView tvMax = view.findViewById(R.id.tv_fragment_select_amount_amount_max);

        assert coin != null;
        tvMin.setText(String.format(getLocale(), coin.getFormat(), 0.0));
        tvMax.setText(String.format(getLocale(), coin.getFormat(),  coin.formattedAmount(balance)));
    }

    private void setupMinHalfMaxButtons(View view) {
        TextView tvMin = view.findViewById(R.id.tv_fragment_select_amount_min);
        TextView tvHalf = view.findViewById(R.id.tv_fragment_select_amount_half);
        TextView tvMax = view.findViewById(R.id.tv_fragment_select_amount_max);

        tvMin.setOnClickListener(v -> sbAmount.setProgress(0));
        tvHalf.setOnClickListener(v -> sbAmount.setProgress(50));
        tvMax.setOnClickListener(v -> sbAmount.setProgress(100));
    }

    private double getAmountFromProgress(int progress) {
        double balance = getBalance();

        return balance * ((double) progress) / 100;
    }

    private int getProgressFromAmount(double amount) {
        double balance = getBalance();

        return (int) (100 * amount / balance);
    }

    private double getBalance() {
//        Account account = Accounts.getInstance().getCurrentAccount();
//        Wallet wallet = account.getWallet(coin);
//        return wallet.getBalance();
        return 234.56;
    }

    private void setAmount(double amount) {
        sendViewModel.setAmount(amount);
    }
}
