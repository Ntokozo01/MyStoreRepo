package com.example.samueldrinkstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewAndUpdate extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN = 1;
    Button btnScan;
    Button btnUpdateProduct;

    Button btnDeleteProduct;
    Button btnClose;
    EditText edtBarcode;
    EditText edtProductCode;
    EditText edtProductDescription;
    EditText edtPrice;
    EditText edtCategory;
    AlertDialog.Builder builder;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_update);


        dbHandler = new DatabaseHandler(this);
        dbHandler.open();

        edtBarcode = (EditText) findViewById(R.id.edtBarcode);
        edtProductCode = findViewById(R.id.edtProductCode);
        edtProductDescription = findViewById(R.id.edtProductDescription);
        edtPrice = findViewById(R.id.edtPrice);
        edtCategory = findViewById(R.id.edtCategory);
        btnUpdateProduct = findViewById(R.id.btnUpdate);
        btnDeleteProduct = findViewById(R.id.btnDelete);
        btnClose = findViewById(R.id.btnClose);
        btnScan = findViewById(R.id.btnScan);

        String selectedItem = getIntent().getStringExtra("selectedItem");
        Product product = dbHandler.getProduct(selectedItem);
        edtBarcode.setText(product.getBarcode());
        edtProductCode.setText(product.getProductCode());
        edtProductDescription.setText(product.getProductDescription());
        edtPrice.setText("" + product.getPrice());
        edtCategory.setText(product.getCategory());

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBarcodeScannerActivity();
            }
        });

        builder = new AlertDialog.Builder(this);
        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("Confirm")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton(
                                "Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int res = dbHandler.deleteProduct(selectedItem);

                                        if (res > 0) {
                                            Toast.makeText(ViewAndUpdate.this, res + " Product(s) Deleted Successfully.", Toast.LENGTH_SHORT).show();
                                        }
                                        btnClose.callOnClick();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create().show();
            }
        });
        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
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
                    //Toast.makeText(ViewAndUpdate.this, "Invalid value: " + str_price, Toast.LENGTH_SHORT).show();
                } else {
                    price = Double.parseDouble(str_price);
                    //Toast.makeText(ViewAndUpdate.this, "Double value: " + price, Toast.LENGTH_SHORT).show();
                }
                if (category.isEmpty()) {
                    missing_input = true;
                }

                if (missing_input) {
                    return;
                }

                Product product = new Product(0, barcode, productCode, productDescription, price, category);
                int res = dbHandler.updateProduct(product);

                if (res > 0) {
                    Toast.makeText(ViewAndUpdate.this, res + " Product(s) Updated Successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAndUpdate.this, MainActivity.class);
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