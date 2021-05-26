package eu.farmingpool.farmingwallet.ui.wallet.creation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.keywords.Keywords;

public class KeywordsCreationFragment extends Fragment {
    KeywordsCreationFragmentInterface keywordsCreationFragmentInterface;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_keywords_creation, container, false);

        setupViewPager(root, getKeywords());

        return root;
    }

    private Keywords getKeywords() {
        KeywordsViewModel keywordsViewModel = new ViewModelProvider(requireActivity()).get(KeywordsViewModel.class);

        return keywordsViewModel.getKeywords().getValue();
    }

    private void setupViewPager(View view, Keywords keywords) {
        ViewPager2 vpKeywordsTable = view.findViewById(R.id.vp_keywords_table);
        vpKeywordsTable.setAdapter(new KeywordsTableFragmentStateAdapter(this, keywords));

        view.findViewById(R.id.bt_keywords_creation_next).setOnClickListener(v -> {
            if (vpKeywordsTable.getCurrentItem() == 0)
                vpKeywordsTable.setCurrentItem(1);
            else
                keywordsCreationFragmentInterface.onNextPressed();
        });

        TabLayout tlKeywordsCreation = view.findViewById(R.id.tl_keywords_creation);
        new TabLayoutMediator(tlKeywordsCreation, vpKeywordsTable,
                (tab, position) -> {
                }
        ).attach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            keywordsCreationFragmentInterface = (KeywordsCreationFragmentInterface) context;
        } catch (Exception ignored) {
        }
    }

    public interface KeywordsCreationFragmentInterface {
        void onNextPressed();
    }
}
