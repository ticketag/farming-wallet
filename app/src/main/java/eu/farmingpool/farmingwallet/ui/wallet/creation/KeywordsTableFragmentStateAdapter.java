package eu.farmingpool.farmingwallet.ui.wallet.creation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import eu.farmingpool.farmingwallet.keywords.Keywords;

public class KeywordsTableFragmentStateAdapter extends FragmentStateAdapter {
    private final Keywords keywords;

    public KeywordsTableFragmentStateAdapter(@NonNull Fragment fragment, Keywords keywords) {
        super(fragment);

        this.keywords = keywords;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return KeywordsTableFragment.getInstance(keywords.getFraction(0, 0.5));
            case 1:
                return KeywordsTableFragment.getInstance(keywords.getFraction(0.5, 1.0));
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
