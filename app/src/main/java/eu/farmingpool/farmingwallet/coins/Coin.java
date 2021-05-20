package eu.farmingpool.farmingwallet.coins;

import eu.farmingpool.farmingwallet.R;

public enum Coin {
    EUR,
    USD,
    XCH;

    public String getCoinName() {
        switch (this) {
            case EUR:
                return "Euro";
            case USD:
                return "Dollar";
            case XCH:
                return "Chia";
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public int getIconResId() {
        switch (this) {
            case EUR:
            case USD:
            case XCH:
                return R.drawable.ic_coin_chia;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
