package model;

import java.math.BigDecimal;
/**
 * 
 * @author santiago
 *
 */
public class Cambio {

	long idArticulo;
	String descripcion;
	boolean dadoDeBaja;
	boolean bajoElPrecio;
	BigDecimal precioAntiguo;
	BigDecimal precioAtual;
	
	public Cambio(Articulo art, Articulo artAnt, boolean bajaPrecio ,boolean dadoDeBaja){
	
		this.idArticulo = artAnt.getIdArticulo();
		this.descripcion = art.getDescripcion();
		this.dadoDeBaja = dadoDeBaja;
		this.bajoElPrecio = bajaPrecio;
		this.precioAntiguo = artAnt.getPrecioUnitario();
		this.precioAtual= art.getPrecioUnitario().setScale(2, BigDecimal.ROUND_HALF_UP);;
	
	}
	
	@Override 
	public String toString() {
		String result = new String();
		result += "El articulo con ID: " + this.idArticulo + "  ";
		result += "Descrpcion: " + this.descripcion + "  ";
		if (this.dadoDeBaja){
			result += "Fue dado de baja ";
			if (this.bajoElPrecio){
				result += "y su precio disminuyó, siendo el precio anterio: ";
				result += this.precioAntiguo.toString();
				result += " y su precio actual: ";
				result += this.precioAtual.toString();
			}
		}else {
			result += "disminuyó en su precio, siendo el precio anterio: ";
			result += this.precioAntiguo.toString();
			result += " y su precio actual: ";
			result += this.precioAtual.toString();
		}

		return result;

	}
}
