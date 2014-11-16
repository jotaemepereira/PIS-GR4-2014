package datatypes;

import java.util.Date;

public class DTVencimiento {
	private long idArticulo;
	private long stock;
	private String descripcion;
	private Date vencimientoMasCercano;
	
	public long getIdArticulo() {
		return idArticulo;
	}
	public void setIdArticulo(long idArticulo) {
		this.idArticulo = idArticulo;
	}
	public long getStock() {
		return stock;
	}
	public void setStock(long stock) {
		this.stock = stock;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getVencimientoMasCercano() {
		return vencimientoMasCercano;
	}
	public void setVencimientoMasCercano(Date vencimientoMasCercano) {
		this.vencimientoMasCercano = vencimientoMasCercano;
	}
}
