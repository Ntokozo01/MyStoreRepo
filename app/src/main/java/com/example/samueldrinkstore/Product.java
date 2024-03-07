package com.example.samueldrinkstore;

/**
 *
 */
public class Product {
    private int id;
    private String productCode;
    private String productDescription;
    private int size;
    private double price;
    private String category;

    public Product(){

    }

    public Product(int id, String productCode, String productDescription, int size, double price, String category) {
        this.id = id;
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
                ", productCode='" + productCode + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
