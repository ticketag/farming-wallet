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

public class ChangeAccountAdapter extends RecyclerView.Adapter<ChangeAccountAdapter.AccountViewHolder> {
    private final OnClickListener onClickListener;

    public ChangeAccountAdapter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_account, parent, false);

        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.setup(Accounts.getInstance().getAccount(position));
        holder.setOnClickListener(v -> onClickListener.onAccountClicked(position));
    }

    @Override
    public int getItemCount() {
        return Accounts.getInstance().getCount();
    }

    public interface OnClickListener {
        void onAccountClicked(int i);
    }

    protected static class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAccountName;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAccountName = itemView.findViewById(R.id.tv_item_account_account_name);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }

        public void setup(Account account) {
            tvAccountName.setText(account.getName());
        }
    }
}

