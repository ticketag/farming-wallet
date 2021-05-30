package eu.farmingpool.farmingwallet.blockchain;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.transactions.Transaction;
import eu.farmingpool.farmingwallet.transactions.TransactionRecord;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.wallet.Coin;

public class BitcoinBlockchainClient extends BlockchainClient {
    private static BitcoinBlockchainClient instance;

    private BitcoinBlockchainClient() {
        super(Coin.XCH);
    }

    public static BitcoinBlockchainClient getInstance() {
        if (instance == null) {
            synchronized (BitcoinBlockchainClient.class) {
                if (instance == null) {
                    instance = new BitcoinBlockchainClient();
                }
            }
        }

        return instance;
    }

    @Override
    protected void postTransaction(Transaction transaction) {

    }

    @NotNull
    @Override
    protected TransactionRecords fetchTransactionRecords(Account account) {
        TransactionRecords transactionRecords = new TransactionRecords();

        String testSender = "xch1wh88x8m47dqkw5wuselqs4t0un5ns3k6z5cf92fpju27jwnpx3fsx2lsha";

        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis()), 1.2, testSender, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 5), 2.1, testSender, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 9), -3.45, testSender, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 5), 0.1, testSender, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 7), -0.4, testSender, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 3), 3.7, testSender, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 2), -4.1, testSender, 0));

        return transactionRecords;
    }
}
