package Util;

import java.util.Comparator;

import datatypes.DTLineaPedido;

public class DTLineaPedidoComparador implements Comparator<DTLineaPedido> {

	/**
	 * Ordena de forma descendente por cantidad
	 */	
	@Override
	public int compare(DTLineaPedido arg0, DTLineaPedido arg1) {
		// TODO Auto-generated method stub
		if (arg0.getCantidad() > arg1.getCantidad())
			return -1;
		else if (arg0.getCantidad() < arg1.getCantidad())
			return 1;
		else
			return 0;
	}

}
