package com.example.samueldrinkstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private SQLiteDatabase db;
    private final DBHelper dbHelper;

    public DatabaseHandler(Context context){
        dbHelper = new DBHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public void addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put("barcode",product.getBarcode());
        values.put("productCode", product.getProductCode());
        values.put("productDescription", product.getProductDescription());
        values.put("price", product.getPrice());
        values.put("category", product.getCategory());
        db.insert("products",null,values);
    }

    public List<Product> getAllProducts() {
        List<Product> lst_products = new ArrayList<>();
        Cursor cursor = db.query("products",null,null,null, null,null, null);

        if (cursor.moveToFirst()){
            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setBarcode(cursor.getString(1));
                product.setProductCode(cursor.getString(2));
                product.setProductDescription(cursor.getString(3));
                product.setPrice(cursor.getDouble(4));
                product.setCategory(cursor.getString(5));
                lst_products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lst_products;
    }
}
