package eu.farmingpool.farmingwallet.ui.dapps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DAppsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DAppsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}