package eu.farmingpool.farmingwallet.ui.send;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eu.farmingpool.farmingwallet.utils.Contact;
import eu.farmingpool.farmingwallet.wallet.Coin;

public class SendViewModel extends ViewModel {
    private final MutableLiveData<Contact> contact = new MutableLiveData<>();
    private final MutableLiveData<Coin> coin = new MutableLiveData<>();
    private final MutableLiveData<Double> amount = new MutableLiveData<>();
    private final MutableLiveData<Double> temporaryAmount = new MutableLiveData<>();

    public SendViewModel() {
        amount.setValue(0.0);
    }

    public MutableLiveData<Contact> getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact.setValue(contact);
    }

    @NonNull
    public MutableLiveData<Coin> getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin.setValue(coin);
    }

    @NonNull
    public MutableLiveData<Double> getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        Double currentAmount = this.amount.getValue();

        if (currentAmount != null) {
            if (currentAmount != amount)
                this.amount.setValue(amount);
        } else
            this.amount.setValue(amount);
    }
}
