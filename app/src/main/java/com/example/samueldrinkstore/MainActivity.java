package com.example.samueldrinkstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SCAN = 1;
    ListView listView;
    Button showView;
    Button hideView;
    Button btnScan;
    Button btnAddProduct;
    EditText edtBarcode;
    EditText edtProductCode;
    EditText edtProductDescription;
    EditText edtPrice;
    EditText edtCategory;
    private DatabaseHandler dbHandler;
    private List<Product> productList;
    private MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);
        dbHandler.open();

        Context context = this;

        //ArrayAdapter<Product> arrayAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1,productList);
        //listView.setAdapter(arrayAdapter);

        listView = (ListView) findViewById(R.id.listView);

        if (adapter == null){
            productList = dbHandler.getAllProducts();
            adapter = new MyListAdapter(this, productList);
        }
        listView.setAdapter(adapter);

        edtBarcode = findViewById(R.id.edtBarcode);
        edtProductCode = findViewById(R.id.edtProductCode);
        edtProductDescription = findViewById(R.id.edtProductDescription);
        btnAddProduct = findViewById(R.id.btnAdd);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProducts.class);
                startActivity(intent);
            }
        });

        edtBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String productDescription = edtProductDescription.getText().toString();
                String productCode = edtProductCode.getText().toString();
                String barcode = s.toString();

                productList = dbHandler.searchOnAllThree(barcode, productCode, productDescription);

                adapter = new MyListAdapter(MainActivity.this, productList);
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtProductCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String productDescription = edtProductDescription.getText().toString();
                String productCode = s.toString();
                String barcode = edtBarcode.getText().toString();

                productList = dbHandler.searchOnAllThree(barcode, productCode, productDescription);

                adapter = new MyListAdapter(MainActivity.this, productList);
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtProductDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String productDescription = s.toString();
                String productCode = edtProductCode.getText().toString();
                String barcode = edtBarcode.getText().toString();

                productList = dbHandler.searchOnAllThree(barcode, productCode, productDescription);

                adapter = new MyListAdapter(MainActivity.this, productList);
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        showView = (Button) findViewById(R.id.showView);
        hideView = (Button) findViewById(R.id.hideView);

        btnScan = (Button) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBarcodeScannerActivity();
            }
        });

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this,ViewAndUpdate.class);
                intent.putExtra("selectedItem", product.getBarcode());
                Toast.makeText(MainActivity.this, "Item : " + position + " selected, id : " + id, Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
    }

    public void startBarcodeScannerActivity() {
        Intent intent = new Intent(this, BarcodeScannerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("barcodeValue")) {
                String barcodeValue = data.getStringExtra("barcodeValue");
                edtBarcode.setText(barcodeValue);
                // Use the barcode value as needed
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}