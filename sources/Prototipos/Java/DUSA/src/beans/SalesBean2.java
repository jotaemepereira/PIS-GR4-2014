package beans;

import interfaces.IBIlling;
import interfaces.IStock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import controladores.Fabric;
import datatypes.DTProduct;
import datatypes.DTSaleDetail;

@ManagedBean
@ViewScoped
public class SalesBean2 implements Serializable {

	private List<DTSaleDetail> saleLines = new ArrayList<DTSaleDetail>();
	private DTSaleDetail dtSaleDetail = new DTSaleDetail();
	private String inDescription = "-";
	private String inPresentation = "-";
	private BigDecimal inDiscount = new BigDecimal(0);
	private BigDecimal subtotal = new BigDecimal(0);
	private BigDecimal total = new BigDecimal(0);
	private String strSubtotal = "0";
	private String strTotal = "0";

	public void barcodeChanged(DTSaleDetail line) {

		if (line.getBarcode() != null && !line.getBarcode().isEmpty()) {
			// Busco los datos del código de barras
			IStock is = Fabric.getIStock();
			DTProduct dt = is.getProduct(line.getBarcode());
			if (dt != null) {
				line.setSalePrice(dt.getSalePrice());
				line.setProductId(dt.getProductId());
				// inDescription = dt.getDescription();
				calculateTotals();
				// Agrego una nueva linea vacia
				addLine();
			}
		}
	}

	@PostConstruct
	public void init() {
		DTSaleDetail dt = new DTSaleDetail();
		saleLines.add(dt);
	}

	public void addLine() {
		if (saleLines.size() == 0 || ( saleLines.get(saleLines.size() - 1).getBarcode() != null
				&& !saleLines.get(saleLines.size() - 1).getBarcode().isEmpty())) {
			saleLines.add(dtSaleDetail);
			dtSaleDetail = new DTSaleDetail();
		}
	}

	public void calculateTotals() {
		subtotal = new BigDecimal(0);
		total = new BigDecimal(0);
		for (DTSaleDetail dt : saleLines) {
			subtotal = subtotal.add((dt.getSalePrice().subtract(
					dt.getSalePrice().multiply(dt.getDiscount())
							.divide(new BigDecimal(100)))
					.multiply(new BigDecimal(dt.getQuantity()))));
		}
		total = subtotal.multiply(new BigDecimal(1.22));
		DecimalFormat df = new DecimalFormat("############.##");
		strSubtotal = df.format(subtotal);
		strTotal = df.format(total);
	}

	public void insertSale() {
		try {
			IBIlling ib = Fabric.getIBilling();
			ib.newSale(1, 1, saleLines, 'A');
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se ha ingresado la venta correctamente.", ""));
			
			saleLines = new ArrayList<DTSaleDetail>();
			addLine();
			strSubtotal = "";
			strTotal = "";
		} catch (Exception e) {
		}
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

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getStrSubtotal() {
		return strSubtotal;
	}

	public void setStrSubtotal(String strSubtotal) {
		this.strSubtotal = strSubtotal;
	}

	public String getStrTotal() {
		return strTotal;
	}

	public void setStrTotal(String strTotal) {
		this.strTotal = strTotal;
	}

}
