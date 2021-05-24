package eu.farmingpool.farmingwallet.keywords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class KeywordsGenerator {
    private static final ArrayList<String> POSSIBLE_KEYWORDS = new ArrayList<>(Arrays.asList("ma", "che", "cazzo", "ne", "so", "di", "quali", "siano", "le", "keywords", "giuste"));
    private static final Random random = new Random();

    public static ArrayList<String> generate(int n) {
        ArrayList<String> keywords = new ArrayList<>();

        for (int i = 0; i < n; i++)
            keywords.add(POSSIBLE_KEYWORDS.get(random.nextInt(POSSIBLE_KEYWORDS.size())));

        return keywords;
    }
}
