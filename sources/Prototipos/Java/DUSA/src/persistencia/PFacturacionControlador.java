package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
					+ "INNER JOIN clients c ON s.client_id = c.client_id "
					+ "INNER JOIN users u ON s.user_id = u.user_id "
					+ "WHERE s.sale_status = '"
					+ Enumerados.EstadoVenta.PENDIENTE + "'";
			ResultSet rs = st.executeQuery(sqlQuery);

			Venta v = null;
			Cliente c = null;
			Usuario u = null;
			LineaVenta lv = null;
			while (rs.next()) {
				if (v == null || rs.getLong("sale_id") != v.getVentaId()) {
					v = new Venta();
					u = new Usuario();
					c = new Cliente();

					c.setClientId(rs.getInt("client_id"));
					c.setApellido(rs.getString("surname"));
					c.setDireccion(rs.getString("client_address"));
					c.setNombre(rs.getString("client_name"));
					c.setTelefono(rs.getString("phone"));
					c.setUltimaModificacion(rs.getDate("last_modified"));

					u.setUsuarioId(rs.getInt("user_id"));
					u.setEstado(rs.getBoolean("status"));
					u.setNombre(rs.getString("username"));
					u.setPwd_hash(rs.getString("pwd_hash"));

					v.setVentaId(rs.getLong("sale_id"));
					v.setCantidadLineas(0);
					v.setClienteId(rs.getInt("client_id"));
					v.setFechaVenta(rs.getDate("sale_date"));
					v.setLineas(new ArrayList<LineaVenta>());
					v.setMontoNetoGravadoIvaBasico(rs
							.getBigDecimal("taxed_basic_net_amount"));
					v.setMontoNetoGravadoIvaMinimo(rs
							.getBigDecimal("taxed_minimum_net_amount"));
					v.setMontoNoFacturable(rs
							.getBigDecimal("not_billable_amount"));
					v.setMontoNoGravado(rs.getBigDecimal("not_taxed_amount"));
					v.setMontoRetenidoIRAE(rs
							.getBigDecimal("irae_withheld_amount"));
					v.setMontoRetenidoIVA(rs
							.getBigDecimal("iva_withheld_amount"));
					v.setMontoTotal(rs.getBigDecimal("total_amount"));
					v.setMontoTotalAPagar(rs.getBigDecimal("total"));
					v.setMontoTributoIvaBasico(rs
							.getBigDecimal("taxed_basic_amount"));
					v.setMontoTributoIvaMinimo(rs
							.getBigDecimal("taxed_minimum_amount"));
					v.setSerial(rs.getString("serial"));
					v.setTipoDgi(rs.getInt("order_dgi_type"));
					v.setFormaDePago(rs.getString("payment_type"));
					v.setTipoVenta((char) rs.getByte("sale_type"));
					v.setTotalIvaBasico(rs.getBigDecimal("basic_tax_total"));
					v.setTotalIvaMinimo(rs.getBigDecimal("minimum_tax_total"));
					v.setUsuarioId(rs.getInt("user_id"));

					v.setUsuario(u);
					v.setCliente(c);

					ret.add(v);
				}

				lv = new LineaVenta();
				lv.setCantidad(rs.getInt("quantity"));
				lv.setDescuento(rs.getBigDecimal("discount"));
				lv.setPrecio(rs.getBigDecimal("sale_price"));
				lv.setProductoId(rs.getLong("product_id"));
				lv.setVentaId(rs.getLong("sale_id"));

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
	public void facturarVenta(long ventaId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Venta obtenerVentaParaFacturar(long ventaId) throws Exception {
		Connection con = Conexion.getConnection();
		con.setAutoCommit(false);
		Statement st = con.createStatement();
		try {

			String sqlQuery = "SELECT * FROM sales s "
					+ "INNER JOIN sale_details sd ON s.sale_id = sd.sale_id "
					+ "INNER JOIN clients c ON s.client_id = c.client_id "
					+ "INNER JOIN users u ON s.user_id = u.user_id "
					+ "WHERE s.sale_id = " + ventaId + " "
					+ "AND s.sale_status = '"
					+ Enumerados.EstadoVenta.PENDIENTE + "'";
			ResultSet rs = st.executeQuery(sqlQuery);

			Venta v = null;
			Cliente c = null;
			Usuario u = null;
			LineaVenta lv = null;
			boolean first = true;
			while (rs.next()) {
				if (first) {
					first = false;
					v = new Venta();
					u = new Usuario();
					c = new Cliente();

					c.setClientId(rs.getInt("client_id"));
					c.setApellido(rs.getString("surname"));
					c.setDireccion(rs.getString("client_address"));
					c.setNombre(rs.getString("client_name"));
					c.setTelefono(rs.getString("phone"));
					c.setUltimaModificacion(rs.getDate("last_modified"));

					u.setUsuarioId(rs.getInt("user_id"));
					u.setEstado(rs.getBoolean("status"));
					u.setNombre(rs.getString("username"));
					u.setPwd_hash(rs.getString("pwd_hash"));

					v.setVentaId(rs.getLong("sale_id"));
					v.setCantidadLineas(0);
					v.setClienteId(rs.getInt("client_id"));
					v.setFechaVenta(rs.getDate("sale_date"));
					v.setLineas(new ArrayList<LineaVenta>());
					v.setMontoNetoGravadoIvaBasico(rs
							.getBigDecimal("taxed_basic_net_amount"));
					v.setMontoNetoGravadoIvaMinimo(rs
							.getBigDecimal("taxed_minimum_net_amount"));
					v.setMontoNoFacturable(rs
							.getBigDecimal("not_billable_amount"));
					v.setMontoNoGravado(rs.getBigDecimal("not_taxed_amount"));
					v.setMontoRetenidoIRAE(rs
							.getBigDecimal("irae_withheld_amount"));
					v.setMontoRetenidoIVA(rs
							.getBigDecimal("iva_withheld_amount"));
					v.setMontoTotal(rs.getBigDecimal("total_amount"));
					v.setMontoTotalAPagar(rs.getBigDecimal("total"));
					v.setMontoTributoIvaBasico(rs
							.getBigDecimal("taxed_basic_amount"));
					v.setMontoTributoIvaMinimo(rs
							.getBigDecimal("taxed_minimum_amount"));
					v.setSerial(rs.getString("serial"));
					v.setTipoDgi(rs.getInt("order_dgi_type"));
					v.setFormaDePago(rs.getString("payment_type"));
					v.setTipoVenta((char) rs.getByte("sale_type"));
					v.setTotalIvaBasico(rs.getBigDecimal("basic_tax_total"));
					v.setTotalIvaMinimo(rs.getBigDecimal("minimum_tax_total"));
					v.setUsuarioId(rs.getInt("user_id"));

					v.setUsuario(u);
					v.setCliente(c);
				}

				lv = new LineaVenta();
				lv.setCantidad(rs.getInt("quantity"));
				lv.setDescuento(rs.getBigDecimal("discount"));
				lv.setPrecio(rs.getBigDecimal("sale_price"));
				lv.setProductoId(rs.getLong("product_id"));
				lv.setVentaId(rs.getLong("sale_id"));

				v.getLineas().add(lv);
				v.setCantidadLineas(v.getCantidadLineas() + 1);
			}

			if (v != null) {
				String sqlUpdate = "UPDATE sales SET sale_status = '"
						+ Enumerados.EstadoVenta.FACTURANDO
						+ "'  WHERE sale_id = " + ventaId;
				st.executeUpdate(sqlUpdate);
			}
			con.commit();
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
		Statement st = con.createStatement();
		try {
			String sqlUpdate = "UPDATE sales SET sale_status = '"
					+ Enumerados.EstadoVenta.FACTURADA
					+ "'  WHERE sale_id = " + ventaId;
			st.executeUpdate(sqlUpdate);
		} catch (Exception e) {
			throw e;
		} finally {
			st.close();
			con.close();
		}

	}
	
	/**
	 * @author Guille
	 */
	@Override
	public List<Long> getIdArticulosEnPeriodo(Date desde, Date hasta) throws Exception{
		
		Connection con = Conexion.getConnection();
		PreparedStatement stmt = null;
		List<Long> articulos = new ArrayList<Long>();
		try {
			String sql = "SELECT distinct product_id FROM sale_details sd "
								+ "WHERE sd.sale_id in " + "(SELECT sale_id FROM sales s" 
															+ "WHERE s.sale_status = ? "
																+ "and s.sale_date BETWEEN ? and ?);";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "'" + Enumerados.EstadoVenta.FACTURADA + "'");
			stmt.setString(2, desde.toString());
			stmt.setString(3, hasta.toString());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				articulos.add(new Long(rs.getLong("product_id"))); 
			}
		} catch (Exception e) {
			throw e;
		} finally {
			stmt.close();
			con.close();
		}
		return articulos;
	}
	
	/**
	 * @author Guille
	 */
	
	@Override
	public int cantidadVendidaEnPeriodo(Long idArticulo, Date desde, Date hasta) throws Exception {
		
		Connection con = Conexion.getConnection();
		PreparedStatement stmt = null;
		int cantidadVendida = 0;
		try {
			String sql = "SELECT sum(quantity) as total" + 
							"FROM sales s INNER JOIN sale_details sd " + 
									"ON s.sale_id = sd.sale_id" +
							"WHERE sd.product_id = ? and s.sale_status = ? and s.sale_date BETWEEN ? and ?" +
							"GROUP BY product_id";
			stmt = con.prepareStatement(sql);
			stmt.setLong(1, idArticulo.longValue());
			stmt.setString(2, "'" + Enumerados.EstadoVenta.FACTURADA + "'");
			stmt.setString(3, desde.toString());
			stmt.setString(4, hasta.toString());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				cantidadVendida = rs.getInt("total"); 
			}
		} catch (Exception e) {
			throw e;
		} finally {
			stmt.close();
			con.close();
		}
		return cantidadVendida;
	}

}
