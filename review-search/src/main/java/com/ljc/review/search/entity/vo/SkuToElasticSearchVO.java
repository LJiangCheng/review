package com.ljc.review.search.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SkuToElasticSearchVO {

    private String brandName;
    private String categoryName;
    private String makerModel;
    private BigDecimal price;
    private String productName;
    private String property;
    private String unit;
    private String code;
    private String makerModelLiteral;
    private String image;
    private String url;
    private Integer source;
    private List<Double> tags = new ArrayList<>();

    @JSONField(serialize = false)
    private Integer brandId;
    @JSONField(serialize = false)
    private Integer categoryId;

    public SkuToElasticSearchVO() {
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public List<Double> getTags() {
        return tags;
    }

    public void setTags(List<Double> tags) {
        this.tags = tags;
    }

    public String getMakerModelLiteral() {
        return makerModelLiteral;
    }

    public void setMakerModelLiteral(String makerModelLiteral) {
        this.makerModelLiteral = makerModelLiteral;
    }

    public String getUnicode() {
        return code;
    }

    public void setUnicode(String code) {
        this.code = code;
    }

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
        this.makerModelLiteral = makerModel;
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
