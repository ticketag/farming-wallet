package eu.farmingpool.farmingwallet.accounts;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.coins.Coin;
import eu.farmingpool.farmingwallet.keywords.Keywords;
import eu.farmingpool.farmingwallet.utils.SharedDataManager;

public class Account {
    private static final String KEY_KEYWORDS = "keywords";
    private static final String SEPARATOR = "_";

    private final int id;
    private final ArrayList<Coin> coins = new ArrayList<>();
    private String name;

    public Account(int id, Keywords keywords) {
        this.id = id;

        saveKeywords(keywords);
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

    public boolean hasCoin(Coin coin) {
        return coins.contains(coin);
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public Keywords retrieveKeywords() {
        String keywordsTag = getKeywordsTag();

        return SharedDataManager.getSharedKeywords(keywordsTag);
    }

    private void saveKeywords(Keywords keywords) {
        String keywordsTag = getKeywordsTag();

        SharedDataManager.putSharedKeywords(keywordsTag, keywords);
    }

    private String getKeywordsTag() {
        return id + SEPARATOR + KEY_KEYWORDS;
    }
}
