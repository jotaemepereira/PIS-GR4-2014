package controladores;

import static org.junit.Assert.*;

import org.junit.Test;

public class StockControladorTest {

	@Test
	public void test() {
		try {
			FabricaSistema.getISistema().actualizarStock();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
