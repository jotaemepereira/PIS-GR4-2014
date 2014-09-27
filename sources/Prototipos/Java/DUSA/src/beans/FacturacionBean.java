package beans;

import interfaces.IFacturacion;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import controladores.FabricaLogica;
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
							"Error al cargar la página.", ""));
		}
	}

	public void facturar() {
		try {
			IFacturacion ifact = FabricaLogica.getIFacturacion();
			ifact.facturarVenta(ventaSeleccionada.getVentaId());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al facturar.", ""));
		}
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
