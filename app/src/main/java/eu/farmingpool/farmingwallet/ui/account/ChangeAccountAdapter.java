package eu.farmingpool.farmingwallet.ui.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.accounts.Account;
import eu.farmingpool.farmingwallet.accounts.Accounts;

public class ChangeAccountAdapter extends RecyclerView.Adapter<ChangeAccountAdapter.BaseViewHolder> {
    private static final int TYPE_ADD_ACCOUNT = 0;
    private static final int TYPE_ACCOUNT = 1;
    private final OnClickListener onClickListener;

    public ChangeAccountAdapter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_ACCOUNT) {
            View view = inflater.inflate(R.layout.item_account, parent, false);
            return new AccountViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_account_add, parent, false);
            return new AddAccountViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (position == Accounts.getInstance().getCount())
            holder.setOnClickListener(v -> onClickListener.onAddAccountClicked());
        else {
            ((AccountViewHolder) holder).setup(Accounts.getInstance().getAccount(position));
            holder.setOnClickListener(v -> onClickListener.onAccountClicked(position));
        }
    }

    @Override
    public int getItemCount() {
        return Accounts.getInstance().getCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == Accounts.getInstance().getCount())
            return TYPE_ADD_ACCOUNT;

        return TYPE_ACCOUNT;
    }

    public interface OnClickListener {
        void onAccountClicked(int i);

        void onAddAccountClicked();
    }

    protected static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }
    }

    protected static class AccountViewHolder extends BaseViewHolder {
        private final TextView tvAccountName;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAccountName = itemView.findViewById(R.id.tv_item_account_account_name);
        }

        public void setup(Account account) {
            tvAccountName.setText(account.getName());
        }
    }

    protected static class AddAccountViewHolder extends BaseViewHolder {
        public AddAccountViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

