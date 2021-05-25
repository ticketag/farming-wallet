package eu.farmingpool.farmingwallet.ui.wallet.creation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eu.farmingpool.farmingwallet.keywords.Keywords;

public class KeywordsViewModel extends ViewModel {
    private final MutableLiveData<Keywords> keywords = new MutableLiveData<>();
    private final MutableLiveData<Keywords> keywordsToCheck = new MutableLiveData<>();

    public MutableLiveData<Keywords> getKeywords() {
        return keywords;
    }

    public void setKeywords(Keywords keywords) {
        this.keywords.setValue(keywords);
    }

    public void generateKeywordsToCheck(int n) {
        Keywords allKeywords = keywords.getValue();

        if (allKeywords != null)
            keywordsToCheck.setValue(allKeywords.getRandom(n));
    }

    public MutableLiveData<Keywords> getKeywordsToCheck() {
        return keywordsToCheck;
    }
}
