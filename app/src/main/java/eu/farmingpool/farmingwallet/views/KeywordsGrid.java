package eu.farmingpool.farmingwallet.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableRow;

import androidx.annotation.Nullable;

import java.util.HashMap;

import eu.farmingpool.farmingwallet.R;

public class KeywordsGrid extends LinearLayout {
    private final LayoutInflater inflater;
    private final HashMap<Integer, HashMap<Integer, KeywordItem>> keywords;

    public KeywordsGrid(Context context) {
        this(context, null);
    }

    public KeywordsGrid(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeywordsGrid(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, 0, 0);
    }

    public KeywordsGrid(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        keywords = new HashMap<>();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_keywords_grid, this);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.KeywordsGrid,
                0, 0);

        try {
            int rows = typedArray.getInt(R.styleable.KeywordsGrid_keywordGridRows, 1);
            int cols = typedArray.getInt(R.styleable.KeywordsGrid_keywordGridCols, 1);

            setupGrid(rows, cols);
        } finally {
            typedArray.recycle();
        }

        invalidate();
        requestLayout();
    }

    private void setupGrid(int rows, int cols) {
        for (int row = 0; row < rows; row++)
            addView(getRow(cols));
    }

    private TableRow getRow(int cols) {
        TableRow row = new TableRow(getContext());
        TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        row.setLayoutParams(params);
        row.setVisibility(VISIBLE);

        for (int col = 0; col < cols; col++)
            row.addView(getKeywordItem());

        return row;
    }

    private KeywordItem getKeywordItem() {
        KeywordItem keywordItem = new KeywordItem(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        keywordItem.setLayoutParams(params);
        keywordItem.setVisibility(VISIBLE);

        return keywordItem;
    }
}
