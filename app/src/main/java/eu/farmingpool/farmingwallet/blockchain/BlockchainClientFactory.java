package eu.farmingpool.farmingwallet.blockchain;

import eu.farmingpool.farmingwallet.coins.Coin;

public class BlockchainClientFactory {
    public static BlockchainClient get(Coin coin) {
        switch (coin) {
            case XCH:
                return ChiaBlockchainClient.getInstance();
            default:
                throw new IllegalStateException("Unexpected value: " + coin);
        }
    }
}
