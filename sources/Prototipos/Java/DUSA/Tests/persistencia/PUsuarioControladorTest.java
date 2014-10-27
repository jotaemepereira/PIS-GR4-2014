package persistencia;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import model.Actividad;
import model.Enumerados.casoDeUso;
import model.Operacion;
import model.Usuario;

import org.junit.Test;

import controladores.Excepciones;
import controladores.FabricaPersistencia;

public class PUsuarioControladorTest {

	@Test
	public void test() throws Excepciones {

		String username = "Admin";
		String pass = "1234admin";
		Usuario usr = FabricaPersistencia.getInstanciaUsuaruiPersistencia().getUsuario(username, pass);
		ArrayList <Operacion> ops=usr.getRoles().get(0).getOperaciones();
		Iterator <Operacion> it= ops.iterator();
		casoDeUso[] cus = casoDeUso.values();
		
		int i=0;
		/**
		 * Pruebo que el admin tenga asociada las 25 operaciones definidas en el enum casoDeUso 
		 * y por lo tanto que tenga el permiso 
		 */
		//arranco a iterar en el 1 porque alguien le agrego el caso de uso "ventalibre"
		while (it.hasNext()){
			assertTrue(cus[i].toString().compareTo(it.next().getNombre())==0);
			assertTrue(usr.tienePermiso(cus[i]));
			i++;
		}
		/**
		 * Prueba el caso de un usuario con 2 roles vendedor y cajero 
		 * 
		 */
		 username = "CajVend";
		 pass = "1234cajvend";
		 usr = FabricaPersistencia.getInstanciaUsuaruiPersistencia().getUsuario(username, pass);

		 /**
		  * Los casos de uso  8,10,11,19,20 y 22 son los que 
		  * solo el admin puede hacer 
		  */
		 
		 for  (i=0; i<25; i++)
			 if (i==8|| i==10 || i==11 || i==19 || i==20 || i==22)
				 assertTrue(!usr.tienePermiso(cus[i]));
			 else
				 assertTrue(usr.tienePermiso(cus[i]));		 
	
		 for  (i=0; i<25; i++){
			 if (i!=8|| i!=10 || i!=11 || i!=19 || i!=20 || i!=22){
				 
				 Operacion op = new Operacion();
				 op.setId(i+1);
				 op.setNombre(cus[i].toString());
				 Actividad act = new Actividad(op,usr.getUsuarioId());
				 FabricaPersistencia.getInstanciaUsuaruiPersistencia().registrarActividad(act);
			 }

		
		 }

		 
	}
	

}
