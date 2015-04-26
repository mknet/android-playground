package net.marcelkoch.playground.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by marcel on 23.04.15.
 */
public class JobsOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "dictionary";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "message TEXT, " +
                    "sender TEXT, " +
                    "latitude TEXT, " +
                    "longitude TEXT);";

    public JobsOpenHelper(Context context) {
        super(context, "almost_there", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void saveJob(String message, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        String ROW1 = "INSERT INTO " + TABLE_NAME + " Values ('"+message+"','"+address+"','long','lang');";
        db.execSQL(ROW1);

        Cursor cursor = this.getReadableDatabase().query(false, TABLE_NAME, new String[]{"message", "sender"}, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            Log.d("MK", "message="+cursor.getString(0));
            Log.d("MK", "sender=" + cursor.getString(1));
        }
    }
}
