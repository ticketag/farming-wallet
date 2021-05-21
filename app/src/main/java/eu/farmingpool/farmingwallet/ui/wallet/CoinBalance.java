package eu.farmingpool.farmingwallet.ui.wallet;

import eu.farmingpool.farmingwallet.coins.Coin;

public class CoinBalance {
    private final Coin coin;
    private final double amount;

    public CoinBalance(Coin coin, double amount) {
        this.coin = coin;
        this.amount = amount;
    }

    public Coin getCoin() {
        return coin;
    }

    public double getAmount() {
        return amount;
    }
}
