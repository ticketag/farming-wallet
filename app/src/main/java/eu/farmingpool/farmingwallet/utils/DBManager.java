package eu.farmingpool.farmingwallet.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Observable;

import eu.farmingpool.farmingwallet.wallet.Coin;

import static eu.farmingpool.farmingwallet.utils.DBHelper.COL_ADDRESS;
import static eu.farmingpool.farmingwallet.utils.DBHelper.COL_CONTACT_NAME;
import static eu.farmingpool.farmingwallet.utils.DBHelper.COL_CONTACT_SURNAME;
import static eu.farmingpool.farmingwallet.utils.DBHelper.CONTACT_ADDRESSES_QUERY;

public class DBManager extends Observable {
    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() throws SQLException {
        dbHelper = DBHelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Contact> getContacts(Coin coin) {
        Cursor cursor = database.rawQuery(CONTACT_ADDRESSES_QUERY, new String[]{coin.toString()});

        if (cursor == null)
            return new ArrayList<>();

        ArrayList<Contact> contacts = getContacts(coin, cursor);

        cursor.close();

        return contacts;
    }

    @NotNull
    private ArrayList<Contact> getContacts(Coin coin, Cursor cursor) {
        ArrayList<Contact> contacts = new ArrayList<>();
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(cursor.getColumnIndex(COL_CONTACT_NAME)));
            contact.setSurname(cursor.getString(cursor.getColumnIndex(COL_CONTACT_SURNAME)));
            contact.setReceivingAddress(coin, cursor.getString(cursor.getColumnIndex(COL_ADDRESS)));
            contacts.add(contact);
        }
        return contacts;
    }
}
