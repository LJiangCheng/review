package com.ljc.review.search.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;
import java.io.Serializable;
import java.util.Date;
public class PcBusinessProduct implements Serializable{
	private Integer productSkuid;
	private String unicode;
	private String showName;
	private Integer isCompany;
	private String productIntroduction;
	private byte[] productFile;
	private Integer isDefault;
	private String tag;

	public PcBusinessProduct(){
	}
	public PcBusinessProduct(
		Integer productSkuid
	){
		this.productSkuid = productSkuid;
	}
	public void setProductSkuid(Integer value) {
		this.productSkuid = value;
	}
	
	public Integer getProductSkuid() {
		return this.productSkuid;
	}
	public void setUnicode(String value) {
		this.unicode = value;
	}
	
	public String getUnicode() {
		return this.unicode;
	}
	public void setShowName(String value) {
		this.showName = value;
	}
	
	public String getShowName() {
		return this.showName;
	}
	public void setIsCompany(Integer value) {
		this.isCompany = value;
	}
	
	public Integer getIsCompany() {
		return this.isCompany;
	}
	public void setProductIntroduction(String value) {
		this.productIntroduction = value;
	}
	
	public String getProductIntroduction() {
		return this.productIntroduction;
	}
	public void setProductFile(byte[] value) {
		this.productFile = value;
	}
	
	public byte[] getProductFile() {
		return this.productFile;
	}
	public void setIsDefault(Integer value) {
		this.isDefault = value;
	}
	
	public Integer getIsDefault() {
		return this.isDefault;
	}
	public void setTag(String value) {
		this.tag = value;
	}
	
	public String getTag() {
		return this.tag;
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getProductSkuid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PcBusinessProduct == false) return false;
		if(this == obj) return true;
		PcBusinessProduct other = (PcBusinessProduct)obj;
		return new EqualsBuilder()
			.append(getProductSkuid(),other.getProductSkuid())
			.isEquals();
	}
}
