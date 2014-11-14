package controladores;

import interfaces.IFacturacion;
import interfaces.IFacturacionPersistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import model.Articulo;
import model.Cambio;
import model.Mail;
import model.PocasVentas;
import model.Venta;
import Util.XMLUtil;

public class FacturacionControlador implements IFacturacion {

	@Override
	public List<Venta> listarVentasPendientes() throws Excepciones {

		IFacturacionPersistencia ifp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();
		return ifp.listarVentasPendientes();
	}

	@Override
	public boolean facturarVenta(long ventaId) throws Excepciones {
		IFacturacionPersistencia ifp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();

		// Obtengo venta pendiente y la marco como facturada
		Venta venta = ifp.facturarVenta(ventaId);

		if (venta == null) {
			return false;
		}
		// Creo factura
		try {
			XMLUtil.jaxbObjectToXML(venta);
		} catch (Exception e) {

			e.printStackTrace();
			throw new Excepciones(Excepciones.MENSAJE_ERROR_IMPRESION_FACTURA,
					Excepciones.ERROR_SISTEMA);
		}
		return true;
	}
	
	@Override
	public void facturarVenta(Venta v) throws Excepciones {
		IFacturacionPersistencia ifp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();

		// Obtengo venta pendiente y la marco como facturada
		Venta venta = ifp.facturarVenta(v.getVentaId());

		if (venta != null) {
			// Creo factura
			try {
				
				XMLUtil.jaxbObjectToXML(venta);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new Excepciones(Excepciones.MENSAJE_ERROR_IMPRESION_FACTURA, Excepciones.ERROR_SISTEMA);
			}
		}
	}

	@Override
	public void cancelarVenta(long ventaId) throws Excepciones {

		IFacturacionPersistencia ifp = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();

		ifp.cancelarVenta(ventaId);
	}

	@Override
	public long registrarNuevaVenta(Venta v) throws Excepciones {
		// TODO Auto-generated method stub
		IFacturacionPersistencia pf = FabricaPersistencia
				.getInstanciaFacturacionPersistencia();
		return pf.persistirVenta(v);

	}

	@Override
	public void articulosConPocasVentasEnLosUltimosMeses(int mesesAtras)
			throws Exception {
		
		Mail m;

		IFacturacionPersistencia fp = FabricaPersistencia.getInstanciaFacturacionPersistencia();
		List<PocasVentas> pv = fp.articulosConPocasVentasEnLosUltimosMeses(mesesAtras);   
		
		OutputStream output;
		
		FileInputStream in;
		Properties prop = new Properties();
		String mailEmisor = null;
		String passEmisor = null;
		String receptores = null;
		try {
			in = new FileInputStream("ventaStock.properties");
			
			prop.load(in);
			mailEmisor = prop.getProperty("mailEmisor");
			passEmisor = prop.getProperty("passEmisor");
			receptores = prop.getProperty("mailReceptores");
			in.close();
		
		if (mailEmisor==null)
			mailEmisor = "dusapis";
		if (passEmisor ==null)
			passEmisor = "grupo4grupo4";
		if (receptores == null)
			receptores = "dusapis@gmail.com";
		
		
		output = new FileOutputStream("ventaStock.properties");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		String sDate = sdf.format(date);
		prop.setProperty("fechaUltimaActualizacion", sDate);
		prop.setProperty("mailEmisor",mailEmisor);
		prop.setProperty("passEmisor",passEmisor);
		prop.setProperty("mailReceptores",receptores);
		prop.store(output, null);


		if (pv.size()>0){
			m = new Mail();
			m.setAsunto("alerta por pocas ventas");   
			m.setContenidoPocasVentas(pv);
			m.setEmisor(mailEmisor, passEmisor);
			m.setDestinatarios(receptores);
			m.Enviar();
		}
		} catch ( IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}
