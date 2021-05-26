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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.keywords.Keyword;
import eu.farmingpool.farmingwallet.keywords.Keywords;
import eu.farmingpool.farmingwallet.views.KeywordItem;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class KeywordsCheckFragment extends Fragment implements
        KeywordItem.KeywordItemInterface {
    private static final int N_COLS = 2;

    private KeywordsViewModel keywordsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_keywords_check, container, false);

        keywordsViewModel = new ViewModelProvider(requireActivity()).get(KeywordsViewModel.class);
        Keywords keywordsToCheck = keywordsViewModel.getKeywordsToCheck().getValue();

        if (keywordsToCheck != null)
            fillKeywordsTable(root, keywordsToCheck.keywords);

        return root;
    }

    @Override
    public void onKeywordItemTextChanged(int keywordIndex, boolean correct) {
        keywordsViewModel.onKeywordItemTextChanged(keywordIndex, correct);
    }

    private void fillKeywordsTable(View view, ArrayList<Keyword> keywords) {
        TableLayout tableLayout = view.findViewById(R.id.tl_keywords_check_keywords);

        int rows = keywords.size() / N_COLS;
        int keywordIndex = 0;

        for (int row = 0; row < rows; row++) {
            TableRow tableRow = new TableRow(requireContext());
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);

            for (int col = 0; col < N_COLS; col++) {
                Keyword keyword = keywords.get(keywordIndex);

                KeywordItem keywordItem = getKeywordItem(keyword);
                TableRow.LayoutParams tableParams = getLayoutParams();
                tableRow.addView(keywordItem, tableParams);

                keywordIndex++;
            }

            tableLayout.addView(tableRow, linearLayoutParams);
        }
    }

    @NotNull
    private KeywordItem getKeywordItem(Keyword keyword) {
        KeywordItem keywordItem = new KeywordItem(requireContext());

        keywordItem.setKeyword(keyword);
        keywordItem.setEditable(true);
        keywordItem.setChecker(this);
        return keywordItem;
    }

    @NotNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams tableParams = new TableRow.LayoutParams(0, WRAP_CONTENT);

        tableParams.weight = 1;
        tableParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.fragment_keywords_keywords_margin);
        tableParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.fragment_keywords_keywords_margin);
        tableParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.fragment_keywords_keywords_margin);
        return tableParams;
    }
}
