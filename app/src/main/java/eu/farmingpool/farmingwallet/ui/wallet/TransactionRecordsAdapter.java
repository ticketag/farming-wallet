package eu.farmingpool.farmingwallet.ui.wallet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.transactions.TransactionRecord;
import eu.farmingpool.farmingwallet.transactions.TransactionRecords;
import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.Utils.getLocale;

public class TransactionRecordsAdapter extends RecyclerView.Adapter<TransactionRecordsAdapter.TransactionRecordViewHolder> {
    private final TransactionRecords transactionRecords;
    private final OnClickListener onClickListener;

    public TransactionRecordsAdapter(TransactionRecords transactionRecords, OnClickListener onClickListener) {
        this.transactionRecords = transactionRecords;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TransactionRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_transaction_record, parent, false);

        return new TransactionRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionRecordViewHolder holder, int position) {
        holder.setup(transactionRecords.get(position));
        holder.setOnClickListener(v -> onClickListener.onTransactionClicked(position));
    }

    @Override
    public int getItemCount() {
        return transactionRecords.size();
    }

    public interface OnClickListener {
        void onTransactionClicked(int i);
    }

    protected static class TransactionRecordViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvOperation;
        private final TextView tvAmount;
        private final TextView tvDate;
        private final TextView tvCorresponding;

        public TransactionRecordViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_itr_icon);
            tvOperation = itemView.findViewById(R.id.tv_itr_operation);
            tvAmount = itemView.findViewById(R.id.tv_itr_amount);
            tvDate = itemView.findViewById(R.id.tv_itr_date);
            tvCorresponding = itemView.findViewById(R.id.tv_itr_corresponding);
        }

        public void setup(TransactionRecord transactionRecord) {
            double amount = transactionRecord.amount;
            boolean isPositive = amount > 0;
            int colorId = isPositive ? R.color.positive_amount : R.color.negative_amount;
            int color = tvAmount.getContext().getColor(colorId);
            int iconId = isPositive ? R.drawable.ic_receive_24 : R.drawable.ic_send_24;

            Coin coin = transactionRecord.coin;
            Date date = new Date(transactionRecord.timestamp.getTime());
            DateFormat f = new SimpleDateFormat("HH:mm   dd/MM/yyyy", getLocale());

            ivIcon.setImageResource(iconId);
            tvOperation.setText(isPositive ? R.string.itr_operation_received : R.string.itr_operation_sent);
            tvOperation.setTextColor(color);
            tvAmount.setText(String.format(getLocale(), coin.getFormat(), amount));
            tvAmount.setTextColor(color);
            tvDate.setText(f.format(date));
            tvCorresponding.setText("0.0 $");
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }
    }
}
