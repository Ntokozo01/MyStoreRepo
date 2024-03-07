package com.example.samueldrinkstore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private  static final String DATABASE_NAME = "productscatalogue.db";
    private  static final int DATABASE_VERSION = 3;

    public DBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "barcode TEXT UNIQUE DEFAULT 'Unknown',"+
                "productCode TEXT UNIQUE,"+
                "productdescription TEXT," +
                "price REAL," +
                "category TEXT)";
        db.execSQL((createTable));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }
}
