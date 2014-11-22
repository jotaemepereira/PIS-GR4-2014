package Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfiguracionUtil {
	
	
	public static Properties generarPedidoConfiguracion() {
		
		Properties prop = new Properties();
		InputStream input = null;
		OutputStream output = null;
		
		try {
			
			try {
				
				input = new FileInputStream("generarPedido.properties");
				prop.load(input);
				
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
				//Si no existe, creo uno con valores por defecto.
				
				output = new FileOutputStream("generarPedido.properties");
				prop.setProperty("dias_a_predecir", "5");
				prop.setProperty("limite_articulos_mostrar", "150");
				
				prop.store(output, null);
				
				output.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prop;
	}

}
