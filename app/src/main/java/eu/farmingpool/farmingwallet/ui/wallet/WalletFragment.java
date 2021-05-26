package eu.farmingpool.farmingwallet.ui.wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.coins.Coin;

public class WalletFragment extends Fragment implements CoinBalancesAdapter.OnClickListener {
    CoinBalancesAdapter.OnClickListener onClickListener;
    RecyclerView rvCoins;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            onClickListener = (CoinBalancesAdapter.OnClickListener) context;
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onCoinBalanceClicked(int i) {
        onClickListener.onCoinBalanceClicked(i);
    }

    private void setupRecyclerView(View view) {
        ArrayList<CoinBalance> coinBalances = new ArrayList<>();

        coinBalances.add(new CoinBalance(Coin.XCH, 1.45));

        CoinBalancesAdapter adapter = new CoinBalancesAdapter(coinBalances, this);

        rvCoins = view.findViewById(R.id.rv_coins);
        rvCoins.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvCoins.setAdapter(adapter);
    }
}