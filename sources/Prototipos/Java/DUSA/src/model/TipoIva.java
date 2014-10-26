package model;

import java.math.BigDecimal;

public class TipoIva {
	private int tipoIVA;
	private String descripcion;
	private int tipoTasa;
	private int indicadorFacturacion;
	private BigDecimal valorIVA;
	private BigDecimal valorTributo;
	private BigDecimal resguardoIVA;
	private BigDecimal resguardoIRAE;
	private boolean status;
	
	public int getTipoIVA() {
		return tipoIVA;
	}
	public void setTipoIVA(int tipoIVA) {
		this.tipoIVA = tipoIVA;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getTipoTasa() {
		return tipoTasa;
	}
	public void setTipoTasa(int tipoTasa) {
		this.tipoTasa = tipoTasa;
	}
	public int getIndicadorFacturacion() {
		return indicadorFacturacion;
	}
	public void setIndicadorFacturacion(int indicadorFacturacion) {
		this.indicadorFacturacion = indicadorFacturacion;
	}
	public BigDecimal getValorIVA() {
		return valorIVA;
	}
	public void setValorIVA(BigDecimal valorIVA) {
		this.valorIVA = valorIVA;
	}
	public BigDecimal getValorTributo() {
		return valorTributo;
	}
	public void setValorTributo(BigDecimal valorTributo) {
		this.valorTributo = valorTributo;
	}
	public BigDecimal getResguardoIVA() {
		return resguardoIVA;
	}
	public void setResguardoIVA(BigDecimal resguardoIVA) {
		this.resguardoIVA = resguardoIVA;
	}
	public BigDecimal getResguardoIRAE() {
		return resguardoIRAE;
	}
	public void setResguardoIRAE(BigDecimal resguardoIRAE) {
		this.resguardoIRAE = resguardoIRAE;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
