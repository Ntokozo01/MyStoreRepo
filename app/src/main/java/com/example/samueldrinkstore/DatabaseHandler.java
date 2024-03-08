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

    public int updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put("barcode",product.getBarcode());
        values.put("productCode", product.getProductCode());
        values.put("productDescription", product.getProductDescription());
        values.put("price", product.getPrice());
        values.put("category", product.getCategory());

        // Specify which record to update based on the ID
        String selection = "barcode = ?";
        String[] selectionArgs = {product.getBarcode() };

        // Perform the update
        int count = db.update("products", values, selection, selectionArgs);
        return count;
    }

    public int deleteProduct(String barcode){
        String selection = "barcode = ?";
        String[] selectionArgs = { barcode };

        // Perform the delete operation
        int count = db.delete("products", selection, selectionArgs);

        // Close the database
        db.close();
        return 1;
    }

    public Product getProduct(String barcode){
        String query = "SELECT * FROM products WHERE barcode LIKE '%" + barcode + "%'";

        Cursor cursor = db.rawQuery(query, null);
        Product product = new Product();

        if (cursor.moveToFirst()){
                product.setId(cursor.getInt(0));
                product.setBarcode(cursor.getString(1));
                product.setProductCode(cursor.getString(2));
                product.setProductDescription(cursor.getString(3));
                product.setPrice(cursor.getDouble(4));
                product.setCategory(cursor.getString(5));
        }
        cursor.close();
        return product;
    }

    private List<Product> getProducts(Cursor cursor){
        List<Product> lst_products = new ArrayList<>();

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

    public List<Product> searchOnBarcode(String searchText) {
        // Define the query
        String query = "SELECT * FROM products WHERE barcode LIKE '%" + searchText + "%'";

        Cursor cursor = db.rawQuery(query, null);

        List<Product> lst_products = getProducts(cursor);
        cursor.close();
        return lst_products;
    }

    public List<Product> searchOnAllThree(String barcode, String productCode, String productDescription) {
        // Define the query
        String query = "SELECT * FROM products WHERE (barcode LIKE '%" + barcode + "%')" +
                "AND (productCode LIKE '%" + productCode + "%')" +
                "AND (productDescription LIKE '%" + productDescription + "%')";

        Cursor cursor = db.rawQuery(query, null);

        List<Product> lst_products = getProducts(cursor);
        cursor.close();
        return lst_products;
    }
    public List<Product> searchOnProductCode(String searchText) {
        // Define the query
        String query = "SELECT * FROM products WHERE productCode LIKE '%" + searchText + "%'";

        Cursor cursor = db.rawQuery(query, null);

        List<Product> lst_products = getProducts(cursor);
        cursor.close();
        return lst_products;
    }
    public List<Product> searchOnDescription(String searchText) {
        // Define the query
        String query = "SELECT * FROM products WHERE productDescription LIKE '%" + searchText + "%'";

        Cursor cursor = db.rawQuery(query, null);

        List<Product> lst_products = getProducts(cursor);
        cursor.close();
        return lst_products;
    }
    public List<Product> getAllProducts() {
        Cursor cursor = db.query("products",null,null,null, null,null, null);

        List<Product> lst_products = getProducts(cursor);
        cursor.close();
        return lst_products;
    }
}
