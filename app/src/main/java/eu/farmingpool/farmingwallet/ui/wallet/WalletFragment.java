package eu.farmingpool.farmingwallet.ui.wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.accounts.Accounts;
import eu.farmingpool.farmingwallet.blockchain.BlockchainClient;
import eu.farmingpool.farmingwallet.blockchain.BlockchainClientFactory;
import eu.farmingpool.farmingwallet.logging.Event;
import eu.farmingpool.farmingwallet.wallet.Coin;
import eu.farmingpool.farmingwallet.wallet.Wallet;

import static eu.farmingpool.farmingwallet.logging.Log.E;
import static eu.farmingpool.farmingwallet.logging.Log.logEvent;
import static eu.farmingpool.farmingwallet.logging.Tag.EVENT_ACTIVITY_SEND;
import static eu.farmingpool.farmingwallet.utils.Utils.KEY_SERIALIZABLE_COIN;
import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class WalletFragment extends Fragment {
    private Coin coin;
    private Interface walletFragmentInterface;
    private TextView tvBalance;
    private TextView tvCorresponding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        Bundle args = getArguments();

        if (args != null)
            coin = (Coin) args.getSerializable(KEY_SERIALIZABLE_COIN);

        setupIcon(root);
        setupBalance(root);
        setupSendReceiveButtons(root);
        setupViewPager(root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            walletFragmentInterface = (Interface) context;
        } catch (ClassCastException e) {
            logEvent(E, new Event("onAttach", e.getMessage(), EVENT_ACTIVITY_SEND));
        }
    }

    private void setupIcon(View view) {
        ImageView ivIcon = view.findViewById(R.id.iv_item_balance_icon);
        ivIcon.setImageResource(coin.getIconResId());
    }

    private void setupBalance(View view) {


        tvBalance = view.findViewById(R.id.tv_item_balance_balance);
        tvCorresponding = view.findViewById(R.id.tv_item_balance_corresponding);
        TextView tvAddress = view.findViewById(R.id.tv_item_balance_address);

        Account account = Accounts.getInstance().getCurrentAccount();
        fetchTransactionRecords(account, Coin.XCH);
        tvBalance.setText("...");

        //tvCorresponding.setText(String.format(getLocale(), coin.getFormat(), -1.0));
        tvCorresponding.setText("...");

        tvAddress.setText(account.getWallet(coin).getMainAddress(account));
        tvAddress.setOnClickListener(v -> copyAddress());
    }

    private void setupSendReceiveButtons(View view) {
        Button btSend = view.findViewById(R.id.bt_send);
        btSend.setOnClickListener(v -> walletFragmentInterface.onSendClicked(coin));

        Button btReceive = view.findViewById(R.id.bt_receive);
        btReceive.setOnClickListener(v -> walletFragmentInterface.onReceiveClicked(coin));
    }

    private void setupViewPager(View view) {
        WalletFragmentStateAdapter adapter = new WalletFragmentStateAdapter(this);
        ViewPager2 viewPager = view.findViewById(R.id.fragment_wallet_host_fragment);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tl_wallet);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getTabTitle(position))
        ).attach();
    }

    private void copyAddress() {
        Account account = Accounts.getInstance().getCurrentAccount();

        ClipboardManager clipboard = (ClipboardManager) (requireActivity().getSystemService(Context.CLIPBOARD_SERVICE));
        ClipData clip = ClipData.newPlainText("address", account.getWallet(coin).getMainAddress(account));
        clipboard.setPrimaryClip(clip);
        Toast.makeText(requireContext(), R.string.fragment_keywords_creation_copied, Toast.LENGTH_SHORT).show();
    }

    public void fetchTransactionRecords(Account account, Coin coin) {
        BlockchainClient blockchainClient = BlockchainClientFactory.get(coin);
        blockchainClient.fetchAndCacheTransactionRecords(account, (records, balance) -> {
            Wallet wallet = account.getWallet(coin);
            wallet.setTransactions(records);
            wallet.setBalance(balance);

            tvBalance.setText(String.format(getLocale(), coin.getFormat(), coin.formattedAmount(balance)));

            account.updateWallet(coin, wallet);
        });
    }

    public interface Interface {
        void onSendClicked(Coin coin);

        void onReceiveClicked(Coin coin);
    }
}