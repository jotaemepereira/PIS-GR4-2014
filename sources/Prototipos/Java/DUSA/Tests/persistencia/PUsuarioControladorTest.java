package persistencia;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import model.Enumerados.casoDeUso;
import model.Operacion;
import model.Rol;
import model.Usuario;

import org.junit.Test;
import org.primefaces.validate.bean.AssertTrueClientValidationConstraint;

import controladores.Excepciones;
import controladores.FabricaPersistencia;
import controladores.FabricaSistema;

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
			assertTrue(cus[++i].toString().compareTo(it.next().getNombre())==0);
			assertTrue(usr.tienePermiso(cus[i]));
		}
		/**
		 * Prueba el caso de un usuario con 2 roles vendedor y cajero 
		 * 
		 */
		 username = "CajVend";
		 pass = "1234cajvend";
		 usr = FabricaPersistencia.getInstanciaUsuaruiPersistencia().getUsuario(username, pass);

//		 Iterator<Rol> iter = usr.getRoles().iterator();
//		 while(iter.hasNext()){
//			it =iter.next().getOperaciones().iterator();
//			while (it.hasNext()){
//				System.out.prinln(it.next().getNombre());
//			}
//			
//		 }
		 /**
		  * Los casos de uso  9,11,12,20,21y32 son los que 
		  * solo el admin puede hacer 
		  */
		 
		 for  (i=1; i<26; i++)
			 if (i==9|| i==11 || i==12 || i==20 || i==21 || i==23)
				 assertTrue(!usr.tienePermiso(cus[i]));
			 else
				 assertTrue(usr.tienePermiso(cus[i]));		 
	}
		

}
