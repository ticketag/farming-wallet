package eu.farmingpool.farmingwallet.ui.wallet.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.wallet.Coin;

public class WalletDetailFragment extends Fragment {
    private Button btSend;
    private Button btReceive;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wallet_detail, container, false);

        Coin coin = (Coin) getArguments().getSerializable("coin");
        setupIcon(root, coin);
        setupButtons(root);
        setupViewPager(root);

        return root;
    }

    private void setupIcon(View view, Coin coin) {
        ImageView ivIcon = view.findViewById(R.id.iv_ib_icon);
        ivIcon.setImageResource(coin.getIconResId());
    }

    private void setupButtons(View view) {
        btSend = view.findViewById(R.id.bt_send);
        btReceive = view.findViewById(R.id.bt_receive);
    }

    private void setupViewPager(View view) {
        WalletDetailFragmentStateAdapter adapter = new WalletDetailFragmentStateAdapter(this);

        ViewPager2 viewPager = view.findViewById(R.id.fragment_wallet_detail_host_fragment);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tl_wallet);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getTabTitle(position))
        ).attach();
    }
}