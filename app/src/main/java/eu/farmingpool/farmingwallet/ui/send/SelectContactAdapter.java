package eu.farmingpool.farmingwallet.ui.send;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;
import eu.farmingpool.farmingwallet.utils.Contact;
import eu.farmingpool.farmingwallet.wallet.Coin;

public class SelectContactAdapter extends Adapter<SelectContactAdapter.ContactViewHolder> {
    private final Coin coin;
    private final ArrayList<Contact> contacts;
    private final OnClickListener onClickListener;

    public SelectContactAdapter(Coin coin, ArrayList<Contact> contacts, OnClickListener onClickListener) {
        this.coin = coin;
        this.contacts = contacts;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_contact, parent, false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.setup(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public interface OnClickListener {
        void onContactSelected(Contact contact);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setup(Contact contact) {
            itemView.setOnClickListener(v -> onClickListener.onContactSelected(contact));

            TextView tvFullName = itemView.findViewById(R.id.tv_item_contact_full_name);
            TextView tvReceivingAddress = itemView.findViewById(R.id.tv_item_contact_receiving_address);

            tvFullName.setText(contact.getFullName());
            tvReceivingAddress.setText(contact.getReceivingAddress(coin).getValue());
        }
    }
}
