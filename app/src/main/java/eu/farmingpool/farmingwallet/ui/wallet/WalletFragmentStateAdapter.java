package eu.farmingpool.farmingwallet.ui.wallet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import eu.farmingpool.farmingwallet.R;

public class WalletFragmentStateAdapter extends FragmentStateAdapter {
    public WalletFragmentStateAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TransactionRecordsFragment();
            case 1:
                return new PricesFragment();
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public int getTabTitle(int position) {
        switch (position) {
            case 0:
                return R.string.fragment_transactions_title;
            case 1:
                return R.string.fragment_prices_title;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }
}
