package datatypes;

import java.io.Serializable;
import java.math.BigDecimal;

public class DTVenta implements Serializable {
	private int productId;
	private String presentacion;
	private String principioActivo;
	private String descripcion;
	private String laboratorio;
	private String concentracion;
	private String nombre;
	private String barcode;
	private BigDecimal precioVenta = new BigDecimal(0);
	private int cantidad = 1;
	private boolean recetaBlanca;
	private boolean recetaVerde;
	private boolean recetaNaranja;
	private boolean refrigerado;
	private BigDecimal descuento = new BigDecimal(0);
	private BigDecimal descuento1 = new BigDecimal(0);
	private BigDecimal descuento2 = new BigDecimal(0);
	private BigDecimal descuento3 = new BigDecimal(0);
	private String CodigoBarras;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getBarcode() {
		return barcode;
	}

	public boolean isRecetaBlanca() {
		return recetaBlanca;
	}

	public void setRecetaBlanca(boolean recetaBlanca) {
		this.recetaBlanca = recetaBlanca;
	}

	public boolean isRecetaVerde() {
		return recetaVerde;
	}

	public void setRecetaVerde(boolean recetaVerde) {
		this.recetaVerde = recetaVerde;
	}

	public boolean isRecetaNaranja() {
		return recetaNaranja;
	}

	public void setRecetaNaranja(boolean recetaNaranja) {
		this.recetaNaranja = recetaNaranja;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public String getPrincipioActivo() {
		return principioActivo;
	}

	public void setPrincipioActivo(String principioActivo) {
		this.principioActivo = principioActivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getCodigoBarras() {
		return CodigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		CodigoBarras = codigoBarras;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getDescuento2() {
		return descuento2;
	}

	public void setDescuento2(BigDecimal descuento2) {
		this.descuento2 = descuento2;
	}

	public BigDecimal getDescuento1() {
		return descuento1;
	}

	public void setDescuento1(BigDecimal descuento1) {
		this.descuento1 = descuento1;
	}

	public BigDecimal getDescuento3() {
		return descuento3;
	}

	public void setDescuento3(BigDecimal descuento3) {
		this.descuento3 = descuento3;
	}

	public String getConcentracion() {
		return concentracion;
	}

	public void setConcentracion(String concentracion) {
		this.concentracion = concentracion;
	}

	public boolean isRefrigerado() {
		return refrigerado;
	}

	public void setRefrigerado(boolean refrigerado) {
		this.refrigerado = refrigerado;
	}
}
