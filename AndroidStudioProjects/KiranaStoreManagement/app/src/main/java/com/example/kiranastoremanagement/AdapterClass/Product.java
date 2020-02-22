package com.example.kiranastoremanagement.AdapterClass;

public class Product {
    public enum layoutType {
        PRODUCT_LIST, SALE_PRODUCT;
    }
    private layoutType type;
    private   int id;
    private  String product_image;
    private  String product_name;
    private  int available_stock;
    private  int min_stock;
    private  String measurement_unit;
    private  String purchase_cost;
    private  String sale_cost;

    public Product(int id, String product_image, String product_name, int available_stock, int min_stock, String measurement_unit, String purchase_cost, String sale_cost, layoutType type) {
        this.id = id;
        this.product_image = product_image;
        this.product_name = product_name;
        this.available_stock = available_stock;
        this.min_stock = min_stock;
        this.measurement_unit = measurement_unit;
        this.purchase_cost = purchase_cost;
        this.sale_cost = sale_cost;
        this.type=type;

    }

    public void changeName(String productName) {
        product_name = productName;
    }

    public layoutType getType() {
        return type;
    }

    public void setType(layoutType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getAvailable_stock() {
        return available_stock;
    }

    public int getMin_stock() {
        return min_stock;
    }

    public String getMeasurement_unit() {
        return measurement_unit;
    }

    public String getPurchase_cost() {
        return purchase_cost;
    }

    public String getSale_cost() {
        return sale_cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setAvailable_stock(int available_stock) {
        this.available_stock = available_stock;
    }

    public void setMin_stock(int min_stock) {
        this.min_stock = min_stock;
    }

    public void setMeasurement_unit(String measurement_unit) {
        this.measurement_unit = measurement_unit;
    }

    public void setPurchase_cost(String purchase_cost) {
        this.purchase_cost = purchase_cost;
    }

    public void setSale_cost(String sale_cost) {
        this.sale_cost = sale_cost;
    }
}
