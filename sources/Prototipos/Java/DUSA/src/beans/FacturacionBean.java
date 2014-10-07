package beans;

import interfaces.IFacturacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

	public void facturar(Venta v) {
		try {
			// IFacturacion ifact = FabricaLogica.getIFacturacion();
			// ifact.facturarVenta(v.getVentaId());

			ventas.remove(v);

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se facturó correctamente.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al facturar.", ""));
		}
	}
	
	public void cargarFactura(Venta v){
		ventaSeleccionada = v;
	}

	public String parseFechaVenta(Date d) {
		// long diff = (new Date()).getTime() - d.getTime();
		// return "" + (diff / (60 * 1000));
		Random r = new Random();
		int a = Math.abs(r.nextInt() % 15);
		if (a == 0) {
			a = 1;
		}
		return "Hace " + a + " minutos";
	}

	public String obtenerNombre(BigDecimal precio) {
		// sacar
		if (precio.intValue() == 25){
			return "Producto1";
		} else if (precio.intValue() == 15) {
			return "Producto2";
		} else if (precio.intValue() == 45) {
			return "Producto3";
		} else {
			return "Producto4";
		}
	}

	public BigDecimal calculcarSubtotal(int cantidad, BigDecimal precio){
		return precio.multiply(new BigDecimal(cantidad));
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
