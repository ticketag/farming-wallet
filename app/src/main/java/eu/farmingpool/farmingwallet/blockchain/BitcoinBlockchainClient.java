package eu.farmingpool.farmingwallet.blockchain;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.transactions.Transaction;
import eu.farmingpool.farmingwallet.transactions.TransactionRecord;
import eu.farmingpool.farmingwallet.transactions.TransactionRecordEventHandler;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.MOCK_RECEIVING_ADDRESS;

public class BitcoinBlockchainClient extends BlockchainClient {
    private static BitcoinBlockchainClient instance;

    private BitcoinBlockchainClient() {
        super(Coin.BTC);
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
    protected TransactionRecords fetchTransactionRecords(Account account, TransactionRecordEventHandler handler) {
        TransactionRecords transactionRecords = new TransactionRecords();

        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis()), 1.2, MOCK_RECEIVING_ADDRESS, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 5), 2.1, MOCK_RECEIVING_ADDRESS, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 9), -3.45, MOCK_RECEIVING_ADDRESS, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 5), 0.1, MOCK_RECEIVING_ADDRESS, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 7), -0.4, MOCK_RECEIVING_ADDRESS, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() + 1000 * 60 * 3), 3.7, MOCK_RECEIVING_ADDRESS, 0));
        transactionRecords.insert(new TransactionRecord(coin, new Timestamp(System.currentTimeMillis() - 1000 * 60 * 2), -4.1, MOCK_RECEIVING_ADDRESS, 0));

        return transactionRecords;
    }
}
