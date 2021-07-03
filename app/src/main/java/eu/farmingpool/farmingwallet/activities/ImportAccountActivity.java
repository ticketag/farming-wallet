package eu.farmingpool.farmingwallet.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import org.jetbrains.annotations.NotNull;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.keywords.Keyword;
import eu.farmingpool.farmingwallet.keywords.Keywords;
import eu.farmingpool.farmingwallet.views.KeywordItem;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ImportAccountActivity extends AppCompatActivity {
    private static final int N_COLS = 2;
    private KeywordItem[] keywordItems;
    private static String[] testKeywords = {"opera", "erode", "denial", "hobby", "use", "cereal", "answer", "obvious", "birth", "soon", "collect", "second", "evolve", "scene", "check", "essay", "tray", "result", "giant", "usage", "eye", "demand", "orient", "stone"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_import_account);
        keywordItems = new KeywordItem[24];
        fillKeywordsTable();
        setupConfirmButton();
    }

    private void fillKeywordsTable() {
        Keywords keywords = new Keywords();
        TableLayout tableLayout = findViewById(R.id.tl_keywords_import_keywords);

        int rows = 24 / N_COLS;
        int keywordIndex = 0;

        for (int row = 0; row < rows; row++) {
            TableRow tableRow = new TableRow(this);
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);

            for (int col = 0; col < N_COLS; col++) {
                KeywordItem keywordItem = new KeywordItem(this);
                keywordItem.setKeyword(new Keyword(keywordIndex+1, testKeywords[keywordIndex]));
                keywordItem.etKeyword.setText(testKeywords[keywordIndex]);
                TableRow.LayoutParams tableParams = getLayoutParams();
                tableRow.addView(keywordItem, tableParams);
                keywordItems[keywordIndex] = keywordItem;
                keywordIndex++;
            }

            tableLayout.addView(tableRow, linearLayoutParams);
        }
    }

    private void setupConfirmButton() {
        AppCompatButton btCreateNewWallet = findViewById(R.id.bt_fragment_account_imported_done);
        btCreateNewWallet.setOnClickListener(v -> {
            Keywords keywords = readKeywordsTable();
            Accounts accounts = Accounts.getInstance();
            accounts.addAccount(keywords);
            //TODO: navigation
        });
    }

    private Keywords readKeywordsTable() {
        Keywords ret = new Keywords();
        for (int i = 0; i < keywordItems.length; i++) {
            ret.add(new Keyword(i, keywordItems[i].etKeyword.getText().toString()));
        }
        return ret;
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
