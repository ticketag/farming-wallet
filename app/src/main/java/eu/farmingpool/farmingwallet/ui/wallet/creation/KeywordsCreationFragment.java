package eu.farmingpool.farmingwallet.ui.wallet.creation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

        setupViewPager(root);
        setupCopyButton(root);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            keywordsCreationFragmentInterface = (KeywordsCreationFragmentInterface) context;
        } catch (Exception ignored) {
        }
    }

    private Keywords getKeywords() {
        KeywordsViewModel keywordsViewModel = new ViewModelProvider(requireActivity()).get(KeywordsViewModel.class);

        return keywordsViewModel.getKeywords().getValue();
    }

    private void setupViewPager(View view) {
        Keywords keywords = getKeywords();
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

    private void setupCopyButton(View view) {
        TextView llCopy = view.findViewById(R.id.tv_keywords_creation_copy);

        llCopy.setOnClickListener(v -> copyKeywords());
    }

    private void copyKeywords() {
        Keywords keywords = getKeywords();

        ClipboardManager clipboard = (ClipboardManager) (requireActivity().getSystemService(Context.CLIPBOARD_SERVICE));
        ClipData clip = ClipData.newPlainText("keywords", keywords.toPlainString());
        clipboard.setPrimaryClip(clip);

        Toast.makeText(requireContext(), R.string.fragment_keywords_creation_copied, Toast.LENGTH_SHORT).show();
    }

    public interface KeywordsCreationFragmentInterface {
        void onNextPressed();
    }
}
