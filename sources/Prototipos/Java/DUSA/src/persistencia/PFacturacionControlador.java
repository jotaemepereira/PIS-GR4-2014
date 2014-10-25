package persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import controladores.Excepciones;
import model.Articulo;
import model.Cliente;
import model.Enumerados;
import model.LineaVenta;
import model.Usuario;
import model.Venta;
import interfaces.IFacturacionPersistencia;

public class PFacturacionControlador implements IFacturacionPersistencia {

	@Override
	public List<Venta> listarVentasPendientes() throws Exception {
		Connection con = Conexion.getConnection();
		Statement st = con.createStatement();
		try {
			List<Venta> ret = new ArrayList<Venta>();

			String sqlQuery = "SELECT * FROM sales s "
					+ "INNER JOIN sale_details sd ON s.sale_id = sd.sale_id "
					+ "INNER JOIN products p ON p.product_id = sd.product_id "
					+ "INNER JOIN users u ON s.user_id = u.user_id "
					+ "LEFT JOIN clients c ON s.client_id = c.client_id "
					+ "WHERE s.sale_status = '"
					+ Enumerados.EstadoVenta.PENDIENTE + "'";
			ResultSet rs = st.executeQuery(sqlQuery);

			Venta v = null;
			Cliente c = null;
			Usuario u = null;
			LineaVenta lv = null;
			Articulo a = null;
			while (rs.next()) {
				if (v == null || rs.getLong("sale_id") != v.getVentaId()) {
					v = new Venta();
					u = new Usuario();
					c = new Cliente();

					v.setVentaId(rs.getLong("sale_id"));

					v.setClienteId(rs.getInt("client_id"));
					c.setClientId(rs.getInt("client_id"));
					c.setApellido(rs.getString("surname"));
					c.setDireccion(rs.getString("client_address"));
					c.setNombre(rs.getString("client_name"));
					c.setTelefono(rs.getString("phone"));
					c.setUltimaModificacion(rs.getDate("last_modified"));

					v.setUsuarioId(rs.getInt("user_id"));
					u.setUsuarioId(rs.getInt("user_id"));
					u.setEstado(rs.getBoolean("status"));
					u.setNombre(rs.getString("username"));
					u.setPwd_hash(rs.getString("pwd_hash"));

					v.setFechaVenta(rs.getTimestamp("sale_date"));
					v.setEstadoVenta(rs.getString("sale_status"));
					v.setTipoDgi(rs.getInt("sale_dgi_type"));
					v.setSerial(rs.getString("serial"));
					v.setFormaDePago(rs.getString("payment_type"));
					v.setMontoNoGravado(rs.getBigDecimal("not_taxed_amount"));
					v.setMontoNetoGravadoIvaMinimo(rs
							.getBigDecimal("taxed_minimum_net_amount"));
					v.setMontoNetoGravadoIvaBasico(rs
							.getBigDecimal("taxed_basic_net_amount"));
					v.setTotalIvaMinimo(rs.getBigDecimal("minimum_tax_total"));
					v.setTotalIvaBasico(rs.getBigDecimal("basic_tax_total"));
					v.setMontoTotal(rs.getBigDecimal("total_amount"));
					v.setMontoRetenidoIVA(rs
							.getBigDecimal("iva_withheld_amount"));
					v.setMontoRetenidoIRAE(rs
							.getBigDecimal("irae_withheld_amount"));
					v.setMontoNoFacturable(rs
							.getBigDecimal("not_billable_amount"));
					v.setMontoTributoIvaMinimo(rs
							.getBigDecimal("taxed_minimum_amount"));
					v.setMontoTributoIvaBasico(rs
							.getBigDecimal("taxed_basic_amount"));
					v.setMontoTotalAPagar(rs.getBigDecimal("total"));

					v.setCantidadLineas(0);
					v.setLineas(new ArrayList<LineaVenta>());

					v.setUsuario(u);
					v.setCliente(c);

					ret.add(v);
				}

				lv = new LineaVenta();
				lv.setVentaId(rs.getLong("sale_id"));
				lv.setLinea(rs.getInt("line"));
				lv.setProductoId(rs.getLong("product_id"));
				a = new Articulo();
				a.setDescripcion(rs.getString("description"));
				a.setPresentacion(rs.getString("presentation"));
				lv.setArticulo(a);

				lv.setPrecio(rs.getBigDecimal("sale_price"));
				if (lv.getPrecio() == null){
					lv.setPrecio(new BigDecimal(0));
				}
				lv.setCantidad(rs.getInt("quantity"));
				lv.setDescuento(rs.getBigDecimal("discount"));
				lv.setRecetaBlanca(rs.getBoolean("white_recipe"));
				lv.setRecetaNaranja(rs.getBoolean("orange_recipe"));
				lv.setRecetaVerde(rs.getBoolean("green_recipe"));
				if (lv.getDescuento() == null){
					lv.setDescuento(new BigDecimal(0));
				}
				v.getLineas().add(lv);
				
				v.setCantidadLineas(v.getCantidadLineas() + 1);
			}
			return ret;
		} catch (Exception e) {
			throw e;
		} finally {
			st.close();
			con.close();
		}
	}

	@Override
	public void cancelarVenta(long ventaId) throws Exception {
		Connection con = Conexion.getConnection();
		Statement st = con.createStatement();
		try {
			String sqlUpdate = "UPDATE sales SET sale_status = '"
					+ Enumerados.EstadoVenta.CANCELADA + "'  WHERE sale_id = "
					+ ventaId;
			st.executeUpdate(sqlUpdate);
		} catch (Exception e) {
			throw e;
		} finally {
			st.close();
			con.close();
		}
	}
	
	@Override
	public Venta facturarVenta(long ventaId) throws Exception {
		Connection con = Conexion.getConnection();
		Statement st = con.createStatement();
		try {

			String sqlUpdate = "UPDATE sales SET sale_status = '"
					+ Enumerados.EstadoVenta.FACTURADA + "'  WHERE sale_id = "
					+ ventaId;
			st.executeUpdate(sqlUpdate);

			String sqlQuery = "SELECT * FROM sales s "
					+ "INNER JOIN sale_details sd ON s.sale_id = sd.sale_id "
					+ "INNER JOIN products p ON p.product_id = sd.product_id "
					+ "INNER JOIN users u ON s.user_id = u.user_id "
					+ "LEFT JOIN clients c ON s.client_id = c.client_id "
					+ "WHERE s.sale_id = " + ventaId;
			ResultSet rs = st.executeQuery(sqlQuery);

			Venta v = null;
			Cliente c = null;
			Usuario u = null;
			LineaVenta lv = null;
			Articulo a = null;
			boolean first = true;
			while (rs.next()) {
				if (first) {
					first = false;
					v = new Venta();
					u = new Usuario();
					c = new Cliente();

					v.setVentaId(rs.getLong("sale_id"));

					v.setClienteId(rs.getInt("client_id"));
					c.setClientId(rs.getInt("client_id"));
					c.setApellido(rs.getString("surname"));
					c.setDireccion(rs.getString("client_address"));
					c.setNombre(rs.getString("client_name"));
					c.setTelefono(rs.getString("phone"));
					c.setUltimaModificacion(rs.getDate("last_modified"));

					v.setUsuarioId(rs.getInt("user_id"));
					u.setUsuarioId(rs.getInt("user_id"));
					u.setEstado(rs.getBoolean("status"));
					u.setNombre(rs.getString("username"));
					u.setPwd_hash(rs.getString("pwd_hash"));

					v.setFechaVenta(rs.getDate("sale_date"));
					v.setEstadoVenta(rs.getString("sale_status"));
					v.setTipoDgi(rs.getInt("sale_dgi_type"));
					v.setSerial(rs.getString("serial"));
					v.setFormaDePago(rs.getString("payment_type"));
					v.setMontoNoGravado(rs.getBigDecimal("not_taxed_amount"));
					v.setMontoNetoGravadoIvaMinimo(rs
							.getBigDecimal("taxed_minimum_net_amount"));
					v.setMontoNetoGravadoIvaBasico(rs
							.getBigDecimal("taxed_basic_net_amount"));
					v.setTotalIvaMinimo(rs.getBigDecimal("minimum_tax_total"));
					v.setTotalIvaBasico(rs.getBigDecimal("basic_tax_total"));
					v.setMontoTotal(rs.getBigDecimal("total_amount"));
					v.setMontoRetenidoIVA(rs
							.getBigDecimal("iva_withheld_amount"));
					v.setMontoRetenidoIRAE(rs
							.getBigDecimal("irae_withheld_amount"));
					v.setMontoNoFacturable(rs
							.getBigDecimal("not_billable_amount"));
					v.setMontoTributoIvaMinimo(rs
							.getBigDecimal("taxed_minimum_amount"));
					v.setMontoTributoIvaBasico(rs
							.getBigDecimal("taxed_basic_amount"));
					v.setMontoTotalAPagar(rs.getBigDecimal("total"));

					v.setCantidadLineas(0);
					v.setLineas(new ArrayList<LineaVenta>());

					v.setUsuario(u);
					v.setCliente(c);

				}

				lv = new LineaVenta();
				lv.setVentaId(rs.getLong("sale_id"));
				lv.setLinea(rs.getInt("line"));
				lv.setProductoId(rs.getLong("product_id"));
				a = new Articulo();
				a.setDescripcion(rs.getString("description"));
				// TODO: Agregar presentacion
				lv.setArticulo(a);

				lv.setPrecio(rs.getBigDecimal("sale_price"));
				lv.setCantidad(rs.getInt("quantity"));
				lv.setDescuento(rs.getBigDecimal("discount"));
				lv.setRecetaBlanca(rs.getBoolean("white_recipe"));
				lv.setRecetaNaranja(rs.getBoolean("orange_recipe"));
				lv.setRecetaVerde(rs.getBoolean("green_recipe"));
				v.getLineas().add(lv);
				v.setCantidadLineas(v.getCantidadLineas() + 1);
			}
			return v;
		} catch (Exception e) {
			throw e;
		} finally {
			st.close();
			con.close();
		}
	}

	@Override
	public void marcarVentaFacturada(long ventaId) throws Exception {
		Connection con = Conexion.getConnection();
		con.setAutoCommit(false);
		Statement st = con.createStatement();
		try {

			String sqlUpdate = "UPDATE sales SET sale_status = '"
					+ Enumerados.EstadoVenta.FACTURADA + "'  WHERE sale_id = "
					+ ventaId;
			st.executeUpdate(sqlUpdate);

			String sqlQuery = "SELECT product_id, quantity "
					+ "FROM sale_details sd"
					+ "INNER JOIN products p ON p.produt_id = sd.product_id "
					+ "WHERE sd.sale_id = " + ventaId;
			ResultSet rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				sqlUpdate = "UPDATE products SET stock = stock - "
						+ rs.getInt("quantity") + " " + "WHERE product_id = "
						+ rs.getLong("product_id");
				st.executeUpdate(sqlUpdate);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			st.close();
			con.rollback();
			con.close();
		}

	}

	/**
	 * @author Guille
	 */
	@Override
	public List<Long> getIdArticulosEnPeriodo(java.util.Date desde, java.util.Date hasta) throws Excepciones{
		
		Connection con = null;
		PreparedStatement stmt = null;
		List<Long> articulos = new ArrayList<Long>();
		
		try {
			
			con = Conexion.getConnection();
			String sql = "SELECT distinct p.product_id " + 
							"FROM sale_details sd " +
								"INNER JOIN products_suppliers ps ON sd.product_id = ps.product_id " +
								"INNER JOIN products p ON p.product_id = sd.product_id " +
								  "WHERE ps.supplier_id = ? AND " +
								  		"p.status = ? AND " +
								  		"sd.sale_id in " + "(SELECT sale_id FROM sales s " 
															+ "WHERE s.sale_status = ? "
																+ "AND s.sale_date BETWEEN ? AND ?);";
			stmt = con.prepareStatement(sql);
			stmt.setInt		(1, Enumerados.infoDUSA.proveedorID);
			stmt.setBoolean	(2, true);
			stmt.setString	(3, String.valueOf(Enumerados.EstadoVenta.FACTURADA));
			Calendar cal = Calendar.getInstance();
			cal.setTime(desde);
			stmt.setTimestamp(4, new Timestamp(cal.getTimeInMillis()));
			cal.setTime(hasta);
			stmt.setTimestamp(5, new Timestamp(cal.getTimeInMillis()));
//			stmt.setDate(4, desde);
//			stmt.setDate(5, hasta);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				articulos.add(new Long(rs.getLong("product_id"))); 
			}
			
			stmt.close();
			con.close();
		} catch (Exception e) {
			
			e.printStackTrace();
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		return articulos;
	}

	/**
	 * @author Guille
	 */
	@Override
	public int cantidadVendidaEnPeriodo(Long idArticulo, java.util.Date desde, java.util.Date hasta) throws Excepciones {
		
		int cantidadVendida = 0;
		try {
			
			Connection con = Conexion.getConnection();
			
			String sql = "SELECT sum(quantity) as total " + 
							"FROM sales s INNER JOIN sale_details sd " + 
									"ON s.sale_id = sd.sale_id " +
							"WHERE sd.product_id = ? and s.sale_status = ? and s.sale_date BETWEEN ? and ? " +
							"GROUP BY product_id;";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, idArticulo.longValue());
			stmt.setString(2, String.valueOf(Enumerados.EstadoVenta.FACTURADA));
			Calendar cal = Calendar.getInstance();
			cal.setTime(desde);
			stmt.setTimestamp(3, new Timestamp(cal.getTimeInMillis()));
			cal.setTime(hasta);
			stmt.setTimestamp(4, new Timestamp(cal.getTimeInMillis()));
			ResultSet rs = stmt.executeQuery();
			
			//Obtengo la cantidad total
			while (rs.next()) {
				cantidadVendida = rs.getInt("total"); 
			}
			
			stmt.close();
			con.close();
		} catch (Exception e) {
			
			e.printStackTrace();
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		
		return cantidadVendida;
	}

}
