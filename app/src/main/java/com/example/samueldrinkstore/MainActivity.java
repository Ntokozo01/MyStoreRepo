package com.example.samueldrinkstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler dbHandler;
    ListView listView;
    Button showView;
    Button hideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);
        dbHandler.open();

       /* Product product = new Product(0, "coca1234", "CocaCola Coke 2l", 2000, 25.0, "Cooldrink");
        dbHandler.addProduct(product);

        Product product1 = new Product(0, "sprite2312", "Sprite Can 500ml", 500, 11.50, "Cooldrink");
        dbHandler.addProduct(product1);
*/
        showView = (Button) findViewById(R.id.showView);
        hideView = (Button) findViewById(R.id.hideView);

        listView = (ListView) findViewById(R.id.listView);
        List<Product> productList = dbHandler.getAllProducts();

        ArrayAdapter<Product> arrayAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1,productList);
        listView.setAdapter(arrayAdapter);

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