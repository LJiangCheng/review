package com.ljc.review.search.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;
public class PcProductSku implements Serializable{
	private Integer id;
	private Integer productSkuid;
	private Integer productSpuid;
	private Integer primarySkuid;
	private Integer isDerivative;
	private Integer isValid;
	private String skuIntroduction;
	private String imageFile;
	private String pdfFile;
	private String videoFile;
	private String url;
	private String productAllinfo;
	private Integer viewFirst;
	private Long searchWeight;
	private String searchKeyword;
	private Integer isDeleted;
	private Integer isVisible;
	private Integer isShelved;
	private Date createTime;
	private Date modifyTime;
	private Integer totalHits;
	private String makerPlace;
	private Long deliveryPeriod;
	private Integer totalSales;
	private Integer enableStock;
	private Integer lockedStock;
	private String unicode;
	private String makerCode;
	private Long toolmallPrice;
	private Long marketPrice;
	private Long basePrice;
	private Long taxRate;
	private Long length;
	private Long width;
	private Long height;
	private Long weight;
	private Long largeType;
	private String unitName;
	private Date validityDays;

	public PcProductSku(){
	}
	public PcProductSku(
		Integer id
	){
		this.id = id;
	}
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setProductSkuid(Integer value) {
		this.productSkuid = value;
	}
	
	public Integer getProductSkuid() {
		return this.productSkuid;
	}
	public void setProductSpuid(Integer value) {
		this.productSpuid = value;
	}
	
	public Integer getProductSpuid() {
		return this.productSpuid;
	}
	public void setPrimarySkuid(Integer value) {
		this.primarySkuid = value;
	}
	
	public Integer getPrimarySkuid() {
		return this.primarySkuid;
	}
	public void setIsDerivative(Integer value) {
		this.isDerivative = value;
	}
	
	public Integer getIsDerivative() {
		return this.isDerivative;
	}
	public void setIsValid(Integer value) {
		this.isValid = value;
	}
	
	public Integer getIsValid() {
		return this.isValid;
	}
	public void setSkuIntroduction(String value) {
		this.skuIntroduction = value;
	}
	
	public String getSkuIntroduction() {
		return this.skuIntroduction;
	}
	public void setImageFile(String value) {
		this.imageFile = value;
	}
	
	public String getImageFile() {
		return this.imageFile;
	}
	public void setPdfFile(String value) {
		this.pdfFile = value;
	}
	
	public String getPdfFile() {
		return this.pdfFile;
	}
	public void setVideoFile(String value) {
		this.videoFile = value;
	}
	
	public String getVideoFile() {
		return this.videoFile;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setProductAllinfo(String value) {
		this.productAllinfo = value;
	}
	
	public String getProductAllinfo() {
		return this.productAllinfo;
	}
	public void setViewFirst(Integer value) {
		this.viewFirst = value;
	}
	
	public Integer getViewFirst() {
		return this.viewFirst;
	}
	public void setSearchWeight(Long value) {
		this.searchWeight = value;
	}
	
	public Long getSearchWeight() {
		return this.searchWeight;
	}
	public void setSearchKeyword(String value) {
		this.searchKeyword = value;
	}
	
	public String getSearchKeyword() {
		return this.searchKeyword;
	}
	public void setIsDeleted(Integer value) {
		this.isDeleted = value;
	}
	
	public Integer getIsDeleted() {
		return this.isDeleted;
	}
	public void setIsVisible(Integer value) {
		this.isVisible = value;
	}
	
	public Integer getIsVisible() {
		return this.isVisible;
	}
	public void setIsShelved(Integer value) {
		this.isShelved = value;
	}
	
	public Integer getIsShelved() {
		return this.isShelved;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setModifyTime(Date value) {
		this.modifyTime = value;
	}
	
	public Date getModifyTime() {
		return this.modifyTime;
	}
	public void setTotalHits(Integer value) {
		this.totalHits = value;
	}
	
	public Integer getTotalHits() {
		return this.totalHits;
	}
	public void setMakerPlace(String value) {
		this.makerPlace = value;
	}
	
	public String getMakerPlace() {
		return this.makerPlace;
	}
	public void setDeliveryPeriod(Long value) {
		this.deliveryPeriod = value;
	}
	
	public Long getDeliveryPeriod() {
		return this.deliveryPeriod;
	}
	public void setTotalSales(Integer value) {
		this.totalSales = value;
	}
	
	public Integer getTotalSales() {
		return this.totalSales;
	}
	public void setEnableStock(Integer value) {
		this.enableStock = value;
	}
	
	public Integer getEnableStock() {
		return this.enableStock;
	}
	public void setLockedStock(Integer value) {
		this.lockedStock = value;
	}
	
	public Integer getLockedStock() {
		return this.lockedStock;
	}
	public void setUnicode(String value) {
		this.unicode = value;
	}
	
	public String getUnicode() {
		return this.unicode;
	}
	public void setMakerCode(String value) {
		this.makerCode = value;
	}
	
	public String getMakerCode() {
		return this.makerCode;
	}
	public void setToolmallPrice(Long value) {
		this.toolmallPrice = value;
	}
	
	public Long getToolmallPrice() {
		return this.toolmallPrice;
	}
	public void setMarketPrice(Long value) {
		this.marketPrice = value;
	}
	
	public Long getMarketPrice() {
		return this.marketPrice;
	}
	public void setBasePrice(Long value) {
		this.basePrice = value;
	}
	
	public Long getBasePrice() {
		return this.basePrice;
	}
	public void setTaxRate(Long value) {
		this.taxRate = value;
	}
	
	public Long getTaxRate() {
		return this.taxRate;
	}
	public void setLength(Long value) {
		this.length = value;
	}
	
	public Long getLength() {
		return this.length;
	}
	public void setWidth(Long value) {
		this.width = value;
	}
	
	public Long getWidth() {
		return this.width;
	}
	public void setHeight(Long value) {
		this.height = value;
	}
	
	public Long getHeight() {
		return this.height;
	}
	public void setWeight(Long value) {
		this.weight = value;
	}
	
	public Long getWeight() {
		return this.weight;
	}
	public void setLargeType(Long value) {
		this.largeType = value;
	}
	
	public Long getLargeType() {
		return this.largeType;
	}
	public void setUnitName(String value) {
		this.unitName = value;
	}
	
	public String getUnitName() {
		return this.unitName;
	}
	public void setValidityDays(Date value) {
		this.validityDays = value;
	}
	
	public Date getValidityDays() {
		return this.validityDays;
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PcProductSku == false) return false;
		if(this == obj) return true;
		PcProductSku other = (PcProductSku)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}
