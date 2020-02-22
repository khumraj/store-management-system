package com.example.kiranastoremanagement.Model;

public class LayoutModel {
    public enum layoutType {
        PRODUCT_LIST, SALE_PRODUCT;
    }
    private layoutType type;

    public LayoutModel(layoutType type) {
        this.type = type;
    }

    public layoutType getType() {
        return type;
    }

    public void setType(layoutType type) {
        this.type = type;
    }
}
