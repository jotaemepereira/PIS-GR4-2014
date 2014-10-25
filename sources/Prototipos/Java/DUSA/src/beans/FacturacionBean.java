package beans;

import interfaces.IFacturacion;
import interfaces.ISistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import controladores.FabricaLogica;
import controladores.FabricaServicios;
import controladores.FabricaSistema;
import model.LineaVenta;
import model.Venta;

@ManagedBean
@ViewScoped
public class FacturacionBean implements Serializable {

	private List<Venta> ventas;
	private Venta ventaSeleccionada;

	public FacturacionBean() {
		try {
			IFacturacion ifact = FabricaLogica.getIFacturacion();
			ventas = ifact.listarVentasPendientes();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al cargar la pÃ¡gina.", ""));
		}
	}

	public void facturar() {
		try {
			IFacturacion ifact = FabricaLogica.getIFacturacion();
			ifact.facturarVenta(ventaSeleccionada.getVentaId());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se facturó correctamente.", ""));
			ventas = ifact.listarVentasPendientes();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al facturar.", ""));
		}
	}
	
	public void cancelar() {
		try {
			IFacturacion ifact = FabricaLogica.getIFacturacion();
			ifact.cancelarVenta(ventaSeleccionada.getVentaId());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se cancelada correctamente.", ""));
			ventas = ifact.listarVentasPendientes();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al facturar.", ""));
		}
	}


	public void cargarFactura(Venta v) {
		ventaSeleccionada = v;
	}

	public String parseFechaVenta(Venta v) {
		long diff = (new Date()).getTime() - v.getFechaVenta().getTime();
		return "Hace " + (diff / (60 * 1000)) + " minutos";
	}

	public BigDecimal calculcarSubtotal(LineaVenta lv) {
		if (lv.getDescuento().compareTo(new BigDecimal(0))== 0){
			return  (lv.getPrecio().multiply(new BigDecimal(lv.getCantidad())));
		}
		
		return (lv.getPrecio().multiply(new BigDecimal(lv.getCantidad())))
				.multiply(((new BigDecimal(100)).subtract(lv.getDescuento())).divide(new BigDecimal(100)));
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

}
