package eu.farmingpool.farmingwallet.ui.wallet.creation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import eu.farmingpool.farmingwallet.keywords.Keywords;

public class KeywordsViewModel extends ViewModel {
    private final MutableLiveData<Keywords> keywords = new MutableLiveData<>();
    private final MutableLiveData<Keywords> keywordsToCheck = new MutableLiveData<>();
    private final MutableLiveData<Boolean> allKeywordsCorrect = new MutableLiveData<>();

    private final HashMap<Integer, Boolean> correctKeywords = new HashMap<>();

    public MutableLiveData<Keywords> getKeywords() {
        return keywords;
    }

    public void setKeywords(Keywords keywords) {
        this.keywords.setValue(keywords);
    }

    public MutableLiveData<Boolean> getAllKeywordsCorrect() {
        return allKeywordsCorrect;
    }

    public void generateKeywordsToCheck(int n) {
        Keywords allKeywords = keywords.getValue();

        if (allKeywords != null)
            keywordsToCheck.setValue(allKeywords.getRandom(n));

        correctKeywords.clear();
        allKeywordsCorrect.setValue(false);
    }

    public MutableLiveData<Keywords> getKeywordsToCheck() {
        return keywordsToCheck;
    }

    public void onKeywordItemTextChanged(int keywordIndex, boolean isCorrect) {
        correctKeywords.put(keywordIndex, isCorrect);

        int totalCorrect = 0;
        for (Boolean correct : correctKeywords.values()) {
            if (correct != null)
                if (correct)
                    totalCorrect += 1;
        }

        Keywords keywords = keywordsToCheck.getValue();

        if (keywords != null)
            if (keywords.size() == totalCorrect)
                allKeywordsCorrect.setValue(true);
    }
}
