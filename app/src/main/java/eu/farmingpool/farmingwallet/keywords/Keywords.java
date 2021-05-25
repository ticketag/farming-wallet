package eu.farmingpool.farmingwallet.keywords;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Keywords implements Serializable {
    public final ArrayList<Keyword> keywords = new ArrayList<>();

    public void add(Keyword keyword) {
        keywords.add(keyword);
    }

    public void addAll(Collection<Keyword> other) {
        this.keywords.addAll(other);
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

    @NotNull
    private List<Keyword> getSelectedKeywordsList(int n) {
        ArrayList<Keyword> allKeywordsList = new ArrayList<>(keywords);

        Collections.shuffle(allKeywordsList);

        return allKeywordsList.subList(0, n);
    }
}
