package eu.farmingpool.farmingwallet.ui.account;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.wallet.Coin;

public class AddWalletAdapter extends Adapter<AddWalletAdapter.AddCoinViewHolder> {
    private final ArrayList<Coin> coins;
    private final OnClickListener onClickListener;

    public AddWalletAdapter(ArrayList<Coin> coins, OnClickListener onClickListener) {
        this.coins = coins;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AddCoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_add_wallet, parent, false);

        return new AddCoinViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddCoinViewHolder holder, int position) {
        holder.setup(coins.get(position));
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public interface OnClickListener {
        void onWalletToAddSelected(Coin coin);
    }

    class AddCoinViewHolder extends RecyclerView.ViewHolder {
        public AddCoinViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setup(Coin coin) {
            itemView.setOnClickListener(v -> onClickListener.onWalletToAddSelected(coin));

            ImageView ivCoinIcon = itemView.findViewById(R.id.iv_item_add_coin_coin_icon);
            TextView tvCoinName = itemView.findViewById(R.id.tv_item_add_coin_coin_name);
            TextView tvCoinSymbol = itemView.findViewById(R.id.tv_item_add_coin_coin_symbol);

            ivCoinIcon.setImageResource(coin.getIconResId());
            tvCoinName.setText(coin.getCoinName());
            tvCoinSymbol.setText(coin.toString());
        }
    }
}
