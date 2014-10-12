package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

					v.setFechaVenta(rs.getDate("sale_date"));
					v.setEstadoVenta((char) rs.getByte("sale_status"));
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
				// TODO: Agregar presentacion
				lv.setArticulo(a);

				lv.setPrecio(rs.getBigDecimal("sale_price"));
				lv.setCantidad(rs.getInt("quantity"));
				lv.setDescuento(rs.getBigDecimal("discount"));

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
	public void facturarVenta(Venta venta) throws Exception {
		Connection con = Conexion.getConnection();
		con.setAutoCommit(false);
		Statement st = con.createStatement();
		try {

			String sqlQuery = "SELECT * FROM sales s "
					+ "INNER JOIN sale_details sd ON s.sale_id = sd.sale_id "
					+ "INNER JOIN clients c ON s.client_id = c.client_id "
					+ "INNER JOIN users u ON s.user_id = u.user_id "
					+ "WHERE s.sale_id = " + venta.getVentaId() + " "
					+ "AND s.sale_status = '"
					+ Enumerados.EstadoVenta.PENDIENTE + "'";
			ResultSet rs = st.executeQuery(sqlQuery);

			if (rs.next()) {
				String sqlUpdate = "UPDATE sales SET sale_status = '"
						+ Enumerados.EstadoVenta.FACTURADA
						+ "'  WHERE sale_id = " + venta.getVentaId();
				st.executeUpdate(sqlUpdate);

				for (LineaVenta lv : venta.getLineas()) {
					sqlUpdate = "UPDATE sale_details SET white_recipe = "
							+ lv.isRecetaBlanca() + " " + "green_recipe = "
							+ lv.isRecetaVerde() + " " + "orange_recipe = "
							+ lv.isRecetaNaranja() + " " + "WHERE sale_id = "
							+ venta.getVentaId() + " AND product_id = "
							+ lv.getArticulo().getIdArticulo();
					st.executeUpdate(sqlUpdate);
				}
			}
			
			// TODO: Falta recalcular totales

			con.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			st.close();
			con.rollback();
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
	public List<Long> getIdArticulosEnPeriodo(Date desde, Date hasta)
			throws Exception {

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
	public int cantidadVendidaEnPeriodo(Long idArticulo, Date desde, Date hasta)
			throws Exception {

		Connection con = Conexion.getConnection();
		PreparedStatement stmt = null;
		int cantidadVendida = 0;
		try {
			String sql = "SELECT sum(quantity) as total"
					+ "FROM sales s INNER JOIN sale_details sd "
					+ "ON s.sale_id = sd.sale_id"
					+ "WHERE sd.product_id = ? and s.sale_status = ? and s.sale_date BETWEEN ? and ?"
					+ "GROUP BY product_id";
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
