package eu.farmingpool.farmingwallet.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableRow;

import eu.farmingpool.farmingwallet.R;

public class KeywordsRow extends TableRow {
    public KeywordsRow(Context context) {
        this(context, null);
    }

    public KeywordsRow(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_keywords_row, this);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.KeywordsRow,
                0, 0);

        try {
            int cols = typedArray.getInt(R.styleable.KeywordsRow_keywordsRowCols, 1);

            setupRow(cols);
        } finally {
            typedArray.recycle();
        }

        invalidate();
        requestLayout();
    }

    private void setupRow(int cols) {
        for (int col = 0; col < cols; col++) {
            KeywordItem keywordItem = new KeywordItem(getContext());
            TableRow.LayoutParams params = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            addView(keywordItem, params);
        }
    }
}
