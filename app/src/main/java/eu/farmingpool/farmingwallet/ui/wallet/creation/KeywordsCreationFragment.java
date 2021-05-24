package eu.farmingpool.farmingwallet.ui.wallet.creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.views.KeywordItem;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class KeywordsCreationFragment extends Fragment {
    private static final int N_COLS = 2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_keywords_creation, container, false);

        KeywordsViewModel keywordsViewModel = new ViewModelProvider(requireActivity()).get(KeywordsViewModel.class);
        keywordsViewModel.getKeywords().observe(getViewLifecycleOwner(), keywords -> {
            setupKeywordsTable(root, keywords);
        });

        return root;
    }

    private void setupKeywordsTable(View view, ArrayList<String> keywords) {
        TableLayout tableLayout = view.findViewById(R.id.tl_keywords);

        int rows = keywords.size() / N_COLS;
        int keywordIndex = 0;

        for (int row = 0; row < rows; row++) {
            TableRow tableRow = new TableRow(requireContext());
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);

            for (int col = 0; col < N_COLS; col++) {
                KeywordItem keywordItem = new KeywordItem(requireContext());
                TableRow.LayoutParams tableParams = new TableRow.LayoutParams();

                tableParams.weight = 1;
                tableParams.leftMargin = 10;
                tableParams.rightMargin = 10;
                tableParams.bottomMargin = 20;

                keywordItem.setNumber(keywordIndex + 1);
                keywordItem.setKeyword(keywords.get(keywordIndex));
                keywordItem.setEditable(false);

                tableRow.addView(keywordItem, tableParams);

                keywordIndex++;
            }

            tableLayout.addView(tableRow, linearLayoutParams);
        }
    }
}
