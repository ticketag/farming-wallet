package eu.farmingpool.farmingwallet.accounts;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.keywords.Keywords;
import eu.farmingpool.farmingwallet.ui.ObservableChanges;
import eu.farmingpool.farmingwallet.utils.EncryptedSharedDataManager;
import eu.farmingpool.farmingwallet.utils.SharedDataManager;
import eu.farmingpool.farmingwallet.wallet.Coin;
import eu.farmingpool.farmingwallet.wallet.Wallet;

public class Account {
    private static final String KEY_KEYWORDS = "keywords";
    private static final String KEY_WALLET = "wallet";
    private static final String SEPARATOR = "_";

    private final int id;
    private final ArrayList<Coin> coins = new ArrayList<>();
    private String name;

    Account(int id, Keywords keywords) {
        this.id = id;

        saveKeywords(keywords);
        onChange();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        onChange();
    }

    public void addCoin(Coin coin) {
        if (coins.contains(coin))
            throw new IllegalStateException("Coin " + coin + " already in account");

        coins.add(coin);

        String walletKey = getWalletKey(coin);
        SharedDataManager.putWallet(walletKey, new Wallet(coin));

        onChange();
    }

    public boolean hasCoin(Coin coin) {
        return coins.contains(coin);
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public ArrayList<Wallet> getWallets() {
        ArrayList<Wallet> wallets = new ArrayList<>();

        for (Coin coin : coins)
            wallets.add(getWallet(coin));

        return wallets;
    }

    public Wallet getWallet(Coin coin) {
        String walletKey = getWalletKey(coin);

        return SharedDataManager.getWallet(walletKey);
    }

    public void updateWallet(Coin coin, Wallet wallet) {
        String walletKey = getWalletKey(coin);
        SharedDataManager.putWallet(walletKey, wallet);
    }

    public Keywords retrieveKeywords() {
        String keywordsKey = getKeywordsKey();

        return EncryptedSharedDataManager.getSharedKeywords(keywordsKey);
    }

    private void saveKeywords(Keywords keywords) {
        String keywordsKey = getKeywordsKey();

        EncryptedSharedDataManager.putKeywords(keywordsKey, keywords);
    }

    private String getKeywordsKey() {
        return id + SEPARATOR + KEY_KEYWORDS;
    }

    private String getWalletKey(Coin coin) {
        return id + SEPARATOR + coin + SEPARATOR + KEY_WALLET;
    }

    private void onChange() {
        EncryptedSharedDataManager.putAccount(this);
        ObservableChanges.getInstance().onAccountChanged();
    }
}
