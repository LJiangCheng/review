package com.ljc.review.search.entity.vo;

import java.math.BigDecimal;

public class SkuToElasticSearchVO {

    private String brandName;
    private String categoryName;
    private String makerModel;
    private BigDecimal price;
    private String productName;
    private String property;
    private String unit;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMakerModel() {
        return makerModel;
    }

    public void setMakerModel(String makerModel) {
        this.makerModel = makerModel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
}
