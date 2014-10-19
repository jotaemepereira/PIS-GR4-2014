package persistencia;

import static org.junit.Assert.*;
import model.Actividad;
import model.Operacion;
import model.Rol;
import model.Usuario;

import org.apache.tomcat.jni.User;
import org.junit.Test;

import controladores.Excepciones;
import controladores.FabricaPersistencia;

/**
 * 
 * @author santiago
 *
 */
public class PUsuarioControladorTest {

	@Test
	public void test() {
		Usuario usr = null;
		try {
			usr = (Usuario) FabricaPersistencia.getInstanciaUsuaruiPersistencia().getUsuario(1,"1234admin");
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("problemas al levantar el usuario");

		}
		
		Rol rol = usr.getRoles().get(0);
		Operacion op = rol.getOperaciones().get(4);
		Actividad act = new Actividad(op,usr.getUsuarioId());
		try{
			FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
		
		} catch (Excepciones e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("problemas al levantar el usuario");

		}

	}

}
