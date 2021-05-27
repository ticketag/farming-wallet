package eu.farmingpool.farmingwallet.keywords;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import eu.farmingpool.farmingwallet.R;

public class KeywordsGenerator {
    private static final Random random = new Random();

    public static Keywords generate(Context context, int n) {
        ArrayList<String> possibleKeywords = new ArrayList<>(Arrays.asList(
                context.getResources().getStringArray(R.array.keywords_en)));

        Keywords keywords = new Keywords();

        for (int i = 0; i < n; i++) {
            String keywordValue = possibleKeywords.get(random.nextInt(possibleKeywords.size()));
            Keyword keyword = new Keyword(i + 1, keywordValue);
            keywords.add(keyword);
        }

        return keywords;
    }
}
