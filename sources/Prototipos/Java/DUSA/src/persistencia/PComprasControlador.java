package persistencia;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import Util.NamedParameterStatement;
import model.Orden;
import model.OrdenDetalle;
import controladores.Excepciones;
import datatypes.DTFormasVenta;
import datatypes.DTProveedor;
import datatypes.DTTiposDGI;
import interfaces.IComprasPersistencia;

public class PComprasControlador implements IComprasPersistencia {

	@Override
	public void ingresarFacturaCompra(Orden orden) throws Excepciones {
		NamedParameterStatement stmt = null;

		// Primero inserto la orden
		String 	query = "INSERT INTO orders (supplier_id, username, dgi_type_id, serial, order_number, order_date, payment_type, not_taxed_amount, taxed_minimum_net_amount, taxed_basic_net_amount, minimum_tax_total, basic_tax_total, total_amount, iva_withheld_amount, irae_withheld_amount, not_billable_amount, taxed_minimum_amount, taxed_basic_amount, total, detail_quantity) ";
				query += "VALUES (:supplier_id, :username, :dgi_type_id, :serial, :order_number, :order_date, :payment_type, :not_taxed_amount, :taxed_minimum_net_amount, :taxed_basic_net_amount, :minimum_tax_total, :basic_tax_total, :total_amount, :iva_withheld_amount, :irae_withheld_amount, :not_billable_amount, :taxed_minimum_amount, :taxed_basic_amount, :total, :detail_quantity) ";
				query += "RETURNING ORDER_ID";
		
				
		Connection c;
		try {
			c = Conexion.getConnection();
			c.setAutoCommit(false);
			
			stmt = new NamedParameterStatement(c, query);
			stmt.setInt("supplier_id", orden.getIdProveedor());
			stmt.setString("username", "Admin"); //TODO poner el correcto
			stmt.setInt("dgi_type_id", orden.getTipoCFE());
			stmt.setString("serial", orden.getSerieCFE());
			stmt.setInt("order_number", orden.getNumeroCFE());
			stmt.setTimestamp("order_date", new Timestamp(orden.getFechaComprobante().getTime()));
			stmt.setString("payment_type", orden.getFormaDePago());
			stmt.setBigDecimal("not_taxed_amount", orden.getMontoNoGravado());
			stmt.setBigDecimal("taxed_minimum_net_amount", orden.getMontoNetoGravadoIvaMinimo());
			stmt.setBigDecimal("taxed_basic_net_amount", orden.getMontoNetoGravadoIvaBasico());
			stmt.setBigDecimal("minimum_tax_total", orden.getTotalIvaMinimo());
			stmt.setBigDecimal("basic_tax_total", orden.getTotalIvaBasico());
			stmt.setBigDecimal("total_amount", orden.getMontoTotal());
			stmt.setBigDecimal("iva_withheld_amount", orden.getMontoRetenidoIVA());
			stmt.setBigDecimal("irae_withheld_amount", orden.getMontoRetenidoIRAE());
			stmt.setBigDecimal("not_billable_amount", orden.getMontoNoFacturable());
			stmt.setBigDecimal("taxed_minimum_amount", orden.getMontoTributoIvaMinimo());
			stmt.setBigDecimal("taxed_basic_amount", orden.getMontoTributoIvaBasico());
			stmt.setBigDecimal("total", orden.getMontoTotalAPagar());
			stmt.setInt("detail_quantity", orden.getCantidadLineas());
			
			ResultSet rs = stmt.executeQuery();
			long key = 0;
			while (rs.next()){
				key = rs.getLong(1);
			}
			
			stmt.close();
			
			// Para cada detalle, lo guardo en la base de datos
			Iterator<OrdenDetalle> it = orden.getDetalle().iterator();
			while (it.hasNext()) {
				OrdenDetalle ordenDetalle = (OrdenDetalle) it.next();
				
				query = "INSERT INTO order_details (order_id, line, product_id, product_number, cost, quantity, discount, offer_description, billing_id) ";
				query += " VALUES (:order_id, :line, :product_id, :product_number, :cost, :quantity, :discount, :offer_description, :billing_id)";
				
				stmt = new NamedParameterStatement(c, query);
				
				stmt.setLong("order_id", key);
				stmt.setInt("line", ordenDetalle.getNumeroLinea());
				stmt.setInt("product_id", ordenDetalle.getProductId());
				stmt.setInt("product_number", ordenDetalle.getNumeroArticulo());
				stmt.setBigDecimal("cost", ordenDetalle.getPrecioUnitario());
				stmt.setInt("quantity", ordenDetalle.getCantidad());
				stmt.setBigDecimal("discount", ordenDetalle.getDescuento());
				stmt.setString("offer_description", ordenDetalle.getDescripcionOferta());
				stmt.setInt("billing_id", ordenDetalle.getIndicadorDeFacturacion());
				
				stmt.executeUpdate();
				stmt.close();
			}
			
			c.commit();
			c.close();
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
	}

	@Override
	public List<DTTiposDGI> obtenerTiposDGI() throws Excepciones {
		List<DTTiposDGI> ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT dgi_type_id, description " + "FROM dgi_types ";

		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ret = new ArrayList<DTTiposDGI>();
			while (rs.next()) {
				DTTiposDGI tipo = new DTTiposDGI();
				tipo.setId(rs.getInt("dgi_type_id"));
				tipo.setDescripcion(rs.getString("description"));
				ret.add(tipo);
			}
		} catch (Exception e) {
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

}
