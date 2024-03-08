package com.example.samueldrinkstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;

public class AddProducts extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN = 1;
    Button btnScan;
    Button btnSaveProduct;
    Button btnClose;
    EditText edtBarcode;
    EditText edtProductCode;
    EditText edtProductDescription;
    EditText edtPrice;
    EditText edtCategory;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        dbHandler = new DatabaseHandler(this);
        dbHandler.open();

        edtBarcode = (EditText) findViewById(R.id.edtBarcode);
        edtProductCode = findViewById(R.id.edtProductCode);
        edtProductDescription = findViewById(R.id.edtProductDescription);
        edtPrice = findViewById(R.id.edtPrice);
        edtCategory = findViewById(R.id.edtCategory);
        btnSaveProduct = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.btnClose);
        btnScan = findViewById(R.id.btnScan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBarcodeScannerActivity();
            }
        });
        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean missing_input = false;
                String barcode = edtBarcode.getText().toString();
                String productCode = edtProductCode.getText().toString();
                String productDescription = edtProductDescription.getText().toString();
                String str_price = edtPrice.getText().toString();
                double price = 0.0;
                String category = edtCategory.getText().toString();

                if (barcode.isEmpty()) {
                    missing_input = true;
                }
                if (productCode.isEmpty()) {
                    missing_input = true;
                }
                if (productDescription.isEmpty()) {
                    missing_input = true;
                }
                if (str_price.isEmpty()) {
                    missing_input = true;
                    Toast.makeText(AddProducts.this, "Invalid value: " + str_price, Toast.LENGTH_SHORT).show();
                } else {
                    price = Double.parseDouble(str_price);
                    Toast.makeText(AddProducts.this, "Double value: " + price, Toast.LENGTH_SHORT).show();
                }
                if (category.isEmpty()) {
                    missing_input = true;
                }

                if (missing_input) {
                    return;
                }

                Product product = new Product(0, barcode, productCode, productDescription, price, category);
                dbHandler.addProduct(product);

                edtBarcode.getText().clear();
                edtCategory.getText().clear();
                edtProductCode.getText().clear();
                edtProductDescription.getText().clear();
                edtPrice.getText().clear();

                Toast.makeText(AddProducts.this, "Product added Successfully: " + product, Toast.LENGTH_SHORT).show();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProducts.this, MainActivity.class);
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