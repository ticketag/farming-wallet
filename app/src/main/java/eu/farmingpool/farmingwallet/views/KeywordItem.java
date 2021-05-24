package eu.farmingpool.farmingwallet.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import eu.farmingpool.farmingwallet.R;

import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class KeywordItem extends LinearLayout {
    public final TextView tvNumber;
    public final EditText etKeyword;

    public KeywordItem(Context context) {
        this(context, null);
    }

    public KeywordItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeywordItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, 0, 0);
    }

    public KeywordItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_keyword, this);

        tvNumber = findViewById(R.id.tv_item_keyword_number);
        etKeyword = findViewById(R.id.et_item_keyword_word);
    }

    public void setNumber(int number) {
        tvNumber.setText(String.format(getLocale(), "%d. ", number));
    }

    public void setKeyword(String keyword) {
        etKeyword.setText(keyword);
    }

    public void setEditable(boolean editable) {
        etKeyword.setFocusable(editable);
        etKeyword.setBackgroundResource(android.R.color.transparent);
    }
}
