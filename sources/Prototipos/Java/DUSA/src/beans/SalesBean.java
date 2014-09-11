package beans;

import interfaces.IStock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import controladores.Fabric;
import datatypes.DTProduct;
import datatypes.DTSaleDetail;

@ManagedBean
@ViewScoped
public class SalesBean implements Serializable {

	private List<DTSaleDetail> saleLines = new ArrayList<DTSaleDetail>();
	private DTSaleDetail dtSaleDetail = new DTSaleDetail();
	private String inDescription;
	private String inPresentation;
	private BigDecimal inDiscount = new BigDecimal(0);

	public void barcodeChanged() {
		IStock is = Fabric.getIStock();
		DTProduct dt = is.getProduct(dtSaleDetail.getBarcode());
		dtSaleDetail.setSalePrice(dt.getSalePrice());
		inDescription = dt.getDescription();
	}

	@PostConstruct
	public void init() {

	}

	public void addLine() {
		saleLines.add(dtSaleDetail);
		dtSaleDetail = new DTSaleDetail();
		inDescription = "";
		inPresentation = "";
	}

	public void newRow() {
		saleLines.add(new DTSaleDetail());
	}

	public List<DTSaleDetail> getSaleLines() {
		return saleLines;
	}

	public void setSaleLines(List<DTSaleDetail> saleLines) {
		this.saleLines = saleLines;
	}

	public DTSaleDetail getDtSaleDetail() {
		return dtSaleDetail;
	}

	public void setDtSaleDetail(DTSaleDetail dtSaleDetail) {
		this.dtSaleDetail = dtSaleDetail;
	}

	public String getInDescription() {
		return inDescription;
	}

	public void setInDescription(String inDescription) {
		this.inDescription = inDescription;
	}

	public String getInPresentation() {
		return inPresentation;
	}

	public void setInPresentation(String inPresentation) {
		this.inPresentation = inPresentation;
	}

	public BigDecimal getInDiscount() {
		return inDiscount;
	}

	public void setInDiscount(BigDecimal inDiscount) {
		this.inDiscount = inDiscount;
	}

}
