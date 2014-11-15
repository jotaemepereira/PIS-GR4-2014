package persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.NamingException;

import Util.NamedParameterStatement;
import model.Orden;
import model.OrdenDetalle;
import controladores.Excepciones;
import datatypes.DTComprobanteFactura;
import datatypes.DTLineaFacturaCompra;
import datatypes.DTTiposDGI;
import interfaces.IComprasPersistencia;

public class PComprasControlador implements IComprasPersistencia {

	@Override
	public void ingresarFacturaCompra(Orden orden) throws Excepciones {
		NamedParameterStatement stmt = null;
		Connection c;
		try {
			c = Conexion.getConnection();
			c.setAutoCommit(false);
		} catch (NamingException e1) {
			e1.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		
		// En el caso que sea una factura de DUSA verifico que no exista en el sistema
		if(orden.getOrdenDeCompra() != 0){
			String query = "SELECT COUNT(order_id) cant FROM orders WHERE dusa_order_id = :dusa_order_id";
			
			try {
				stmt = new NamedParameterStatement(c, query);
				
				stmt.setLong("dusa_order_id", orden.getOrdenDeCompra());
				
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if(rs.getInt("cant") > 0){
						return;
					}
				}
				
				stmt.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
						Excepciones.ERROR_SISTEMA));
			}
		}
		
		// Verifico que no haya otra factura ingresada en el sistema con ese tipo, serie y numero
		String query = "SELECT COUNT(order_id) cant ";
		query += "FROM orders ";
		query += "WHERE dgi_type_id = :dgi_type_id ";
		System.out.println(orden.getSerieCFE());
		if((orden.getSerieCFE() != null) && (!orden.getSerieCFE().equals("")))
			query += "AND serial = :serial ";
		query += "AND order_number = :order_number ";
		
		try {
			stmt = new NamedParameterStatement(c, query);
			
			stmt.setInt("dgi_type_id", orden.getTipoCFE());
			if((orden.getSerieCFE() != null) && (!orden.getSerieCFE().equals("")))
				stmt.setString("serial", orden.getSerieCFE());
			stmt.setInt("order_number", orden.getNumeroCFE());
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("CANT FACTURAS: " + rs.getInt("cant"));
				if(rs.getInt("cant") > 0){
					throw (new Excepciones(Excepciones.MENSAJE_FACTURA_DUPLICADA,
							Excepciones.ERROR_SISTEMA));
				}
			}
			
			stmt.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		
		// Primero inserto la orden
		query = "INSERT INTO orders (supplier_id, username, dgi_type_id, serial, order_number, order_date, payment_type, not_taxed_amount, taxed_minimum_net_amount, taxed_basic_net_amount, minimum_tax_total, basic_tax_total, total_amount, iva_withheld_amount, irae_withheld_amount, not_billable_amount, taxed_minimum_amount, taxed_basic_amount, total, detail_quantity, dusa_order_id, is_processed) ";
		query += "VALUES (:supplier_id, :username, :dgi_type_id, :serial, :order_number, :order_date, :payment_type, :not_taxed_amount, :taxed_minimum_net_amount, :taxed_basic_net_amount, :minimum_tax_total, :basic_tax_total, :total_amount, :iva_withheld_amount, :irae_withheld_amount, :not_billable_amount, :taxed_minimum_amount, :taxed_basic_amount, :total, :detail_quantity, :dusa_order_id, :is_processed) ";
		query += "RETURNING ORDER_ID";

		try {

			stmt = new NamedParameterStatement(c, query);
			stmt.setLong("supplier_id", orden.getIdProveedor());
			stmt.setString("username", orden.getNombreUsuario());
			stmt.setInt("dgi_type_id", orden.getTipoCFE());
			stmt.setString("serial", orden.getSerieCFE());
			stmt.setInt("order_number", orden.getNumeroCFE());
			stmt.setTimestamp("order_date", new Timestamp(orden
					.getFechaComprobante().getTime()));
			stmt.setString("payment_type", orden.getFormaDePago());
			stmt.setBigDecimal("not_taxed_amount", orden.getMontoNoGravado());
			stmt.setBigDecimal("taxed_minimum_net_amount",
					orden.getMontoNetoGravadoIvaMinimo());
			stmt.setBigDecimal("taxed_basic_net_amount",
					orden.getMontoNetoGravadoIvaBasico());
			stmt.setBigDecimal("minimum_tax_total", orden.getTotalIvaMinimo());
			stmt.setBigDecimal("basic_tax_total", orden.getTotalIvaBasico());
			stmt.setBigDecimal("total_amount", orden.getMontoTotal());
			stmt.setBigDecimal("iva_withheld_amount",
					orden.getMontoRetenidoIVA());
			stmt.setBigDecimal("irae_withheld_amount",
					orden.getMontoRetenidoIRAE());
			stmt.setBigDecimal("not_billable_amount",
					orden.getMontoNoFacturable());
			stmt.setBigDecimal("taxed_minimum_amount",
					orden.getMontoTributoIvaMinimo());
			stmt.setBigDecimal("taxed_basic_amount",
					orden.getMontoTributoIvaBasico());
			stmt.setBigDecimal("total", orden.getMontoTotalAPagar());
			stmt.setInt("detail_quantity", orden.getCantidadLineas());
			stmt.setLong("dusa_order_id", orden.getOrdenDeCompra());
			stmt.setBoolean("is_processed", orden.getProcesada());

			ResultSet rs = stmt.executeQuery();
			long key = 0;
			while (rs.next()) {
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
				stmt.setLong("product_id", ordenDetalle.getProductId());
				stmt.setLong("product_number", ordenDetalle.getNumeroArticulo());
				stmt.setBigDecimal("cost", ordenDetalle.getPrecioUnitario());
				stmt.setInt("quantity", ordenDetalle.getCantidad());
				stmt.setBigDecimal("discount", ordenDetalle.getDescuento());
				stmt.setString("offer_description",
						ordenDetalle.getDescripcionOferta());
				stmt.setInt("billing_id",
						ordenDetalle.getIndicadorDeFacturacion());

				stmt.executeUpdate();
				stmt.close();
			}

			c.commit();
			c.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}

	}

	public void actualizarFacturaCompraDUSA(Orden orden) throws Excepciones {
		NamedParameterStatement stmt = null;

		String query = "UPDATE orders SET is_processed = :is_processed ";
		query += "WHERE dusa_order_id = :dusa_order_id ";

		Connection c;
		try {
			c = Conexion.getConnection();

			stmt = new NamedParameterStatement(c, query);
			stmt.setBoolean("is_processed", true);
			System.out.println("ORDEN DE COMPRA " + orden.getOrdenDeCompra());
			stmt.setLong("dusa_order_id", orden.getOrdenDeCompra());
			
			stmt.executeUpdate();

			stmt.close();
			c.close();

		} catch (NamingException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		} catch (SQLException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}

	@Override
	public Map<Integer, DTTiposDGI> obtenerTiposDGI() throws Excepciones {
		Map<Integer, DTTiposDGI> ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT dgi_type_id, description " + "FROM dgi_types ";

		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ret = new HashMap<Integer, DTTiposDGI>();
			while (rs.next()) {
				DTTiposDGI tipo = new DTTiposDGI();
				tipo.setId(rs.getInt("dgi_type_id"));
				tipo.setDescripcion(rs.getString("description"));
				ret.put(tipo.getId(), tipo);
			}

			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	@Override
	public void getDatosArticulo(OrdenDetalle ordenDetalle) throws Excepciones {
		PreparedStatement stmt = null;
		String query = "SELECT p.product_id ";
		query += "FROM products p ";
		query += "INNER JOIN products_suppliers ps ON ps.product_id = p.product_id ";
		query += "WHERE ps.product_number = ? ";

		try {
			Connection c = Conexion.getConnection();

			stmt = c.prepareStatement(query);
			stmt.setLong(1, ordenDetalle.getNumeroArticulo());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ordenDetalle.setProductId(rs.getLong("product_id"));
			}

			stmt.close();

			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}

	public Map<Long, DTComprobanteFactura> obtenerFacturasPendientes()
			throws Excepciones {
		Map<Long, DTComprobanteFactura> facturas = new HashMap<Long, DTComprobanteFactura>();
		PreparedStatement stmtOrden = null;
		PreparedStatement stmtDetalle = null;
		String queryOrden = "SELECT * FROM orders ";
		queryOrden += "WHERE is_processed = ?";

		String queryDetalle = "SELECT od.*, p.description, p.list_cost, p.avg_cost, p.stock ";
		queryDetalle += "FROM order_details od ";
		queryDetalle += "INNER JOIN products p ON p.product_id = od.product_id ";
		queryDetalle += "WHERE order_id = ?";

		try {
			Connection c = Conexion.getConnection();

			stmtOrden = c.prepareStatement(queryOrden);
			stmtOrden.setBoolean(1, false);

			ResultSet rs = stmtOrden.executeQuery();
			while (rs.next()) {
				DTComprobanteFactura factura = new DTComprobanteFactura();

				factura.setCantidadLineas(rs.getInt("detail_quantity"));
				factura.setFechaComprobante(rs.getDate("order_date"));
				factura.setFormaDePago(rs.getString("payment_type"));
				factura.setIdOrden(rs.getLong("order_id"));
				factura.setIdProveedor(rs.getInt("supplier_id"));
				factura.setMontoNetoGravadoIvaBasico(rs
						.getBigDecimal("taxed_basic_net_amount"));
				factura.setMontoNetoGravadoIvaMinimo(rs
						.getBigDecimal("taxed_minimum_net_amount"));
				factura.setMontoNoFacturable(rs
						.getBigDecimal("not_billable_amount"));
				factura.setMontoNoGravado(rs.getBigDecimal("not_taxed_amount"));
				factura.setMontoRetenidoIRAE(rs
						.getBigDecimal("irae_withheld_amount"));
				factura.setMontoRetenidoIVA(rs
						.getBigDecimal("iva_withheld_amount"));
				factura.setMontoTotal(rs.getBigDecimal("total_amount"));
				factura.setMontoTotalAPagar(rs.getBigDecimal("total"));
				factura.setMontoTributoIvaBasico(rs
						.getBigDecimal("taxed_basic_amount"));
				factura.setMontoTributoIvaMinimo(rs
						.getBigDecimal("taxed_minimum_amount"));
				factura.setOrdenDeCompra(rs.getLong("dusa_order_id"));
				factura.setTipoCFE(rs.getInt("dgi_type_id"));
				factura.setSerieCFE(rs.getString("serial"));
				factura.setNumeroCFE(rs.getInt("order_number"));
				factura.setTotalIvaBasico(rs.getBigDecimal("basic_tax_total"));
				factura.setTotalIvaMinimo(rs.getBigDecimal("minimum_tax_total"));

				stmtDetalle = c.prepareStatement(queryDetalle);
				stmtDetalle.setLong(1, factura.getIdOrden());

				ResultSet rsDetalle = stmtDetalle.executeQuery();
				while (rsDetalle.next()) {
					DTLineaFacturaCompra detalle = new DTLineaFacturaCompra();

					detalle.setCantidad(rsDetalle.getInt("quantity"));
					detalle.setCostoListaArticulo(rsDetalle
							.getBigDecimal("list_cost"));
					detalle.setAvg_cost(rsDetalle.getBigDecimal("avg_cost"));
					detalle.setDescripcion(rsDetalle.getString("description"));
					detalle.setDescripcionOferta(rsDetalle
							.getString("offer_description"));
					detalle.setDescuento(rsDetalle.getBigDecimal("discount"));
					detalle.setIdOrden(factura.getIdOrden());
					detalle.setIndicadorDeFacturacion(rsDetalle
							.getInt("billing_id"));
					detalle.setNumeroArticulo(rsDetalle
							.getInt("product_number"));
					detalle.setNumeroLinea(rsDetalle.getInt("line"));
					detalle.setPrecioUnitario(rsDetalle.getBigDecimal("cost"));
					detalle.setProductId(rsDetalle.getLong("product_id"));
					detalle.setStock(rsDetalle.getInt("stock"));

					BigDecimal precio = detalle.getPrecioUnitario();
					BigDecimal descuento = detalle.getDescuento()
							.subtract(new BigDecimal(100)).abs()
							.divide(new BigDecimal(100));
					BigDecimal cantidad = new BigDecimal(detalle.getCantidad());
					BigDecimal total = precio.multiply(cantidad).multiply(
							descuento);
					detalle.setTotal(total);

					factura.setSubtotalProdctos(factura.getSubtotalProdctos().add(total));
					factura.getDetalle().add(detalle);
				}

				stmtDetalle.close();

				facturas.put(factura.getOrdenDeCompra(), factura);
			}

			stmtOrden.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}

		return facturas;
	}

	public Date getFechaUltimaFacturaDUSA() throws Excepciones {
		Date ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT o.order_date ";
		query += "FROM orders o ";
		query += "WHERE o.dusa_order_id > ? ";
		query += "AND o.order_date IN (SELECT MAX(aux.order_date) FROM orders aux WHERE aux.order_id = o.order_id) ";
		query += "LIMIT 1";

		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			stmt.setLong(1, 0);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("fecha: " + rs.getDate("order_date"));
				ret = rs.getDate("order_date");
			}

			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

}
