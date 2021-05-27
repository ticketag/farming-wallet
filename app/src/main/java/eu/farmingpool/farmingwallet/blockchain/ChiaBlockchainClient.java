package eu.farmingpool.farmingwallet.blockchain;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.coins.Coin;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;

public class ChiaBlockchainClient extends BlockchainClient {
    private static ChiaBlockchainClient instance;

    private ChiaBlockchainClient() {
        super(Coin.XCH);
    }

    public static ChiaBlockchainClient getInstance() {
        if (instance == null) {
            synchronized (ChiaBlockchainClient.class) {
                if (instance == null) {
                    instance = new ChiaBlockchainClient();
                }
            }
        }

        return instance;
    }

    @Override
    protected void postTransaction(String transaction) {

    }

    @Override
    protected TransactionRecords fetchTransactions(Account account) {
        return null;
    }
}
