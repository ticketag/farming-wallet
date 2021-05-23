package eu.farmingpool.farmingwallet.ui.wallet;

import eu.farmingpool.farmingwallet.coins.Coin;

public class CoinBalance {
    public final Coin coin;
    public final double amount;

    public CoinBalance(Coin coin, double amount) {
        this.coin = coin;
        this.amount = amount;
    }
}
