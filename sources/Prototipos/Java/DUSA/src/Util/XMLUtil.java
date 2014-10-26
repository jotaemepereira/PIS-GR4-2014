package Util;

import java.io.File;
import java.io.OutputStream;

import model.Venta;

import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLUtil {

	public static File jaxbObjectToXML(Venta v) throws Exception {

		try {
			JAXBContext context = JAXBContext.newInstance(Venta.class);
			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			// m.marshal(v, System.out);

			// Write to File
			String path = FacesContext.getCurrentInstance()
            .getExternalContext().getInitParameter("PathFacturacion");
			File f = new File(path + v.getVentaId() + ".xml");
			f.createNewFile();
			m.marshal(v, f);
			return f;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
}
