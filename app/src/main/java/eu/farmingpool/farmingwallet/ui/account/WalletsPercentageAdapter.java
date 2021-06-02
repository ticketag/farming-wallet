package eu.farmingpool.farmingwallet.ui.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.wallet.Coin;
import eu.farmingpool.farmingwallet.wallet.Wallet;

import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class WalletsPercentageAdapter extends RecyclerView.Adapter<WalletsPercentageAdapter.WalletsPercentageViewHolder> {
    private final ArrayList<Wallet> wallets;
    private double totalBalance;

    public WalletsPercentageAdapter(ArrayList<Wallet> wallets) {
        this.wallets = wallets;
        this.totalBalance = 0;

        for (Wallet wallet : wallets)
            this.totalBalance += wallet.getBalance();
    }

    @NonNull
    @Override
    public WalletsPercentageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_wallet_balance_percentage, parent, false);

        return new WalletsPercentageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletsPercentageViewHolder holder, int position) {
        Wallet wallet = wallets.get(position);
        holder.setup(wallet);
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    protected class WalletsPercentageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivDot;
        private final TextView tvCoin;
        private final TextView tvPercentage;

        public WalletsPercentageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDot = itemView.findViewById(R.id.tv_item_wallet_balance_percentage_dot);
            tvCoin = itemView.findViewById(R.id.tv_item_wallet_balance_percentage_coin);
            tvPercentage = itemView.findViewById(R.id.tv_item_wallet_balance_percentage_percentage);
        }

        public void setup(Wallet wallet) {
            Coin coin = wallet.coin;

            tvCoin.setText(coin.toString());
            setupColor(coin);
            setupPercentage(wallet);
        }

        private void setupPercentage(Wallet wallet) {
            String percentage;

            if (totalBalance > 0)
                percentage = String.format(getLocale(), "%d %%", (int) (100 * wallet.getBalance() / totalBalance));
            else
                percentage = "N/A";

            tvPercentage.setText(percentage);
        }

        private void setupColor(Coin coin) {
            Context context = itemView.getContext();
            @ColorRes int colorResId = coin.getColorResId();
            int color = context.getColor(colorResId);

            ivDot.setColorFilter(color);
            tvCoin.setTextColor(color);
            tvPercentage.setTextColor(color);
        }
    }
}

