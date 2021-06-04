package eu.farmingpool.farmingwallet.utils;

import eu.farmingpool.farmingwallet.keys.Key;

public class Contact {
    private Key receivingAddress;
    private String name;
    private String surname;

    public void setReceivingAddress(String keyString) {
        receivingAddress = new Key(keyString);
    }

    public void setReceivingAddress(Key key) {
        receivingAddress = key;
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

    public Key getReceivingAddress() {
        return receivingAddress;
    }
}
