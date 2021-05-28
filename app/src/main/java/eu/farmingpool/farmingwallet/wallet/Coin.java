package eu.farmingpool.farmingwallet.wallet;

import androidx.annotation.DrawableRes;

import eu.farmingpool.farmingwallet.R;

public enum Coin {
    EUR,
    USD,
    XCH,
    BTC;

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
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public String getFormat() {
        switch (this) {
            case EUR:
            case USD:
            case XCH:
            case BTC:
                return "%.2f " + this;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
