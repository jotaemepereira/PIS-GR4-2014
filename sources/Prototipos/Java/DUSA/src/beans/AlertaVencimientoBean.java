package beans;

import interfaces.ISistema;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import controladores.Excepciones;
import datatypes.DTVencimiento;

@ManagedBean
@ViewScoped
public class AlertaVencimientoBean implements Serializable {

	private ISistema instanciaSistema;

	private Date fechaDesde;
	private Date fechaHasta;
	private List<DTVencimiento> articulos;
	private Date[] nuevosVencimientos;

	public void articulosQueSeVencenEnPeriodo() {
		try {
			if (fechaDesde == null || fechaHasta == null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Tiene que ingresar ambas fechas.",
								""));
			} else {
				if (fechaDesde.after(fechaHasta)) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"La fecha 'desde' tiene que ser anterior a 'hasta'.",
									""));
				} else {
					articulos = instanciaSistema.articulosQueSeVencenEnPeriodo(
							fechaDesde, fechaHasta);
					nuevosVencimientos = new Date[articulos.size()];
				}
			}
		} catch (Excepciones e) {
			e.printStackTrace();
			FacesContext contexto = FacesContext.getCurrentInstance();
			contexto.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void confirmar() {
		Map<Long, Date> cambios = new HashMap<Long, Date>();
		FacesContext contexto = FacesContext.getCurrentInstance();
		if (fechaDesde != null && fechaHasta != null) {
			if (nuevosVencimientos != null) {
				for (int i = 0; i < articulos.size(); i++) {
					if (nuevosVencimientos[i] != null) {
						cambios.put(articulos.get(i).getIdArticulo(),
								nuevosVencimientos[i]);
					}
				}
				if (cambios.size() > 0) {
					try {
						instanciaSistema
								.modificarVencimientosDeArticulos(cambios);

						// si todo bien aviso y vacio el formulario
						contexto.addMessage(null, new FacesMessage(
								FacesMessage.SEVERITY_INFO,
								Excepciones.MENSAJE_OK_ALERTA_VENCIMIENTO, ""));
						fechaDesde = null;
						fechaHasta = null;
						articulos = null;
						nuevosVencimientos = null;
					} catch (Excepciones e) {
						e.printStackTrace();
						contexto.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, e
										.getMessage(), ""));
					}
				} else {
					contexto.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"No se realizaron cambios", ""));
				}
			}
		}
	}
	
	public String parseFecha(Date fecha) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(fecha);
	}

	public AlertaVencimientoBean() {

	}

	public ISistema getInstanciaSistema() {
		return instanciaSistema;
	}

	public void setInstanciaSistema(ISistema instanciaSistema) {
		this.instanciaSistema = instanciaSistema;
	}

	public List<DTVencimiento> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<DTVencimiento> articulos) {
		this.articulos = articulos;
	}

	public Date[] getNuevosVencimientos() {
		return nuevosVencimientos;
	}

	public void setNuevosVencimientos(Date[] nuevosVencimientos) {
		this.nuevosVencimientos = nuevosVencimientos;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

}
