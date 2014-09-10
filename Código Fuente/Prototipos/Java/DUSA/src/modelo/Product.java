package modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
	private int productId;
	private String description;
	private int supplierId;
	private String lineId;
	private String key1;
	private String key2;
	private String key3;
	private boolean isPsychotropic;
	private boolean isNarcotic;
	private char saleCode;
	private char authorizationType;
	private char productType;
	private BigDecimal unitPrice;
	private BigDecimal salePrice;
	private BigDecimal listCost;
	private BigDecimal offerCost;
	private BigDecimal lastCost;
	private BigDecimal avgCost;
	private int taxType;
	private String barcode;
	private Date lastPriceDate;
	private int stock;
	private Date lastModified;
	private boolean status;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public boolean isPsychotropic() {
		return isPsychotropic;
	}

	public void setPsychotropic(boolean isPsychotropic) {
		this.isPsychotropic = isPsychotropic;
	}

	public boolean isNarcotic() {
		return isNarcotic;
	}

	public void setNarcotic(boolean isNarcotic) {
		this.isNarcotic = isNarcotic;
	}

	public char getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(char saleCode) {
		this.saleCode = saleCode;
	}

	public char getAuthorizationType() {
		return authorizationType;
	}

	public void setAuthorizationType(char authorizationType) {
		this.authorizationType = authorizationType;
	}
	
	public char getProductType() {
		return productType;
	}

	public void setProductType(char productType) {
		this.productType = productType;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getListCost() {
		return listCost;
	}

	public void setListCost(BigDecimal listCost) {
		this.listCost = listCost;
	}

	public BigDecimal getOfferCost() {
		return offerCost;
	}

	public void setOfferCost(BigDecimal offerCost) {
		this.offerCost = offerCost;
	}

	public BigDecimal getLastCost() {
		return lastCost;
	}

	public void setLastCost(BigDecimal lastCost) {
		this.lastCost = lastCost;
	}

	public BigDecimal getAvgCost() {
		return avgCost;
	}

	public void setAvgCost(BigDecimal avgCost) {
		this.avgCost = avgCost;
	}

	public int getTaxType() {
		return taxType;
	}

	public void setTaxType(int taxType) {
		this.taxType = taxType;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Date getLastPriceDate() {
		return lastPriceDate;
	}

	public void setLastPriceDate(Date lastPriceDate) {
		this.lastPriceDate = lastPriceDate;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
