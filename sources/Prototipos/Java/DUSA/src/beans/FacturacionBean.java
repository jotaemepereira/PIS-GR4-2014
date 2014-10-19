package beans;

import interfaces.IFacturacion;

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
							"Error al cargar la p√°gina.", ""));
		}
	}

	public void facturar(Venta v) {
		try {
			// List<String> messages = chequearRecetas();
			// if (messages.size() <= 0) {
			IFacturacion ifact = FabricaLogica.getIFacturacion();
			ifact.facturarVenta(v.getVentaId());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se facturÛ correctamente.", ""));
			ventas = ifact.listarVentasPendientes();
			// } else {
			// for (String s : messages) {
			// FacesContext.getCurrentInstance().addMessage(null,new
			// FacesMessage(FacesMessage.SEVERITY_ERROR, s, ""));
			// }
			// }
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al facturar.", ""));
		}
	}
	
	public void cancelar(Venta v) {
		try {
			IFacturacion ifact = FabricaLogica.getIFacturacion();
			ifact.cancelarVenta(v.getVentaId());

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

	// private List<String> chequearRecetas() {
	// List<String> errores = new ArrayList<String>();
	// for (LineaVenta lv : ventaSeleccionada.getLineas()) {
	// if (lv.getArticulo().isEsEstupefaciente() && !lv.isRecetaNaranja()) {
	// errores.add(lv.getArticulo().getDescripcion()
	// + " requiere receta naranja.");
	// }
	// if (lv.getArticulo().isEsPsicofarmaco() && lv.isRecetaVerde()) {
	// errores.add(lv.getArticulo().getDescripcion()
	// + " requiere receta verde.");
	// }
	// }
	// return errores;
	// }

	public void cargarFactura(Venta v) {
		ventaSeleccionada = v;
	}

	public String parseFechaVenta(Date d) {
		long diff = (new Date()).getTime() - d.getTime();
		return "Hace " + (diff / (60 * 1000)) + " minutos";
	}

	public BigDecimal calculcarSubtotal(LineaVenta lv) {
		return (lv.getPrecio().multiply(new BigDecimal(lv.getCantidad())))
				.multiply(lv.getDescuento());
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
