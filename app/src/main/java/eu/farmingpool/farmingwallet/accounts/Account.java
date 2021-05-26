package eu.farmingpool.farmingwallet.accounts;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.coins.Coin;

public class Account {
    private static final String PRIVATE_SUFFIX = "private";
    private static final String SEPARATOR = "_";

    private final int id;
    private final ArrayList<Coin> coins = new ArrayList<>();
    private String name;

    public Account(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCoin(Coin coin) {
        coins.add(coin);
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    private void retrievePrivateKey(Coin coin) {
        if (!coins.contains(coin))
            throw new IllegalStateException("Unexpected value: " + this);

        String privateKeyTag = id + SEPARATOR + coin.toString() + SEPARATOR + PRIVATE_SUFFIX;
    }
}
