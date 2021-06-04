package eu.farmingpool.farmingwallet.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance;

    private static final String DATABASE_NAME = "FarmingWallet";
    private static final int DATABASE_VERSION = 1;

    static final String CONTACTS_TABLE_NAME = "contacts";
    static final String COL_CONTACT_ID = "id";
    static final String COL_CONTACT_NAME = "name";
    static final String COL_CONTACT_SURNAME = "surname";
    static final String ADDRESSES_TABLE_NAME = "addresses";
    static final String COL_ADDRESS_ID = "id";
    static final String COL_ADDRESS_CONTACT_ID = "contact_id";
    static final String COL_COIN = "coin";
    static final String COL_ADDRESS = "address";

    private static final String CREATE_CONTACTS_TABLE =
            "create table if not exists " + CONTACTS_TABLE_NAME + " ("
                    + COL_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_CONTACT_NAME + " TEXT, "
                    + COL_CONTACT_SURNAME + " TEXT);";

    private static final String CREATE_ADDRESSES_TABLE =
            "create table if not exists " + ADDRESSES_TABLE_NAME + " ("
                    + COL_ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_ADDRESS_CONTACT_ID + " TEXT, "
                    + COL_COIN + " TEXT, "
                    + COL_ADDRESS + " TEXT);";

    final static String CONTACT_ADDRESSES_QUERY =
            "SELECT " +
                    CONTACTS_TABLE_NAME + "." + COL_CONTACT_ID + "," +
                    COL_CONTACT_NAME + "," +
                    COL_CONTACT_SURNAME + "," +
                    COL_COIN + "," +
                    COL_ADDRESS +
                    " FROM " + CONTACTS_TABLE_NAME +
                    " JOIN " + ADDRESSES_TABLE_NAME +
                    " ON " + CONTACTS_TABLE_NAME + "." + COL_CONTACT_ID + " = " + ADDRESSES_TABLE_NAME + "." + COL_ADDRESS_CONTACT_ID +
                    " WHERE " + ADDRESSES_TABLE_NAME + "." + COL_COIN + "= ?;";

    final static String CONTACT_FROM_KEY_QUERY =
            "SELECT " +
                    CONTACTS_TABLE_NAME + "." + COL_CONTACT_ID + "," +
                    COL_CONTACT_NAME + "," +
                    COL_CONTACT_SURNAME +
                    " FROM " + CONTACTS_TABLE_NAME +
                    " JOIN " + ADDRESSES_TABLE_NAME +
                    " ON " + CONTACTS_TABLE_NAME + "." + COL_CONTACT_ID + " = " + ADDRESSES_TABLE_NAME + "." + COL_ADDRESS_CONTACT_ID +
                    " WHERE " + COL_COIN + "= ? AND " + COL_ADDRESS + " = ?;";

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }

        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_ADDRESSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

