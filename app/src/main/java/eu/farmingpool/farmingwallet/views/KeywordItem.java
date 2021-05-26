package eu.farmingpool.farmingwallet.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.keywords.Keyword;

import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class KeywordItem extends LinearLayout {
    public final TextView tvNumber;
    public final EditText etKeyword;
    public final ImageView ivCorrect;
    public Keyword keyword;

    public KeywordItem(Context context) {
        this(context, null);
    }

    public KeywordItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeywordItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public KeywordItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_keyword, this);

        tvNumber = findViewById(R.id.tv_item_keyword_number);
        etKeyword = findViewById(R.id.et_item_keyword_word);
        ivCorrect = findViewById(R.id.iv_item_keyword_correct);
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
        tvNumber.setText(String.format(getLocale(), "%d. ", keyword.index));
        etKeyword.setText("");
    }

    public void showText() {
        etKeyword.setText(keyword.value);
    }

    public void setEditable(boolean editable) {
        etKeyword.setFocusable(editable);
        etKeyword.setBackgroundResource(android.R.color.transparent);
    }

    public void setChecker(KeywordItemInterface keywordItemInterface) {
        etKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean correct = keyword.value.equals(String.valueOf(s));

                if (correct)
                    ivCorrect.setVisibility(VISIBLE);
                else
                    ivCorrect.setVisibility(INVISIBLE);

                keywordItemInterface.onKeywordItemTextChanged(keyword.index, correct);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public interface KeywordItemInterface {
        void onKeywordItemTextChanged(int keywordIndex, boolean correct);
    }
}
