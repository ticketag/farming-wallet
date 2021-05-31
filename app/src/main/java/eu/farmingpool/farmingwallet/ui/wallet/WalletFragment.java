package eu.farmingpool.farmingwallet.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class WalletFragment extends Fragment {
    private Button btSend;
    private Button btReceive;
    private Coin coin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        coin = (Coin) getArguments().getSerializable("coin");

        setupIcon(root);
        setupBalance(root);
        setupButtons(root);
        setupViewPager(root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setupIcon(View view) {
        ImageView ivIcon = view.findViewById(R.id.iv_ib_icon);
        ivIcon.setImageResource(coin.getIconResId());
    }

    private void setupBalance(View view) {
        TextView tvBalance = view.findViewById(R.id.tv_ib_balance);
        TextView tvCorresponding = view.findViewById(R.id.tv_ib_corresponding);

        Account account = Accounts.getInstance().getCurrentAccount();
        double balance = account.getWallet(coin).getBalance();

        tvBalance.setText(String.format(getLocale(), coin.getFormat(), balance));
        tvCorresponding.setText(String.format(getLocale(), coin.getFormat(), -1.0));
    }

    private void setupButtons(View view) {
        btSend = view.findViewById(R.id.bt_send);
        btReceive = view.findViewById(R.id.bt_receive);
    }

    private void setupViewPager(View view) {
        WalletFragmentStateAdapter adapter = new WalletFragmentStateAdapter(this);

        ViewPager2 viewPager = view.findViewById(R.id.fragment_wallet_host_fragment);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tl_wallet);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getTabTitle(position))
        ).attach();
    }
}