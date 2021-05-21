package eu.farmingpool.farmingwallet.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import eu.farmingpool.farmingwallet.R;

public class WalletFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        setupViewPager(root);

        return root;
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