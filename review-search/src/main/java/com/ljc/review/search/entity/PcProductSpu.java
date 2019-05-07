package com.ljc.review.search.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
public class PcProductSpu implements Serializable{
	private Integer productSpuid;
	private Integer categoryId;
	private Integer relatedModule;
	private String name;
	private Integer brandId;
	private Integer merchantId;
	private String purpose;
	private String brief;
	private String memo;
	private String spuIntroduction;
	private String imageFile;
	private String pdfFile;
	private String videoFile;
	private String seoTitle;
	private String seoKeyword;
	private String seoDescription;

	public PcProductSpu(){
	}
	public PcProductSpu(
		Integer productSpuid
	){
		this.productSpuid = productSpuid;
	}
	public void setProductSpuid(Integer value) {
		this.productSpuid = value;
	}
	
	public Integer getProductSpuid() {
		return this.productSpuid;
	}
	public void setCategoryId(Integer value) {
		this.categoryId = value;
	}
	
	public Integer getCategoryId() {
		return this.categoryId;
	}
	public void setRelatedModule(Integer value) {
		this.relatedModule = value;
	}
	
	public Integer getRelatedModule() {
		return this.relatedModule;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setBrandId(Integer value) {
		this.brandId = value;
	}
	
	public Integer getBrandId() {
		return this.brandId;
	}
	public void setMerchantId(Integer value) {
		this.merchantId = value;
	}
	
	public Integer getMerchantId() {
		return this.merchantId;
	}
	public void setPurpose(String value) {
		this.purpose = value;
	}
	
	public String getPurpose() {
		return this.purpose;
	}
	public void setBrief(String value) {
		this.brief = value;
	}
	
	public String getBrief() {
		return this.brief;
	}
	public void setMemo(String value) {
		this.memo = value;
	}
	
	public String getMemo() {
		return this.memo;
	}
	public void setSpuIntroduction(String value) {
		this.spuIntroduction = value;
	}
	
	public String getSpuIntroduction() {
		return this.spuIntroduction;
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
	public void setSeoTitle(String value) {
		this.seoTitle = value;
	}
	
	public String getSeoTitle() {
		return this.seoTitle;
	}
	public void setSeoKeyword(String value) {
		this.seoKeyword = value;
	}
	
	public String getSeoKeyword() {
		return this.seoKeyword;
	}
	public void setSeoDescription(String value) {
		this.seoDescription = value;
	}
	
	public String getSeoDescription() {
		return this.seoDescription;
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getProductSpuid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PcProductSpu == false) return false;
		if(this == obj) return true;
		PcProductSpu other = (PcProductSpu)obj;
		return new EqualsBuilder()
			.append(getProductSpuid(),other.getProductSpuid())
			.isEquals();
	}
}
