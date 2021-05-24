package eu.farmingpool.farmingwallet.ui.wallet.creation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class KeywordsViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<String>> keywords = new MutableLiveData<>();

    public MutableLiveData<ArrayList<String>> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords.setValue(keywords);
    }
}
