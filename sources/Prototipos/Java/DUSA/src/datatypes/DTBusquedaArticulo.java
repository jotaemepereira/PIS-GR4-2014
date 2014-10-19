package datatypes;

import java.math.BigDecimal;

public class DTBusquedaArticulo extends DTBusquedaArticuloSolr {
	private String tipoDeArticulo;
	private String controlDeVenta;
	private BigDecimal precioPublico;
	private BigDecimal precioDeVenta;
	private BigDecimal costoDeLista;
	private BigDecimal costoReal;
	private BigDecimal costoPonderado;
	
	public DTBusquedaArticulo(DTBusquedaArticuloSolr busqueda){
		super(busqueda.getAccionesTerapeuticas(), busqueda.getCodigoBarras(), busqueda.getDescripcion(), busqueda.getDroga(), busqueda.getIdArticulo(), busqueda.getMarca(), busqueda.getPresentacion());
	}
	
	public String getControlDeVenta() {
		return controlDeVenta;
	}
	public void setControlDeVenta(String controlDeVenta) {
		this.controlDeVenta = controlDeVenta;
	}
	public String getTipoDeArticulo() {
		return tipoDeArticulo;
	}
	public void setTipoDeArticulo(String tipoDeArticulo) {
		this.tipoDeArticulo = tipoDeArticulo;
	}
	public BigDecimal getPrecioDeVenta() {
		return precioDeVenta;
	}
	public void setPrecioDeVenta(BigDecimal precioDeVenta) {
		this.precioDeVenta = precioDeVenta;
	}
	public BigDecimal getPrecioPublico() {
		return precioPublico;
	}
	public void setPrecioPublico(BigDecimal precioPublico) {
		this.precioPublico = precioPublico;
	}
	public BigDecimal getCostoDeLista() {
		return costoDeLista;
	}
	public void setCostoDeLista(BigDecimal costoDeLista) {
		this.costoDeLista = costoDeLista;
	}
	public BigDecimal getCostoReal() {
		return costoReal;
	}
	public void setCostoReal(BigDecimal costoReal) {
		this.costoReal = costoReal;
	}
	public BigDecimal getCostoPonderado() {
		return costoPonderado;
	}
	public void setCostoPonderado(BigDecimal costoPonderado) {
		this.costoPonderado = costoPonderado;
	}
}
