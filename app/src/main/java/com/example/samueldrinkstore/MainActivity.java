package com.example.samueldrinkstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler dbHandler;
    ListView listView;
    Button showView;
    Button hideView;

    Button btnAddProduct;

    EditText edtBarcode;
    EditText edtProductCode;
    EditText edtProductDescription;
    EditText edtPrice;
    EditText edtCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);
        dbHandler.open();

        Context context = this;

        listView = (ListView) findViewById(R.id.listView);
        List<Product> productList = dbHandler.getAllProducts();

        //ArrayAdapter<Product> arrayAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1,productList);
        //listView.setAdapter(arrayAdapter);

        MyListAdapter adapter = new MyListAdapter(this, productList);
        listView.setAdapter(adapter);
        /*Product product = new Product(0,"12345", "coca1234", "CocaCola Coke 2l", 2000, 25.0, "Cooldrink");
        dbHandler.addProduct(product);

        Product product1 = new Product(0,"7654321" ,"sprite2312", "Sprite Can 500ml", 500, 11.50, "Cooldrink");
        dbHandler.addProduct(product1);
         */

        edtBarcode = findViewById(R.id.edtBarcode);
        edtProductCode = findViewById(R.id.edtProductCode);
        edtProductDescription = findViewById(R.id.edtProductDescription);
        edtPrice = findViewById(R.id.edtPrice);
        edtCategory = findViewById(R.id.edtCategory);
        btnAddProduct = findViewById(R.id.btnAdd);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean missing_input = false;
                String barcode = edtBarcode.getText().toString();
                String productCode = edtProductCode.getText().toString();
                String productDescription = edtProductDescription.getText().toString();
                String str_price = edtPrice.getText().toString();
                double price = 0.0;
                String category = edtCategory.getText().toString();

                if (barcode.isEmpty()){
                    missing_input = true;
                }
                if (productCode.isEmpty()){
                    missing_input = true;
                }
                if (productDescription.isEmpty()){
                    missing_input = true;
                }
                if (str_price.isEmpty()){
                    missing_input = true;
                    Toast.makeText(MainActivity.this, "Invalid value: " + str_price, Toast.LENGTH_SHORT).show();
                } else {
                    price = Double.parseDouble(str_price);
                    price = Math.round(price*100)/100;
                    Toast.makeText(MainActivity.this, "Double value: " + price, Toast.LENGTH_SHORT).show();
                }
                if (category.isEmpty()){
                    missing_input = true;
                }

                if (missing_input){
                    return;
                }

                Product product = new Product(0,barcode,productCode,productDescription,price,category);
                dbHandler.addProduct(product);

                edtBarcode.getText().clear();
                edtCategory.getText().clear();
                edtProductCode.getText().clear();
                edtProductDescription.getText().clear();
                edtPrice.getText().clear();

                List<Product> productList = dbHandler.getAllProducts();

                //ArrayAdapter<Product> arrayAdapter = new ArrayAdapter<Product>(context, android.R.layout.simple_list_item_1,productList);
                //listView.setAdapter(arrayAdapter);

                MyListAdapter adapter = new MyListAdapter(MainActivity.this, productList);
                listView.setAdapter(adapter);
            }
        });

        showView = (Button) findViewById(R.id.showView);
        hideView = (Button) findViewById(R.id.hideView);

        hideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
            }
        });

        showView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.VISIBLE);
            }
        });
        /*for (Product p : productList) {
            String msg = "ID: " + p.getId() + ", Product code: " + p.getProductCode() + ", Description: " + p.getProductDescription() + ", Size: " + p.getSize() + "ml, Price: R" + p.getPrice() + ", Category: " + p.getCategory();
            Log.d("Product", msg);

        }*/
        //ArrayAdapter<Product> arrayAdapter = new ArrayAdapter<>(this,R.layout.activity_main,)
    }
}