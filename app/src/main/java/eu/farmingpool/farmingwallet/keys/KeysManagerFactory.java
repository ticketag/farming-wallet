package eu.farmingpool.farmingwallet.keys;

import eu.farmingpool.farmingwallet.coins.Coin;

public class KeysManagerFactory {
    public static KeysManager get(Coin coin) {
        switch (coin) {
            case XCH:
                return ChiaKeysManager.getInstance();
            default:
                throw new IllegalStateException("Unexpected value: " + coin);
        }
    }
}
