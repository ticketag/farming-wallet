package eu.farmingpool.farmingwallet.keywords;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Keywords implements Serializable {
    public final ArrayList<Keyword> keywords = new ArrayList<>();

    public int size() {
        return keywords.size();
    }

    public void add(Keyword keyword) {
        keywords.add(keyword);
    }

    public void addAll(Collection<Keyword> other) {
        this.keywords.addAll(other);
    }

    public Keyword get(int i) {
        return keywords.get(i);
    }

    public Keyword getByIndex(int keywordIndex) {
        for (Keyword keyword : keywords)
            if (keyword.index == keywordIndex)
                return keyword;

        return null;
    }

    public Keywords getFraction(double start, double end) {
        int fromIndex = (int) (start * keywords.size());
        int toIndex = (int) (end * keywords.size());

        Keywords fraction = new Keywords();

        fraction.addAll(keywords.subList(fromIndex, toIndex));

        return fraction;
    }

    public Keywords getRandom(int n) {
        List<Keyword> selectedKeywordsList = getSelectedKeywordsList(n);
        selectedKeywordsList.sort((o1, o2) -> Integer.compare(o1.index, o2.index));

        Keywords selectedKeywords = new Keywords();
        selectedKeywords.addAll(selectedKeywordsList);

        return selectedKeywords;
    }

    public String toPlainString() {
        StringBuilder builder = new StringBuilder();
        String separator = "\n";

        for (Keyword keyword : keywords)
            builder.append(keyword.index).append(". ").append(keyword.value).append(separator);

        String plainString = builder.toString();
        plainString = plainString.substring(0, plainString.length() - separator.length());

        return plainString;
    }

    @NotNull
    private List<Keyword> getSelectedKeywordsList(int n) {
        ArrayList<Keyword> allKeywordsList = new ArrayList<>(keywords);

        Collections.shuffle(allKeywordsList);

        return allKeywordsList.subList(0, n);
    }
}
