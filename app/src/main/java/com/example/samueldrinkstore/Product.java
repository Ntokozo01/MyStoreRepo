package com.example.samueldrinkstore;

/**
 *
 */
public class Product {
    private int id;
    private  String barcode;
    private String productCode;
    private String productDescription;
    private double price;
    private String category;

    public Product(){

    }

    public Product(int id, String barcode, String productCode, String productDescription, double price, String category) {
        this.id = id;
        this.barcode = barcode;
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
