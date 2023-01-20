package com.example.find_your_spot;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelperPhotos {

    public static final String Table_Column_IMAGE_ID = "image_id";
    public static final String Table_Column_USER_ID = "user_id";
    public static final String Table_Column_IMAGE = "image";
    public static final String Table_Column_CITY = "city";
    public static final String Table_Column_COUNTRY = "country";
    private final Context mContext;

    private DatabaseHelper mDbHelper;
    public static SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "Images.db";
    private static final int DATABASE_VERSION = 1;

    public static final String IMAGES_TABLE = "ImagesTable";

    private static final String CREATE_IMAGES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + IMAGES_TABLE + " (" +
                    Table_Column_IMAGE_ID  +" INTEGER PRIMARY KEY AUTOINCREMENT, " + Table_Column_USER_ID +" VARCHAR, "+ Table_Column_CITY +" VARCHAR, "+ Table_Column_COUNTRY +" VARCHAR, "+
                    Table_Column_IMAGE + " BLOB NOT NULL );";


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_IMAGES_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //db.execSQL("DROP TABLE IF EXISTS " + CREATE_IMAGES_TABLE);
            onCreate(db);
        }
    }


    public SQLiteHelperPhotos(Context ctx) {
        mContext = ctx;
        mDbHelper = new DatabaseHelper(mContext);
    }

    public SQLiteHelperPhotos open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    // Insert the image to the Sqlite DB
    public void insertImage(byte[] imageBytes) {
        ContentValues cv = new ContentValues();
        cv.put(Table_Column_IMAGE, imageBytes);
        mDb.insert(IMAGES_TABLE, null, cv);
    }

    // Get the image from SQLite DB
    // We will just get the last image we just saved for convenience...
    public byte[] retreiveImageFromDB() {
        Cursor cur = mDb.query(false, IMAGES_TABLE, new String[]{Table_Column_IMAGE_ID, Table_Column_USER_ID, Table_Column_IMAGE},
                null, null, null, null,
                Table_Column_IMAGE_ID + " DESC", "1");
        if (cur.moveToFirst()) {
            byte[] blob = cur.getBlob(cur.getColumnIndex(Table_Column_IMAGE));
            cur.close();
            return blob;
        }
        cur.close();
        return null;
    }

}