package eu.farmingpool.farmingwallet.ui.send;

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

public class WalletsAdapter extends RecyclerView.Adapter<WalletsAdapter.WalletViewHolder> {
    private final OnClickListener onClickListener;
    private final ArrayList<Wallet> wallets;

    public WalletsAdapter(ArrayList<Wallet> wallets, OnClickListener onClickListener) {
        this.wallets = wallets;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_wallet, parent, false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        holder.setup(wallets.get(position));
        holder.setOnClickListener(v -> onClickListener.onWalletClicked(position));
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public interface OnClickListener {
        void onWalletClicked(int i);
    }

    protected static class WalletViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvCoinName;
        private final TextView tvCoinAmount;
        private final TextView tvCoinValue;
        private final TextView tvCoinChange;
        private final TextView tvCoinCorresponding;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_item_wallet_icon);
            tvCoinName = itemView.findViewById(R.id.item_wallet_coin_name);
            tvCoinAmount = itemView.findViewById(R.id.item_wallet_coin_amount);
            tvCoinValue = itemView.findViewById(R.id.item_wallet_coin_value);
            tvCoinChange = itemView.findViewById(R.id.item_wallet_coin_change);
            tvCoinCorresponding = itemView.findViewById(R.id.item_wallet_coin_corresponding);
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

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }
    }
}

