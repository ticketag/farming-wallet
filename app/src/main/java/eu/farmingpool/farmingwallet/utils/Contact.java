package eu.farmingpool.farmingwallet.utils;

import java.util.HashMap;

import eu.farmingpool.farmingwallet.keys.Key;
import eu.farmingpool.farmingwallet.wallet.Coin;

public class Contact {
    HashMap<Coin, Key> receivingAddresses;
    private String name;
    private String surname;

    public void setReceivingAddress(Coin coin, String keyString) {
        Key key = new Key(keyString);

        this.receivingAddresses.put(coin, key);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public Key getReceivingAddress(Coin coin) {
        return receivingAddresses.get(coin);
    }
}
