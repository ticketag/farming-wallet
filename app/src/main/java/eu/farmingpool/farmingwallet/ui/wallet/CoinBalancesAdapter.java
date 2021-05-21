package eu.farmingpool.farmingwallet.ui.wallet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.coins.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class CoinBalancesAdapter extends RecyclerView.Adapter<CoinBalancesAdapter.CoinBalanceViewHolder> {
    private final ArrayList<CoinBalance> coinBalances;
    private final OnClickListener onClickListener;

    public CoinBalancesAdapter(ArrayList<CoinBalance> coinBalances, OnClickListener onClickListener) {
        this.coinBalances = coinBalances;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CoinBalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_coin_balance, parent, false);

        return new CoinBalanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinBalanceViewHolder holder, int position) {
        holder.setup(coinBalances.get(position));
        holder.setOnClickListener(v -> onClickListener.onCoinBalanceClicked(position));
    }

    @Override
    public int getItemCount() {
        return coinBalances.size();
    }

    public interface OnClickListener {
        void onCoinBalanceClicked(int i);
    }

    protected static class CoinBalanceViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;

        private final TextView tvCoinName;
        private final TextView tvCoinAmount;
        private final TextView tvCoinValue;
        private final TextView tvCoinChange;
        private final TextView tvCoinCorresponding;

        public CoinBalanceViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;

            tvCoinName = itemView.findViewById(R.id.icb_coin_name);
            tvCoinAmount = itemView.findViewById(R.id.icb_coin_amount);
            tvCoinValue = itemView.findViewById(R.id.icb_coin_value);
            tvCoinChange = itemView.findViewById(R.id.icb_coin_change);
            tvCoinCorresponding = itemView.findViewById(R.id.icb_coin_corresponding);
        }

        public void setup(CoinBalance coinBalance) {
            Coin coin = coinBalance.getCoin();
            double amount = coinBalance.getAmount();

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
