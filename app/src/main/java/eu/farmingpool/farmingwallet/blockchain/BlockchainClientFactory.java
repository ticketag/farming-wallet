package eu.farmingpool.farmingwallet.blockchain;

import eu.farmingpool.farmingwallet.wallet.Coin;

public class BlockchainClientFactory {
    public static BlockchainClient get(Coin coin) {
        switch (coin) {
            case XCH:
                return ChiaBlockchainClient.getInstance();
            case BTC:
                return BitcoinBlockchainClient.getInstance();
            case ETH:
                return EthereumBlockchainClient.getInstance();
            default:
                throw new IllegalStateException("Unexpected value: " + coin);
        }
    }
}
