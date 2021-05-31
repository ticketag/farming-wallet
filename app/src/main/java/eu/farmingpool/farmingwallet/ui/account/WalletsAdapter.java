package eu.farmingpool.farmingwallet.ui.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.wallet.Coin;
import eu.farmingpool.farmingwallet.wallet.Wallet;

import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class WalletsAdapter extends RecyclerView.Adapter<WalletsAdapter.BaseViewHolder> {
    private static final int TYPE_ADD_WALLET = 0;
    private final OnClickListener onClickListener;
    private static final int TYPE_WALLET = 1;
    private final ArrayList<Wallet> wallets;

    public WalletsAdapter(ArrayList<Wallet> wallets, OnClickListener onClickListener) {
        this.wallets = wallets;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_WALLET) {
            View view = inflater.inflate(R.layout.item_wallet, parent, false);
            return new WalletViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_wallet_add, parent, false);
            return new AddWalletViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (position == wallets.size())
            holder.setOnClickListener(v -> onClickListener.onAddWalletClicked());
        else {
            ((WalletViewHolder) holder).setup(wallets.get(position));
            holder.setOnClickListener(v -> onClickListener.onWalletClicked(position));
        }
    }

    @Override
    public int getItemCount() {
        return wallets.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == wallets.size())
            return TYPE_ADD_WALLET;

        return TYPE_WALLET;
    }

    public interface OnClickListener {
        void onWalletClicked(int i);

        void onAddWalletClicked();
    }

    protected static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }
    }

    protected static class WalletViewHolder extends BaseViewHolder {
        private final ImageView ivIcon;
        private final TextView tvCoinName;
        private final TextView tvCoinAmount;
        private final TextView tvCoinValue;
        private final TextView tvCoinChange;
        private final TextView tvCoinCorresponding;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_iw_icon);
            tvCoinName = itemView.findViewById(R.id.iw_coin_name);
            tvCoinAmount = itemView.findViewById(R.id.iw_coin_amount);
            tvCoinValue = itemView.findViewById(R.id.iw_coin_value);
            tvCoinChange = itemView.findViewById(R.id.iw_coin_change);
            tvCoinCorresponding = itemView.findViewById(R.id.iw_coin_corresponding);
        }

        public void setup(Wallet wallet) {
            Coin coin = wallet.coin;
            double amount = wallet.getBalance();

            ivIcon.setImageResource(coin.getIconResId());
            tvCoinName.setText(coin.getCoinName());
            tvCoinAmount.setText(String.format(getLocale(), coin.getFormat(), amount));
            tvCoinValue.setText("0.0 $");
            tvCoinChange.setText("+32.1%");
            tvCoinCorresponding.setText("0.0 $");
        }
    }

    protected static class AddWalletViewHolder extends BaseViewHolder {
        public AddWalletViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

