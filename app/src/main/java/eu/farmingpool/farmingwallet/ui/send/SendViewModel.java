package eu.farmingpool.farmingwallet.ui.send;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eu.farmingpool.farmingwallet.utils.Contact;
import eu.farmingpool.farmingwallet.wallet.Wallet;

public class SendViewModel extends ViewModel {
    private final MutableLiveData<Contact> contact = new MutableLiveData<>();
    private final MutableLiveData<Wallet> wallet = new MutableLiveData<>();
    private final MutableLiveData<Double> amount = new MutableLiveData<>();

    public MutableLiveData<Contact> getContact() {
        return contact;
    }

    public MutableLiveData<Wallet> getWallet() {
        return wallet;
    }

    public MutableLiveData<Double> getAmount() {
        return amount;
    }
}
