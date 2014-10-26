package beans;

import interfaces.IFacturacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Enumerados;
import model.LineaVenta;
import model.Venta;
import controladores.Excepciones;
import controladores.FabricaLogica;

@ManagedBean
@ViewScoped
public class FacturacionBean implements Serializable {

	private List<Venta> ventas;
	private Venta ventaSeleccionada;
	private boolean[] lineasCheck;
	private boolean facturacionControlada = false;
	
	public FacturacionBean() {
		try {
			facturacionControlada = (Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext()
					.getInitParameter("MODO_FACTURACION")) == Enumerados.modoFacturacion.controlada);
			
			IFacturacion ifact = FabricaLogica.getIFacturacion();
			ventas = ifact.listarVentasPendientes();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Excepciones.MENSAJE_ERROR_SISTEMA, ""));
		}
	}

	public void facturar() {
		try {
			boolean allCheck = true;
			if (facturacionControlada) { 
				for (int i = 0; i < ventaSeleccionada.getCantidadLineas(); i++) {
					if (!lineasCheck[i]) {
						allCheck = false;
					}
				}
			}

			if (allCheck) {

				IFacturacion ifact = FabricaLogica.getIFacturacion();
				ifact.facturarVenta(ventaSeleccionada.getVentaId());

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								Excepciones.MENSAJE_FACTURADA_OK, ""));
				ventas = ifact.listarVentasPendientes();
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Excepciones.MENSAJE_NO_CORROBORADO_OK, ""));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Excepciones.MENSAJE_ERROR_SISTEMA, ""));
		}
	}

	public void cancelar() {
		try {
			IFacturacion ifact = FabricaLogica.getIFacturacion();
			ifact.cancelarVenta(ventaSeleccionada.getVentaId());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Excepciones.MENSAJE_CANCELADA_OK, ""));
			ventas = ifact.listarVentasPendientes();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							Excepciones.MENSAJE_ERROR_SISTEMA, ""));
		}
	}

	public void cargarFactura(Venta v) {
		ventaSeleccionada = v;
		lineasCheck = new boolean[v.getCantidadLineas()];
		for (int i = 0; i < v.getCantidadLineas(); i++){
			lineasCheck[i] = false;
		}
	}

	public String parseFechaVenta(Venta v) {
		long diff = (new Date()).getTime() - v.getFechaVenta().getTime();
		return "Hace " + (diff / (60 * 1000)) + " minutos";
	}

	public BigDecimal calculcarSubtotal(LineaVenta lv) {
		if (lv.getDescuento().compareTo(new BigDecimal(0)) == 0) {
			return (lv.getPrecio().multiply(new BigDecimal(lv.getCantidad())));
		}

		return (lv.getPrecio().multiply(new BigDecimal(lv.getCantidad())))
				.multiply(((new BigDecimal(100)).subtract(lv.getDescuento()))
						.divide(new BigDecimal(100)));
	}

	public void toggleCheck(int index) {
		lineasCheck[index] = !lineasCheck[index];
	}

	public List<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Venta getVentaSeleccionada() {
		return ventaSeleccionada;
	}

	public void setVentaSeleccionada(Venta ventaSeleccionada) {
		this.ventaSeleccionada = ventaSeleccionada;
	}

	public boolean isFacturacionControlada() {
		return facturacionControlada;
	}

	public void setFacturacionControlada(boolean facturacionControlada) {
		this.facturacionControlada = facturacionControlada;
	}

	public boolean[] getLineasCheck() {
		return lineasCheck;
	}

	public void setLineasCheck(boolean[] lineasCheck) {
		this.lineasCheck = lineasCheck;
	}

}
