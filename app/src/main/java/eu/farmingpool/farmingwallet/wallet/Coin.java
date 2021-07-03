package eu.farmingpool.farmingwallet.wallet;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;

public enum Coin implements Serializable {
    EUR,
    USD,
    XCH,
    BTC,
    ETH;

    private static final ArrayList<Coin> NON_ADDABLE_COINS = new ArrayList<>(Arrays.asList(USD, EUR));

    public static ArrayList<Coin> getAvailableCoins(Account account) {
        ArrayList<Coin> availableCoins = new ArrayList<>();
        ArrayList<Coin> coins = account.getCoins();

        for (Coin coin : Coin.values()) {
            if (!coins.contains(coin) && !NON_ADDABLE_COINS.contains(coin))
                availableCoins.add(coin);
        }

        return availableCoins;
    }

    public String getCoinName() {
        switch (this) {
            case EUR:
                return "Euro";
            case USD:
                return "Dollar";
            case XCH:
                return "Chia";
            case BTC:
                return "Bitcoin";
            case ETH:
                return "Ethereum";
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    @DrawableRes
    public int getIconResId() {
        switch (this) {
            case XCH:
                return R.drawable.ic_coin_chia;
            case BTC:
                return R.drawable.ic_coin_bitcoin;
            case ETH:
                return R.drawable.ic_coin_ethereum;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    @ColorRes
    public int getColorResId() {
        switch (this) {
            case XCH:
                return R.color.xch;
            case BTC:
                return R.color.btc;
            case ETH:
                return R.color.eth;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public String getFormat() {
        switch (this) {
            case EUR:
            case USD:
                return "%.2f " + this;
            case XCH:
            case BTC:
            case ETH:
                return "%.8f " + this;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
    public double formattedAmount(double intValue) {
        return formattedAmount((long)intValue);
    }
    public double formattedAmount(long intValue) {
        switch (this) {
            case EUR:
            case USD:
                return ((double)intValue)/100;
            case XCH:
            case BTC:
            case ETH:
                return ((double)intValue)/1000000000000L;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public boolean isImplemented() {
        switch (this) {
            case XCH:
            case BTC:
                return true;
            case ETH:
                return false;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
