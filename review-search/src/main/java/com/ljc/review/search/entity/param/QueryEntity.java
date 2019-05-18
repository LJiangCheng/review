package com.ljc.review.search.entity.param;

public class QueryEntity {

    private String productName;
    private String makerModel;
    private String brandName;
    private String property;
    private String unit;
    private String categoryName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMakerModel() {
        return makerModel;
    }

    public void setMakerModel(String makerModel) {
        this.makerModel = makerModel;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
