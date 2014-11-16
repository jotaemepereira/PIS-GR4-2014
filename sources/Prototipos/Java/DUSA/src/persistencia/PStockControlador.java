package persistencia;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import Util.NamedParameterStatement;
import controladores.Excepciones;
import datatypes.DTBusquedaArticulo;
import datatypes.DTBusquedaArticuloSolr;
import datatypes.DTModificacionArticulo;
import datatypes.DTProveedor;
import datatypes.DTProducto;
import datatypes.DTVencimiento;
import model.AccionTer;
import model.Articulo;
import model.Cambio;
import model.Droga;
import model.Enumerados;
import model.LineaPedido;
import model.Orden;
import model.OrdenDetalle;
import model.Pedido;
import model.TipoIva;
import model.Usuario;
import interfaces.IStockPersistencia;

public class PStockControlador implements IStockPersistencia {
	
	@Override
	public void persistirArticulo(Articulo articulo) throws Excepciones {
		PreparedStatement stmt = null;

		String query = "INSERT INTO PRODUCTS "
				+ "(BRAND_ID, PRODUCT_TYPE, DESCRIPTION, PRESENTATION, KEY1, KEY2, KEY3, IS_PSYCHOTROPIC, IS_NARCOTIC, IS_REFRIGERATOR, SALE_CODE, AUTHORIZATION_TYPE,"
				+ " UNIT_PRICE, SALE_PRICE, SALE_PRICE_PORCENTAGE, RECIPE_PRICE, RECIPE_DISCOUNT, LIST_COST, OFFER_COST, LAST_COST, AVG_COST, TAX_TYPE_ID, BARCODE, LAST_PRICE_DATE"
				+ ", NEAREST_DUE_DATE, STOCK, MINIMUM_STOCK, USERNAME, LAST_MODIFIED, STATUS) "
				+ " VALUES "
				+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, LOCALTIMESTAMP, ?, ?, ?, ?, LOCALTIMESTAMP, ?) RETURNING PRODUCT_ID;";
		Connection c;
		try {
			c = Conexion.getConnection();
			c.setAutoCommit(false);
			try {
				// Seteo los datos a insertar en la bd
				stmt = c.prepareStatement(query);
				if (articulo.getIdMarca() != 0) {
					stmt.setInt(1, articulo.getIdMarca());
				} else {
					stmt.setNull(1, java.sql.Types.INTEGER);
				}
				stmt.setString(2, String.valueOf(articulo.getTipoArticulo()));// Null
				stmt.setString(3, articulo.getDescripcion());// Not Null
				stmt.setString(4, articulo.getPresentacion());// Null
				stmt.setString(5, articulo.getClave1());// Null
				stmt.setString(6, articulo.getClave2());// Null
				stmt.setString(7, articulo.getClave3());// Null
				stmt.setBoolean(8, articulo.isEsPsicofarmaco());// Not Null
				stmt.setBoolean(9, articulo.isEsEstupefaciente());// Not Null
				stmt.setBoolean(10, articulo.isEsHeladera());// Not Null
				if (articulo.getCodigoVenta() != 0x00) {
					stmt.setString(11,
							String.valueOf(articulo.getCodigoVenta()));// Null
				} else {
					stmt.setNull(11, java.sql.Types.CHAR);
				}
				stmt.setString(12,
						String.valueOf(Enumerados.habilitado.HABILITADO));// Not
																			// Null
				stmt.setBigDecimal(13, articulo.getPrecioUnitario());// Not Null
				stmt.setBigDecimal(14, articulo.getPrecioVenta());// Not Null
				stmt.setBigDecimal(15, articulo.getPorcentajePrecioVenta());// Not Null
				stmt.setBigDecimal(16, articulo.getPrecioConReceta());// Null
				stmt.setBigDecimal(17, articulo.getPorcentajeDescuentoReceta());// Null
				stmt.setBigDecimal(18, articulo.getCostoLista());// Not Null
				stmt.setBigDecimal(19, articulo.getCostoOferta());// Null
				stmt.setBigDecimal(20, articulo.getUltimoCosto());// Null
				stmt.setBigDecimal(21, articulo.getCostoPromedio());// Null
				if (articulo.getTipoIva() != null) {
					if (articulo.getTipoIva().getTipoIVA() != 0x00){
						stmt.setString(22, String.valueOf(articulo.getTipoIva().getTipoIVA()));// Null
					}else{
						stmt.setNull(22, java.sql.Types.CHAR);
					}
				} else {
					stmt.setNull(22, java.sql.Types.CHAR);
				}
				stmt.setString(23, articulo.getCodigoBarras());// Null
				stmt.setNull(24, java.sql.Types.TIMESTAMP);// Null Vencimiento Más Cercano
				stmt.setLong(25, articulo.getStock());// Not Null
				stmt.setLong(26, articulo.getStockMinimo());// Null
				stmt.setString(27, articulo.getUsuario().getNombre());// Not Null
				stmt.setBoolean(28, true);// Not Null

				ResultSet rs = stmt.executeQuery();
				// Obtengo la clave del nuevo artículo creado
				long key = 0;
				while (rs.next()) {
					key = rs.getLong(1);
				}

				// Para cada proveedor asociado inserto una fila en
				// products_suppliers
				List<DTProveedor> proveedores = new ArrayList<DTProveedor>(
						articulo.getProveedores().values());
				Iterator<DTProveedor> i = proveedores.iterator();
				while (i.hasNext()) {
					DTProveedor next = i.next();
					query = "INSERT INTO PRODUCTS_SUPPLIERS "
							+ "(SUPPLIER_ID, PRODUCT_ID, PRODUCT_NUMBER, LINE_ID) "
							+ "VALUES " + "(?, ?, ?, ?)";
					stmt = c.prepareStatement(query);
					stmt.setInt(1, next.getIdProveedor());
					stmt.setLong(2, key);
					stmt.setLong(3, next.getCodigoIdentificador());
					stmt.setString(4, next.getIdLinea());
					stmt.executeUpdate();
				}

				// Para cada droga seleccionada inserto una fila en
				// product_drugs
				if (articulo.getDrogas() != null) {
					for (long idDroga : articulo.getDrogas()) {
						query = "INSERT INTO PRODUCT_DRUGS "
								+ "(PRODUCT_ID, DRUG_ID) " + "VALUES "
								+ "(?, ?)";
						stmt = c.prepareStatement(query);
						stmt.setLong(1, key);
						stmt.setLong(2, idDroga);
						stmt.executeUpdate();
					}
				}

				// Para cada acción terapéutica seleccionada inserto una fila
				// en product_therap_actions
				if (articulo.getAccionesTer() != null) {
					for (long idAccTer : articulo.getAccionesTer()) {
						query = "INSERT INTO PRODUCT_THERAP_ACTIONS "
								+ "(PRODUCT_ID, THERAPEUTIC_ACTION_ID) "
								+ "VALUES " + "(?, ?)";
						stmt = c.prepareStatement(query);
						stmt.setLong(1, key);
						stmt.setLong(2, idAccTer);
						stmt.executeUpdate();
					}
				}

				// Commiteo todo y cierro conexion
				c.commit();
				stmt.close();
				c.close();

			} catch (Exception e) {
				// Hago rollback de las cosas y lanzo excepcion
				c.rollback();
				e.printStackTrace();
				throw (new Excepciones("Error sistema",
						Excepciones.ERROR_SISTEMA));
			}
		} catch (NamingException e1) {
			e1.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
	}

	@Override
	public boolean existeArticulo(String descripcion) throws Excepciones {
		int cant = 0;
		PreparedStatement stmt = null;
		String query = "SELECT COUNT(*) AS cant FROM products "
				+ "WHERE DESCRIPTION = ?";
		
		try {

			
			Connection c = Conexion.getConnection();
			
			stmt = c.prepareStatement(query);
			stmt.setString(1, descripcion);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				
				cant = rs.getInt("cant");
			}

			rs.close();
			stmt.close();
			c.close();
		
		
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return (cant > 0);
		
	}
	
	public long obtenerIdPorDescripcion(String descripcion) throws Excepciones {
		long id = 0;
		PreparedStatement stmt = null;
		String query = "SELECT product_id FROM products "
				+ "WHERE DESCRIPTION = ?";

		try {
			Connection c = Conexion.getConnection();

			stmt = c.prepareStatement(query);
			stmt.setString(1, descripcion);
			ResultSet rs = stmt.executeQuery();
			// Obtengo la cantidad de proveedores con ese rut
			while (rs.next()) {
				id = rs.getLong("product_id");
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return id;
	}


	
	public List<Articulo> buscarArticulo(String descripcion) {


		return null;

	}

	@Override
	public Date obtenerFechaUltimoPedido() throws Excepciones {

		Date ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT order_date FROM orders_dusa"
				+ " GROUP BY order_date" + " ORDER BY order_date DESC"
				+ " LIMIT 1;";
		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				Timestamp time = rs.getTimestamp("order_date");
				ret = new Date(time.getTime());
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {

			e.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	@Override
	public List<DTBusquedaArticuloSolr> buscarArticulosSolr(String busqueda)
			throws Excepciones {
		List<DTBusquedaArticuloSolr> listaArticulos = new ArrayList<DTBusquedaArticuloSolr>();

		String urlString = "http://localhost:8080/solr";
		SolrServer solr = new HttpSolrServer(urlString);
		SolrQuery parameters = new SolrQuery();
		parameters.setRequestHandler("/articulos/select");
		String regexpBusqueda = "*" + busqueda + "*";
		parameters.set("q", "KEY1:" + regexpBusqueda + " KEY2:"
				+ regexpBusqueda + " KEY3:" + regexpBusqueda
				+ " CRITERIO_INTERNO:" + regexpBusqueda + " DESCRIPTION:"
				+ regexpBusqueda + " BARCODE:" + regexpBusqueda + " DROGAS: "
				+ regexpBusqueda + " PRESENTATION: " + regexpBusqueda
				+ " ACCIONES_TERAPEUTICAS: " + regexpBusqueda + " MARCA: "
				+ regexpBusqueda);
		parameters.set("wt", "json");
		parameters
				.set("fl",
						"DESCRIPTION id BARCODE DROGAS PRESENTATION ACCIONES_TERAPEUTICAS MARCA");
		parameters.set("start", 0);
		parameters.set("rows", 100);
		//parameters.set("sort", "DESCRIPTION DESC");

		try {
			SolrDocumentList response = solr.query(parameters).getResults();
			
			Long cant = response.getNumFound();
			if (cant > 100) {
				cant = (long) 100;
			}
			for (int i = 0; i < cant; i++) {
				

				SolrDocument item = response.get(i);

				DTBusquedaArticuloSolr articulo = new DTBusquedaArticuloSolr();
				articulo.setIdArticulo(Integer.parseInt(item
						.getFieldValue("id").toString()));
				
				if (item.getFieldValue("BARCODE") != null) {
					
					articulo.setCodigoBarras(item.getFieldValue("BARCODE")
							.toString());
				}
				articulo.setDescripcion(item.getFieldValue("DESCRIPTION")
						.toString());
				if (item.getFieldValue("DROGAS") == null) {
					articulo.setDroga("");
				} else {
					articulo.setDroga(item.getFieldValue("DROGAS").toString()
							.replace("[", "").replace("]", ""));
				}
				if (item.getFieldValue("PRESENTATION") == null) {
					articulo.setPresentacion("");
				} else {
					articulo.setPresentacion(item.getFieldValue("PRESENTATION")
							.toString());
				}
				if (item.getFieldValue("ACCIONES_TERAPEUTICAS") == null) {
					articulo.setAccionesTerapeuticas("");
				} else {
					articulo.setAccionesTerapeuticas(item
							.getFieldValue("ACCIONES_TERAPEUTICAS").toString()
							.replace("[", "").replace("]", ""));
				}
				if (item.getFieldValue("MARCA") == null) {
					articulo.setMarca("");
				} else {
					articulo.setMarca(item.getFieldValue("MARCA").toString());
				}
				listaArticulos.add(articulo);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}

		return listaArticulos;
	}

	@Override
	public List<DTBusquedaArticuloSolr> buscarArticulosSolr(String busqueda,
			long proveedor) throws Excepciones {
		List<DTBusquedaArticuloSolr> listaArticulos = new ArrayList<DTBusquedaArticuloSolr>();

		String urlString = "http://localhost:8080/solr";
		SolrServer solr = new HttpSolrServer(urlString);
		SolrQuery parameters = new SolrQuery();
		parameters.setRequestHandler("/articulos/select");
		String regexpBusqueda = "*" + busqueda + "*";
		parameters.set("q", "KEY1:" + regexpBusqueda + " KEY2:"
				+ regexpBusqueda + " KEY3:" + regexpBusqueda
				+ " CRITERIO_INTERNO:" + regexpBusqueda + " DESCRIPTION:"
				+ regexpBusqueda + " BARCODE:" + regexpBusqueda + " DROGAS: "
				+ regexpBusqueda + " PRESENTATION: " + regexpBusqueda
				+ " ACCIONES_TERAPEUTICAS: " + regexpBusqueda + " MARCA: "
				+ regexpBusqueda + " SUPPLIER_DATA: " + "*" + busqueda);
		parameters.set("wt", "json");
		parameters
				.set("fl",
						"DESCRIPTION id BARCODE DROGAS PRESENTATION ACCIONES_TERAPEUTICAS MARCA SUPPLIER_DATA");
		parameters.set("start", 0);
		parameters.set("rows", 100);
		parameters.set("fq", "SUPPLIER_DATA: \"" + proveedor + "#*\"");
		//parameters.set("sort", "DESCRIPTION DESC");

		try {
			SolrDocumentList response = solr.query(parameters).getResults();
			
			Long cant = response.getNumFound();
			if (cant > 100) {
				cant = (long) 100;
			}
			for (int i = 0; i < cant; i++) {
				

				SolrDocument item = response.get(i);

				DTBusquedaArticuloSolr articulo = new DTBusquedaArticuloSolr();
				articulo.setIdArticulo(Integer.parseInt(item
						.getFieldValue("id").toString()));
				if(item.getFieldValue("BARCODE") != null)
					articulo.setCodigoBarras(item.getFieldValue("BARCODE")
							.toString());
				articulo.setDescripcion(item.getFieldValue("DESCRIPTION")
						.toString());
				if (item.getFieldValue("DROGAS") == null) {
					articulo.setDroga("");
				} else {
					articulo.setDroga(item.getFieldValue("DROGAS").toString()
							.replace("[", "").replace("]", ""));
				}
				if (item.getFieldValue("PRESENTATION") == null) {
					articulo.setPresentacion("");
				} else {
					articulo.setPresentacion(item.getFieldValue("PRESENTATION")
							.toString());
				}
				if (item.getFieldValue("ACCIONES_TERAPEUTICAS") == null) {
					articulo.setAccionesTerapeuticas("");
				} else {
					articulo.setAccionesTerapeuticas(item
							.getFieldValue("ACCIONES_TERAPEUTICAS").toString()
							.replace("[", "").replace("]", ""));
				}
				if (item.getFieldValue("MARCA") == null) {
					articulo.setMarca("");
				} else {
					articulo.setMarca(item.getFieldValue("MARCA").toString());
				}
				String data_proveedor = item.getFieldValue("SUPPLIER_DATA")
						.toString().replace("[", "").replace("]", "");
				String[] data = data_proveedor.split(",");
				int j = 0;
				while ((j < data.length)
						&& (!data[j].split("#")[0].trim().equals(
								String.valueOf(proveedor)))) {
					j++;
				}
				if (j < data.length) {
					articulo.setNumeroProducto_proveedor(Integer
							.parseInt(data[j].split("#")[1].trim()));
				} else {
					articulo.setNumeroProducto_proveedor(0);
				}

				
				
				listaArticulos.add(articulo);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}

		return listaArticulos;
	}

	@Override
	public void buscarArticulosId(DTBusquedaArticulo articulo)
			throws Excepciones {
		PreparedStatement stmt = null;

		String query = "SELECT p.UNIT_PRICE, p.SALE_PRICE, p.LIST_COST, p.LAST_COST, p.AVG_COST, p.SALE_CODE, p.PRODUCT_TYPE, p.STOCK, p.TAX_TYPE_ID, ";
		query += "t.* ";
		query += "FROM PRODUCTS p "; 
		query += "INNER JOIN TAX_TYPES t ON p.TAX_TYPE_ID = t.TAX_TYPE_ID ";
		query += "WHERE p.PRODUCT_ID = ?";
		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			stmt.setInt(1, articulo.getIdArticulo());
			ResultSet rs = stmt.executeQuery();
			// Obtengo la cantidad de proveedores con ese rut
			while (rs.next()) {
				articulo.setTipoDeArticulo(model.Enumerados
						.descripcionTipoArticuloAbreviado(rs
								.getString("PRODUCT_TYPE")));
				articulo.setControlDeVenta(model.Enumerados
						.descripcionTipoVenta(rs.getString("SALE_CODE")));
				articulo.setPrecioDeVenta(rs.getBigDecimal("UNIT_PRICE"));
				articulo.setPrecioPublico(rs.getBigDecimal("SALE_PRICE"));
				articulo.setCostoDeLista(rs.getBigDecimal("LIST_COST"));
				articulo.setCostoReal(rs.getBigDecimal("LAST_COST"));
				articulo.setCostoPonderado(rs.getBigDecimal("AVG_COST"));
				articulo.setStock(rs.getLong("STOCK"));
				
				TipoIva ti = new TipoIva();
				ti.setTipoIVA(rs.getString("tax_type_id").charAt(0));
				ti.setValorIVA(rs.getBigDecimal("iva_value"));
				ti.setValorTributo(rs.getBigDecimal("tax_value"));
				ti.setResguardoIVA(rs.getBigDecimal("iva_voucher"));
				ti.setResguardoIRAE(rs.getBigDecimal("irae_voucher"));
				ti.setIndicadorFacturacion(rs.getInt("billing_indicator"));
				articulo.setTipoIva(ti);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}

	}

	@Override
	public DTProducto getDatosArticuloVenta(int idArticulo) throws Excepciones {
		DTProducto articulo = new DTProducto();
		PreparedStatement stmt = null;
		String query = "SELECT SALE_PRICE, IS_PSYCHOTROPIC, IS_NARCOTIC, STOCK, IVA_VALUE, TAX_VALUE, BILLING_INDICATOR, RECIPE_PRICE, RECIPE_DISCOUNT, COMERCIALNAME "
				+ "FROM PRODUCTS p "
				+ "INNER JOIN tax_types tt ON p.tax_type_id = tt.tax_type_id "
				+ "LEFT JOIN suppliers s ON s.supplier_id = p.brand_id "

				+ "WHERE PRODUCT_ID = ?";
		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			stmt.setInt(1, idArticulo);
			ResultSet rs = stmt.executeQuery();
			// Obtengo la cantidad de proveedores con ese rut
			while (rs.next()) {
				
				articulo.setPrecioVenta(rs.getBigDecimal("SALE_PRICE"));
				articulo.setPsicofarmaco(rs.getBoolean("IS_PSYCHOTROPIC"));
				articulo.setEstupefaciente(rs.getBoolean("IS_NARCOTIC"));
				articulo.setStock(rs.getInt("STOCK"));
				articulo.setIrae(rs.getBigDecimal("TAX_VALUE"));
				articulo.setIva(rs.getBigDecimal("IVA_VALUE"));
				articulo.setIndicadorFacturacion(rs.getInt("BILLING_INDICATOR"));
				articulo.setPrecioReceta(rs.getBigDecimal("RECIPE_PRICE"));
				articulo.setDescuentoReceta(rs.getBigDecimal("RECIPE_DISCOUNT"));
				if (articulo.getDescuentoReceta() == null){
					articulo.setDescuentoReceta(new BigDecimal(0));
				}
				articulo.setDescuentoReceta(articulo.getDescuentoReceta().multiply(new BigDecimal(100)));
				articulo.setLaboratorio(rs.getString("COMERCIALNAME"));

			}  
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}

		articulo.setCantidad(0);
		articulo.setRecetaBlanca(false);

		return articulo;
	}
	
	@Override
	public DTProducto getDatosArticuloVentaPorCodigo(String codigo) throws Excepciones {
		DTProducto articulo = null;
		PreparedStatement stmt = null;
		String query = "SELECT BARCODE, PRESENTATION, PRODUCT_ID, SALE_PRICE, IS_PSYCHOTROPIC, IS_NARCOTIC, STOCK, IVA_VALUE, TAX_VALUE, BILLING_INDICATOR, RECIPE_PRICE, RECIPE_DISCOUNT, P.DESCRIPTION, COMERCIALNAME "
				+ "FROM PRODUCTS p "
				+ "INNER JOIN tax_types tt ON p.tax_type_id = tt.tax_type_id "
				+ "LEFT JOIN suppliers s ON s.supplier_id = p.brand_id "
				+ "WHERE BARCODE = '" + codigo + "'";
		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				articulo = new DTProducto();
				articulo.setProductId(rs.getInt("PRODUCT_ID"));
				articulo.setPrecioVenta(rs.getBigDecimal("SALE_PRICE"));
				articulo.setDescripcion(rs.getString("DESCRIPTION"));
				articulo.setPresentacion(rs.getString("PRESENTATION"));
				articulo.setCodigoBarras(rs.getString("BARCODE"));
				articulo.setPsicofarmaco(rs.getBoolean("IS_PSYCHOTROPIC"));
				articulo.setEstupefaciente(rs.getBoolean("IS_NARCOTIC"));
				articulo.setStock(rs.getInt("STOCK"));
				articulo.setIrae(rs.getBigDecimal("TAX_VALUE"));
				articulo.setIva(rs.getBigDecimal("IVA_VALUE"));
				articulo.setIndicadorFacturacion(rs.getInt("BILLING_INDICATOR"));
				articulo.setPrecioReceta(rs.getBigDecimal("RECIPE_PRICE"));
				articulo.setDescuentoReceta(rs.getBigDecimal("RECIPE_DISCOUNT"));
				if (articulo.getDescuentoReceta() == null){
					articulo.setDescuentoReceta(new BigDecimal(0));
				}
				articulo.setDescuentoReceta(articulo.getDescuentoReceta().multiply(new BigDecimal(100)));
				articulo.setLaboratorio(rs.getString("COMERCIALNAME"));
				articulo.setCantidad(0);
				articulo.setRecetaBlanca(false);
				articulo.setRecetaVerde(false);
				articulo.setRecetaVerde(false);
			}  
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
		return articulo;
	}

	@Override
	public void fullImportSolr() throws Excepciones {
		String urlString = "http://localhost:8080/solr";
		SolrServer solr = new HttpSolrServer(urlString);
		SolrQuery parameters = new SolrQuery();
		parameters.setRequestHandler("/articulos/dataimport");
		parameters.setParam("command", "full-import");
		parameters.setParam("entity", "stock");
		parameters.setParam("clean", true);
		parameters.setParam("commit", true);

		QueryResponse response;
		try {
			response = solr.query(parameters);
			
		} catch (SolrServerException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}

	@Override
	public void deltaImportSolr() throws Excepciones {
		String urlString = "http://localhost:8080/solr";
		SolrServer solr = new HttpSolrServer(urlString);
		SolrQuery parameters = new SolrQuery();
		parameters.setRequestHandler("/articulos/dataimport");
		parameters.setParam("command", "delta-import");
		parameters.setParam("entity", "stock");
		parameters.setParam("clean", false);
		parameters.setParam("commit", true);

		SolrDocumentList response;
		try {
			response = solr.query(parameters).getResults();
			
		} catch (SolrServerException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}

	/**
	 * @author Guille
	 */
	@Override
	public long getStockMinimo(long idArticulo) throws Excepciones {

		long sMinimo = 0;

		String query = "SELECT minimum_stock FROM products WHERE product_id="
				+ idArticulo + ";";
		try {

			Connection c = Conexion.getConnection();
			PreparedStatement stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				sMinimo = rs.getLong(1);
			}

			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			// Excepcion personalizada
			e.printStackTrace();
			throw new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA);
		}

		return sMinimo;
	}

	/**
	 * @author santiago
	 * @throws Excepciones
	 */
	@Override
	public int getStock(long idArticulo) throws Excepciones {
		// consulta que obtiene el stock de un articulo

		PreparedStatement stmt = null;

		String query = "SELECT stock  FROM products WHERE product_id = "
				+ idArticulo + ";";
		int ret = 0;
		try {

			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				ret = (int) rs.getLong(1);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	/**
	 * @author Guille
	 * @throws Excepciones
	 */
	@Override
	public void persistirPedido(Pedido p) throws Excepciones {
		// TODO Auto-generated method stub

		PreparedStatement stmt = null;

		String query = "INSERT INTO ORDERS_DUSA "
				+ "(USERNAME, PAYMENT_TYPE, ORDER_DATE) VALUES "
				+ " (?, ?, LOCALTIMESTAMP) RETURNING ORDER_DUSA_ID;";
		try {

			Connection c = Conexion.getConnection();
			c.setAutoCommit(false);
			try {

				stmt = c.prepareStatement(query);
				// stmt.setInt(1, p.getIdUsuario());
				stmt.setString(1, p.getUsuario().getNombre());
				stmt.setString(2, "" + p.getFormaDePago() + "");

				ResultSet rs = stmt.executeQuery();
				// Obtengo la clave del pedido persistido
				long pedidoID = 0;
				while (rs.next()) {
					pedidoID = rs.getLong(1);
				}
				// Cierro canal y su dependencia, porque voy abrir uno nuevo.
				rs.close();
				stmt.close();
				// Para cada linea pedido asociado inserto una fila en
				// ORDER_DUSA_DETAILS
				for (Iterator<LineaPedido> iter = p.getLineas().iterator(); iter
						.hasNext();) {

					LineaPedido lPedido = iter.next();

					query = "INSERT INTO ORDER_DUSA_DETAILS "
							+ "(ORDER_DUSA_ID, PRODUCT_ID, QUANTITY) VALUES "
							+ " (?, ?, ?);";
					stmt = c.prepareStatement(query);
					stmt.setLong(1, pedidoID);
					stmt.setLong(2, lPedido.getIdArticulo().longValue());
					stmt.setInt(3, lPedido.getCantidad());

					stmt.executeUpdate();
				}
				// commit de los cambios y cerrar los canales
				c.commit();

				stmt.close();
				c.close();
			} catch (Exception e) {
				// Regreso al estado anterior de la base y lanzo la excepcion
				// correspondiente

				c.rollback();
				e.printStackTrace();
				throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
						Excepciones.ERROR_SISTEMA));
			}
		} catch (Excepciones e) {
			// Paso las excepciones personalizadas.
			throw e;
		} catch (Exception e) {
			// Error de conexión con la base de datos
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
	}

	/**
	 * @author santiago
	 */
	@Override
	public List<Long> obtenerIdTodosLosArticulos() throws Excepciones {
		List<Long> idArts = new ArrayList<Long>();
		PreparedStatement stmt = null;
		String query = "SELECT p.product_id "
				+ "FROM products p "
				+ "INNER JOIN products_suppliers ps ON p.product_id = ps.product_id "
				+ "WHERE ps.supplier_id = ? AND p.status = ?;";

		try {

			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			stmt.setInt(1, Enumerados.infoDUSA.proveedorID);
			stmt.setBoolean(2, true);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				idArts.add(rs.getLong(1));
			}

			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}

		return idArts;
	}

	/**
	 * @author Guille
	 */
	@Override
	public boolean existeArticuloDeDUSA(Long idArticulo) throws Excepciones {

		int cant = 0;
		PreparedStatement stmt = null;
		String query = "SELECT count(*) AS cant"
				+ "FROM products_suppliers ps "
				+ "WHERE ps.product_id = ? AND " + "ps.supplier_id = ?;";

		try {

			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			stmt.setLong(1, idArticulo);
			stmt.setInt(2, Enumerados.infoDUSA.proveedorID); // Hay que ver cual
																// es para
																// hardcodearlo
																// en un lugar
																// solo.
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				cant = rs.getInt("cant");
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
		return cant > 0;
	}

	@Override
	public List<Droga> obtenerDrogas() throws Excepciones {
		List<Droga> ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT d.drug_id, d.description " + "FROM DRUGS d ";

		try {
			Connection c = Conexion.getConnection();
			c.setAutoCommit(false);
			stmt = c.prepareStatement(query);
			stmt.setFetchSize(100);
			ResultSet rs = stmt.executeQuery();
			ret = new ArrayList<Droga>();
			while (rs.next()) {
				Droga nuevo = new Droga();
				nuevo.setIdDroga(rs.getLong("drug_id"));
				nuevo.setDescripcion(rs.getString("description"));
				ret.add(nuevo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	@Override
	public List<AccionTer> obtenerAccionesTerapeuticas() throws Excepciones {
		List<AccionTer> ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT ta.therapeutic_action_id, ta.description "
				+ "FROM THERAPEUTIC_ACTIONS ta ";

		try {
			Connection c = Conexion.getConnection();
			c.setAutoCommit(false);
			stmt = c.prepareStatement(query);
			stmt.setFetchSize(100);
			ResultSet rs = stmt.executeQuery();
			ret = new ArrayList<AccionTer>();
			while (rs.next()) {
				AccionTer nuevo = new AccionTer();
				nuevo.setIdAccionTer(rs.getLong("therapeutic_action_id"));
				nuevo.setDescripcion(rs.getString("description"));
				ret.add(nuevo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	/**
	 * @author Guille
	 */
	@Override
	public Articulo obtenerArticuloConId(long idArticulo) throws Excepciones {
		// TODO Auto-generated method stub

		Articulo articulo = null;
		Map<Integer, DTProveedor> ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT * "
				+ "FROM products p INNER JOIN products_suppliers ps ON p.product_id = ps.product_id "
				+ "INNER JOIN (SELECT supplier_id, comercialname FROM suppliers) as s ON s.supplier_id = ps.supplier_id "
				+ "WHERE p.product_id = ?;";
		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			stmt.setLong(1, idArticulo);
			ResultSet rs = stmt.executeQuery();
			ret = new HashMap<Integer, DTProveedor>();
			while (rs.next()) {

				if (articulo == null) {

					articulo = new Articulo();

					articulo.setIdArticulo(rs.getLong("product_id"));
					String aux = rs.getString("product_type");
					if (aux != null) {
						articulo.setTipoArticulo(aux.charAt(0));
					}
					articulo.setDescripcion(rs.getString("description"));
					articulo.setClave1(rs.getString("key1"));
					articulo.setClave2(rs.getString("key2"));
					articulo.setClave3(rs.getString("key3"));
					articulo.setEsPsicofarmaco(rs.getBoolean("is_psychotropic"));
					articulo.setEsEstupefaciente(rs.getBoolean("is_narcotic"));
					articulo.setEsHeladera(rs.getBoolean("is_refrigerator"));
					aux = rs.getString("sale_code");
					if (aux != null) {
						articulo.setCodigoVenta(aux.charAt(0));
					}
					aux = rs.getString("authorization_type");
					if (aux != null) {
						articulo.setTipoAutorizacion(aux.charAt(0));
					}
					articulo.setPrecioUnitario(rs.getBigDecimal("unit_price"));
					articulo.setPrecioVenta(rs.getBigDecimal("sale_price"));
					articulo.setPorcentajePrecioVenta(rs
							.getBigDecimal("sale_price_porcentage"));
					articulo.setCostoLista(rs.getBigDecimal("list_cost"));
					articulo.setCostoOferta(rs.getBigDecimal("offer_cost"));
					articulo.setUltimoCosto(rs.getBigDecimal("last_cost"));
					articulo.setCostoPromedio(rs.getBigDecimal("avg_cost"));
					// BigDecimal auxDecimal = rs.getBigDecimal("TAX_TYPE_ID");
					// if (auxDecimal != null) {
					// articulo.getTipoIva().setTipoIVA(auxDecimal.intValue());
					// }
					articulo.setCodigoBarras(rs.getString("barcode"));
					Timestamp timestamp = rs.getTimestamp("nearest_due_date");
					if (timestamp != null) {

						articulo.setVencimientoMasCercano(new java.util.Date(
								timestamp.getTime()));
					}
					articulo.setStock(rs.getLong("stock"));
					articulo.setStockMinimo(rs.getLong("minimum_stock"));
					articulo.setStatus(rs.getBoolean("status"));
				}

				DTProveedor dtProveedor = new DTProveedor();
				dtProveedor.setIdProveedor(rs.getInt("supplier_id"));
				dtProveedor
						.setCodigoIdentificador(rs.getLong("product_number"));
				dtProveedor.setNombreComercial(rs.getString("comercialname"));
				dtProveedor.setIdLinea(rs.getString("line_id"));

				ret.put(dtProveedor.getIdProveedor(), dtProveedor);
			}
			if (articulo != null) {

				articulo.setProveedores(ret);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}

		return articulo;
	}

	@Override
	public List<TipoIva> obtenerTiposIva() throws Excepciones {
		List<TipoIva> ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT t.tax_type_id, t.description "
				+ "FROM TAX_TYPES t ";

		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ret = new ArrayList<TipoIva>();
			while (rs.next()) {
				TipoIva nuevo = new TipoIva();
				nuevo.setTipoIVA(rs.getString("tax_type_id").charAt(0));
				nuevo.setDescripcion(rs.getString("description"));
				ret.add(nuevo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	@Override
	public void persistirTiposIva(List<TipoIva> lista) throws Excepciones {
		PreparedStatement stmt = null;
		try {
			Connection c = Conexion.getConnection();

			for (TipoIva t : lista) {
				String query = "INSERT INTO TAX_TYPES "
						+ "(TAX_TYPE_ID, DESCRIPTION, RATE_TYPE, BILLING_INDICATOR, "
						+ "IVA_VALUE, TAX_VALUE, IVA_VOUCHER, IRAE_VOUCHER, STATUS) VALUES "
						+ " (?, ?, ?, ?, ?, ?, ?, ?, TRUE);";
				stmt = c.prepareStatement(query);
				stmt.setString(1, String.valueOf(t.getTipoIVA()));
				stmt.setString(2, t.getDescripcion());
				stmt.setInt(3, t.getTipoTasa());
				stmt.setInt(4, t.getIndicadorFacturacion());
				stmt.setBigDecimal(5, t.getValorIVA());
				stmt.setBigDecimal(6, t.getValorTributo());
				stmt.setBigDecimal(7, t.getResguardoIVA());
				stmt.setBigDecimal(8, t.getResguardoIRAE());
				stmt.executeUpdate();
			}
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}

	/**
	 * @author Guille
	 */
	@Override
	public void modificarStock(long idArticulo, long nuevoValor)
			throws Excepciones {

		PreparedStatement stmt = null;
		try {
			Connection c = Conexion.getConnection();

			String query = "UPDATE PRODUCTS SET STOCK = ? "
					+ "WHERE PRODUCT_ID = ?;";

			stmt = c.prepareStatement(query);
			stmt.setLong(1, nuevoValor);
			stmt.setLong(2, idArticulo);

			stmt.executeUpdate();

			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}

	/**
	 * @author Seba
	 */
	@Override
	public void modificarStock(long[] idsArticulo, long[] nuevosValores)  throws Exception {
		PreparedStatement stmt = null;
		Connection c = Conexion.getConnection();
		try {
			for (int i = 0; i < idsArticulo.length; i++) {
				String query = "UPDATE PRODUCTS SET STOCK = ? "
						+ "WHERE PRODUCT_ID = ?;";

				stmt = c.prepareStatement(query);
				stmt.setLong(1, nuevosValores[i]);
				stmt.setLong(2, idsArticulo[i]);

				stmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		} finally{
			stmt.close();
			c.close();
		}
	}
	
	@Override
	public void movimientoStock(String usuario, long aticuloID, long cantidad,
			char tipoMovimiento, String motivo)
			throws Excepciones {
		
		PreparedStatement stmt = null;
		try {
			Connection c = Conexion.getConnection();

			String query = "INSERT INTO STOCK_MOVEMENTS "
					+ "(USERNAME, PRODUCT_ID, QUANTITY, MOVEMENT_TYPE, "
					+ "MOTIVE, MOVEMENT_DATE) VALUES "
					+ " (?, ?, ?, ?, ?, LOCALTIMESTAMP);";
			
			stmt = c.prepareStatement(query);
			stmt.setString(1, usuario);
			stmt.setLong(2, aticuloID);
			stmt.setLong(3, cantidad);
			stmt.setString(4, String.valueOf(tipoMovimiento));
			stmt.setString(5, motivo);
			
			stmt.executeUpdate();
			
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}
	
	@Override
	public void movimientoStockCompra(Orden orden) throws Excepciones {
		
		PreparedStatement stmt = null;
		try {
			Connection c = Conexion.getConnection();

			String query = "INSERT INTO STOCK_MOVEMENTS "
					+ "(USERNAME, PRODUCT_ID, QUANTITY, MOVEMENT_TYPE, "
					+ "MOTIVE, MOVEMENT_DATE) VALUES "
					+ " (?, ?, ?, ?, ?, LOCALTIMESTAMP);";
			
			Iterator<OrdenDetalle> it = orden.getDetalle().iterator();
			while (it.hasNext()) {
				OrdenDetalle ordenDetalle = (OrdenDetalle) it.next();
				
				stmt = c.prepareStatement(query);
				stmt.setString(1, orden.getNombreUsuario());
				stmt.setLong(2, ordenDetalle.getProductId());
				stmt.setLong(3, ordenDetalle.getCantidad());
				stmt.setString(4, String.valueOf(Enumerados.tipoMovimientoDeStock.aumentoStock));
				String motivo = "COMPRA " + orden.getIdProveedor() + " - " + orden.getTipoCFE() + " " + orden.getSerieCFE() + " " + orden.getNumeroCFE();
				if(motivo.length() > 250)
					motivo = motivo.substring(250);
				stmt.setString(5, motivo);
				
				stmt.executeUpdate();
				
				stmt.close();
			}
			
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}

	@Override
	public void actualizarStockCompra(List<OrdenDetalle> detalles)
			throws Excepciones {

		PreparedStatement stmt = null;
		try {
			Connection c = Conexion.getConnection();
			String  query = "UPDATE products SET stock = stock + ?, last_cost = ?, avg_cost = ? ";
					query += "WHERE product_id = ?;";
					
			Iterator<OrdenDetalle> it = detalles.iterator();
			while (it.hasNext()) {
				OrdenDetalle ordenDetalle = it.next();
				stmt = c.prepareStatement(query);
				stmt.setInt(1, ordenDetalle.getCantidad());
				
				BigDecimal descuento = ordenDetalle.getDescuento()
						.subtract(new BigDecimal(100)).abs()
						.divide(new BigDecimal(100));

				BigDecimal total = ordenDetalle.getPrecioUnitario().multiply(descuento);
				
				BigDecimal tributo = total.multiply(
						ordenDetalle.getTipoIVA().getValorTributo()
								.divide(new BigDecimal(100)));

				total = total.add(tributo);

				BigDecimal iva = total.multiply(ordenDetalle.getTipoIVA().getValorIVA()
						.divide(new BigDecimal(100)));
				
				total = total.add(iva);

				stmt.setBigDecimal(2, total);
				
				// Precio ponderado promedio
				// avg_cost = ((avg_cost * stock) + (costo_real * cant)) / (stock + cant)
				BigDecimal pondActual = ordenDetalle.getAvg_cost().multiply( new BigDecimal(ordenDetalle.getStock()));
				BigDecimal pondNuevo = total.multiply(new BigDecimal(ordenDetalle.getCantidad()));
				BigDecimal sumAvg =  pondActual.add(pondNuevo);
				long totArts = ordenDetalle.getStock() + ordenDetalle.getCantidad();
				stmt.setBigDecimal(3, sumAvg.divide(new BigDecimal(totArts), 2, RoundingMode.HALF_UP));
				
				stmt.setLong(4, ordenDetalle.getProductId());
				
				stmt.executeUpdate();
				stmt.close();
			}

			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}

	public void actualizarPrecioYEstadoDeArticulo(Articulo articulo)throws Excepciones{
		
		//UPDATE PRODUCTS SET status='f',unit_price='100' WHERE product_id=13;
		String query = "UPDATE PRODUCTS SET ";
		String status = articulo.isStatus() ? "'t'" : "'f'";
		query += "status=" + status + ",";
		query += "unit_price='" + articulo.getPrecioUnitario() + "' ";
		query += ",LAST_MODIFIED = LOCALTIMESTAMP ";
		query += "WHERE PRODUCT_ID =" + articulo.getIdArticulo() +";";
		
		Connection c = null;
		
		try {
			c = Conexion.getConnection(); 
			PreparedStatement stmt = null;
			stmt = c.prepareStatement(query);
			stmt.executeUpdate();
			stmt.close();
			c.close();

		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void modificarArticulo(DTModificacionArticulo dataArticulo) throws Excepciones {          
		NamedParameterStatement stmt = null;

		Articulo articulo = dataArticulo.getArticulo();
		
		String query = "UPDATE PRODUCTS SET ";

		if (articulo.isIdMarcaModificado()) {
			query += "BRAND_ID = :brand_id, ";
		}
		if (articulo.isTipoArticuloModificado()) {
			query += "PRODUCT_TYPE = :product_type, ";
		}
		if (articulo.isDescripcionModificado()) {
			query += "DESCRIPTION = :description, ";
		}
		if (articulo.isPresentacionModificado()) {
			query += "PRESENTATION = :presentation, ";
		}
		if (articulo.isClave1Modificado()) {
			query += "KEY1 = :key1, ";
		}
		if (articulo.isClave2Modificado()) {
			query += "KEY2 = :key2, ";
		}
		if (articulo.isClave3Modificado()) {
			query += "KEY3 = :key3, ";
		}
		if (articulo.isEsPsicofarmacoModificado()) {
			query += "IS_PSYCHOTROPIC = :isPsychotropic, ";
		}
		if (articulo.isEsEstupefacienteModificado()) {
			query += "IS_NARCOTIC = :isNarcotic, ";
		}
		if (articulo.isEsHeladeraModificado()) {
			query += "IS_REFRIGERATOR = :isRefrigerator, ";
		}
		if (articulo.isCodigoVentaModificado()) {
			query += "SALE_CODE = :sale_code, ";
		}
		if (articulo.isTipoAutorizacionModificado()) {
			query += "AUTHORIZATION_TYPE = :authorization_type, ";
		}
		if (articulo.isPrecioUnitarioModificado()) {
			query += "UNIT_PRICE = :unit_price, ";
			// Si se modifica el precio unitario tiene que cambiar el valor en
			// fechaUltimoPrecio
			query += "LAST_PRICE_DATE = LOCALTIMESTAMP, ";
		}
		if (articulo.isPrecioVentaModificado()) {
			query += "SALE_PRICE = :sale_price, ";
		}
		if (articulo.isPorcentajePrecioVentaModificado()) {
			query += "SALE_PRICE_PORCENTAGE = :sale_price_porcentage, ";
		}
		if (articulo.isPrecioConRecetaModificado()) {
			query += "RECIPE_PRICE = :recipe_price, ";
		}
		if (articulo.isPorcentajeDescuentoRecetaModificado()) {
			query += "RECIPE_DISCOUNT = :recipe_discount, ";
		}
		if (articulo.isCostoListaModificado()) {
			query += "LIST_COST = :list_cost, ";
		}
		if (articulo.isCostoOfertaModificado()) {
			query += "OFFER_COST = :offer_cost, ";
		}
		if (articulo.isUltimoCostoModificado()) {
			query += "LAST_COST = :last_cost, ";
		}
		if (articulo.isCostoPromedioModificado()) {
			query += "AVG_COST = :avg_cost, ";
		}
		if (articulo.isTipoIvaModificado()) {
			query += "TAX_TYPE_ID = :tax_type_id, ";
		}
		if (articulo.isCodigoBarrasModificado()) {
			query += "BARCODE = :barcode, ";
		}
		if (articulo.isVencimientoMasCercanoModificado()) {
			query += "NEAREST_DUE_DATE = :nearest_due_date, ";
		}
		if (articulo.isStockMinimoModificado()) {
			query += "MINIMUM_STOCK = :minimum_stock, ";
		}
		if (articulo.isUsuarioModificado()) {
			query += "USERNAME = :username, ";
		}
		

		// Seteo fecha de última modificación a la actual
		query += "LAST_MODIFIED = LOCALTIMESTAMP ";
		query += "WHERE PRODUCT_ID = :product_id;";

		Connection c;
		try {
			c = Conexion.getConnection();
			c.setAutoCommit(false);
			try {
				// Seteo los datos a modificar en la bd
				stmt = new NamedParameterStatement(c, query);

				if (articulo.isIdMarcaModificado()) {
					if (articulo.getIdMarca() != 0) {
						stmt.setInt("brand_id", articulo.getIdMarca());
					} else {
						stmt.setNull("brand_id", java.sql.Types.INTEGER);
					}
				}
				if (articulo.isTipoArticuloModificado()) {
					stmt.setChar("product_type", articulo.getTipoArticulo());
				}
				if (articulo.isDescripcionModificado()) {
					stmt.setString("description", articulo.getDescripcion());
				}
				if (articulo.isPresentacionModificado()) {
					stmt.setString("presentation", articulo.getPresentacion());
				}
				if (articulo.isClave1Modificado()) {
					stmt.setString("key1", articulo.getClave1());
				}
				if (articulo.isClave2Modificado()) {
					stmt.setString("key2", articulo.getClave2());
				}
				if (articulo.isClave3Modificado()) {
					stmt.setString("key3", articulo.getClave3());
				}
				if (articulo.isEsPsicofarmacoModificado()) {
					stmt.setBoolean("isPsychotropic",
							articulo.isEsPsicofarmaco());
				}
				if (articulo.isEsEstupefacienteModificado()) {
					stmt.setBoolean("isNarcotic", articulo.isEsEstupefaciente());
				}
				if (articulo.isEsHeladeraModificado()) {
					stmt.setBoolean("isRefrigerator", articulo.isEsHeladera());
				}
				if (articulo.isCodigoVentaModificado()) {
					if (articulo.getCodigoVenta() != 0x00) {
						stmt.setChar("sale_code", articulo.getCodigoVenta());
					} else {
						stmt.setNull("sale_code", java.sql.Types.CHAR);
					}
				}
				if (articulo.isTipoAutorizacionModificado()) {
					stmt.setChar("authorization_type",
							articulo.getTipoAutorizacion());
				}
				if (articulo.isPrecioUnitarioModificado()) {
					stmt.setBigDecimal("unit_price",
							articulo.getPrecioUnitario());
				}
				if (articulo.isPrecioVentaModificado()) {
					stmt.setBigDecimal("sale_price", articulo.getPrecioVenta());
				}
				if (articulo.isPorcentajePrecioVentaModificado()) {
					stmt.setBigDecimal("sale_price_porcentage",
							articulo.getPorcentajePrecioVenta());
				}
				if (articulo.isPrecioConRecetaModificado()) {
					stmt.setBigDecimal("recipe_price", articulo.getPrecioConReceta());
				}
				if (articulo.isPorcentajeDescuentoRecetaModificado()) {
					stmt.setBigDecimal("recipe_discount", articulo.getPorcentajeDescuentoReceta());
				}
				if (articulo.isCostoListaModificado()) {
					stmt.setBigDecimal("list_cost", articulo.getCostoLista());
				}
				if (articulo.isCostoOfertaModificado()) {
					stmt.setBigDecimal("offer_cost", articulo.getCostoOferta());
				}
				if (articulo.isUltimoCostoModificado()) {
					stmt.setBigDecimal("last_cost", articulo.getUltimoCosto());
				}
				if (articulo.isCostoPromedioModificado()) {
					stmt.setBigDecimal("avg_cost", articulo.getCostoPromedio());
				}
				if (articulo.isTipoIvaModificado()) {
					if (articulo.getTipoIva() != null) {
						stmt.setChar("tax_type_id", articulo.getTipoIva()
								.getTipoIVA());
					} else {
						stmt.setNull("tax_type_id", java.sql.Types.CHAR);
					}
				}
				if (articulo.isCodigoBarrasModificado()) {
					stmt.setString("barcode", articulo.getCodigoBarras());
				}
				if (articulo.isVencimientoMasCercanoModificado()) {
					stmt.setTimestamp("nearest_due_date",
							new java.sql.Timestamp(articulo
									.getVencimientoMasCercano().getTime()));
				}
				if (articulo.isStockMinimoModificado()) {
					stmt.setLong("minimum_stock", articulo.getStockMinimo());
				}
				if (articulo.isUsuarioModificado()) {
					stmt.setString("username", articulo.getUsuario()
							.getNombre());
				}

				stmt.setLong("product_id", articulo.getIdArticulo());

				stmt.executeUpdate();

				PreparedStatement st = null;
				if (articulo.isProveedoresModificado()) {
					// Elimino los proveedoresABorrar
					query =	"DELETE FROM PRODUCTS_SUPPLIERS WHERE SUPPLIER_ID = ? AND PRODUCT_ID = ?;";
					
					for(DTProveedor proveedor : dataArticulo.getProveedoresABorrar()){
						st = c.prepareStatement(query); 
						st.setInt(1, proveedor.getIdProveedor()); 
						st.setLong(2, articulo.getIdArticulo());
						st.executeUpdate();
					}
					
					// Modifico los proveedoresConCambios
					//TODO
					
					// Agrego los proveedoresNuevos
					query =	"INSERT INTO PRODUCTS_SUPPLIERS " +
							"(SUPPLIER_ID, PRODUCT_ID, PRODUCT_NUMBER, LINE_ID) " +
							"VALUES " + "(?, ?, ?, ?)";
					
					for(DTProveedor proveedor : dataArticulo.getProveedoresNuevos()){ 
						st = c.prepareStatement(query); 
						st.setInt(1, proveedor.getIdProveedor()); 
						st.setLong(2, articulo.getIdArticulo());
						st.setLong(3, proveedor.getCodigoIdentificador());
						st.setString(4, proveedor.getIdLinea());
						st.executeUpdate();
					}
					 
				}
				if (articulo.isDrogasModificado()) {					
					// Elimino las drogasABorrar
					query = "DELETE FROM PRODUCT_DRUGS WHERE PRODUCT_ID = ? AND DRUG_ID = ?;";
					for(long idDroga : dataArticulo.getDrogasABorrar()){
						st = c.prepareStatement(query);
						st.setLong(1, articulo.getIdArticulo());
						st.setLong(2, idDroga);
						st.executeUpdate();
					}
					// Agrego las drogasNuevas
					query = "INSERT INTO PRODUCT_DRUGS (PRODUCT_ID, DRUG_ID) " +
							"VALUES " + "(?, ?)"; 
					for(long idDroga : dataArticulo.getDrogasNuevas()){ 
						st = c.prepareStatement(query);
						st.setLong(1, articulo.getIdArticulo()); 
						st.setLong(2, idDroga);
						st.executeUpdate(); 
					}
				}
				if (articulo.isAccionesTerModificado()) {
					// Elimino las accionesTerABorrar
					query = "DELETE FROM PRODUCT_THERAP_ACTIONS WHERE PRODUCT_ID = ? AND THERAPEUTIC_ACTION_ID = ?;";
					for(long idAccionTer : dataArticulo.getAccionesTerABorrar()){
						st = c.prepareStatement(query);
						st.setLong(1, articulo.getIdArticulo());
						st.setLong(2, idAccionTer);
						st.executeUpdate();
					}
					// Agrego las accionesTerNuevas
					query = "INSERT INTO PRODUCT_THERAP_ACTIONS (PRODUCT_ID, THERAPEUTIC_ACTION_ID) " +
							"VALUES " + "(?, ?)"; 
					for(long idAccionTer : dataArticulo.getAccionesTerNuevas()){ 
						st = c.prepareStatement(query);
						st.setLong(1, articulo.getIdArticulo()); 
						st.setLong(2, idAccionTer);
						st.executeUpdate(); 
					}
				}

				// Commiteo todo y cierro conexion
				c.commit();
				stmt.close();
				c.close();

			} catch (Exception e) {
				// Hago rollback de las cosas y lanzo excepcion
				c.rollback();
				e.printStackTrace();
				throw (new Excepciones("Error sistema",
						Excepciones.ERROR_SISTEMA));
			}
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
	} 

	/**
	 * @author santiago
	 * @param arts
	 * @return
	 * @throws Excepciones 
	 */
	@Override
	public List <Cambio> obtenerCambios(List <Articulo> arts) throws Excepciones{

		Iterator<Articulo> it = arts.iterator();
		List<Cambio> cambios = new ArrayList<Cambio>();
		
		while (it.hasNext()){
			
			Articulo art = it.next();
			

			if (this.existeArticulo(art.getDescripcion())){
				Long id = this.obtenerIdPorDescripcion(art.getDescripcion());
				Articulo artAnt = this.obtenerArticuloConId(id);
				// si el precio unitario disminuyo o fue dado de baja se agrega a los cambios;

				BigDecimal bg = artAnt.getPrecioUnitario().subtract(art.getPrecioUnitario());
				BigDecimal bg2 = new BigDecimal("0.5");
				boolean bajaEnPrecio = bg.abs().compareTo(bg2) > 0;
				boolean dadoDeBaja =  artAnt.isStatus()==true && art.isStatus()==false;
				if (bajaEnPrecio   || dadoDeBaja) {
					art.setIdArticulo(artAnt.getIdArticulo());
					cambios.add(new Cambio(art,artAnt,bajaEnPrecio,dadoDeBaja));
					this.actualizarPrecioYEstadoDeArticulo(art);
		
				}
				
			}
			else{
				
				this.persistirArticulo(art);
			}
		}	
		return cambios;
	}

	@Override
	public Articulo obtenerArticulo(int idArticulo) throws Excepciones {
		Articulo articulo = null;
		Map<Integer, DTProveedor> proveedores = null;
		long[] drogas = null;
		long[] accionesTer = null;
		PreparedStatement stmt = null;
		String query = "SELECT * "
				+ "FROM products p "
				+ "WHERE p.product_id = ?;";
		try {
			Connection c = Conexion.getConnection();
			c.setAutoCommit(false);
			stmt = c.prepareStatement(query);
			stmt.setLong(1, idArticulo);
			ResultSet rs = stmt.executeQuery();			
			while (rs.next()) {
				
				articulo = new Articulo();
	
				articulo.setIdArticulo(rs.getLong("product_id"));
				articulo.setIdMarca(rs.getInt("brand_id"));
				String aux = rs.getString("product_type");
				if (aux != null) {
					articulo.setTipoArticulo(aux.charAt(0));
				}
				articulo.setDescripcion(rs.getString("description"));
				articulo.setPresentacion(rs.getString("presentation"));
				articulo.setClave1(rs.getString("key1"));
				articulo.setClave2(rs.getString("key2"));
				articulo.setClave3(rs.getString("key3"));
				articulo.setEsPsicofarmaco(rs.getBoolean("is_psychotropic"));
				articulo.setEsEstupefaciente(rs.getBoolean("is_narcotic"));
				articulo.setEsHeladera(rs.getBoolean("is_refrigerator"));
				aux = rs.getString("sale_code");
				if (aux != null) {
					articulo.setCodigoVenta(aux.charAt(0));
				}
				aux = rs.getString("authorization_type");
				if (aux != null) {
					articulo.setTipoAutorizacion(aux.charAt(0));
				}
				articulo.setPrecioUnitario(rs.getBigDecimal("unit_price"));
				articulo.setPrecioVenta(rs.getBigDecimal("sale_price"));
				articulo.setPorcentajePrecioVenta(rs
						.getBigDecimal("sale_price_porcentage"));
				BigDecimal bd = rs.getBigDecimal("recipe_price");
				if (bd != null){
					articulo.setPrecioConReceta(bd);
				}else{
					articulo.setPrecioConReceta(new BigDecimal(0));
				}
				bd = rs.getBigDecimal("recipe_discount");
				if (bd != null){
					articulo.setPorcentajeDescuentoReceta(bd);
				}else{
					articulo.setPorcentajeDescuentoReceta(new BigDecimal(0));
				}
				articulo.setCostoLista(rs.getBigDecimal("list_cost"));
				bd = rs.getBigDecimal("offer_cost");
				if (bd != null){
					articulo.setCostoOferta(bd);
				}else{
					 articulo.setCostoOferta(new BigDecimal(0));
				}
				bd = rs.getBigDecimal("last_cost");
				if (bd != null){
					articulo.setUltimoCosto(bd);
				}else{
					articulo.setUltimoCosto(new BigDecimal(0));
				}
				bd = rs.getBigDecimal("avg_cost");
				if (bd != null){
					articulo.setCostoPromedio(bd);
				}else{
					articulo.setCostoPromedio(new BigDecimal(0));
				}
				char auxTipoIva = rs.getString("tax_type_id").charAt(0);
				if (auxTipoIva != 0) {
					TipoIva ti = new TipoIva();
					ti.setTipoIVA(auxTipoIva);
					articulo.setTipoIva(ti);;
				}
				articulo.setCodigoBarras(rs.getString("barcode"));
				Timestamp timestamp = rs.getTimestamp("nearest_due_date");
				if (timestamp != null) {
	
					articulo.setVencimientoMasCercano(new java.util.Date(
							timestamp.getTime()));
				}else{
					//Fecha mínima de java.util.Date (new Date(0L) = Thu Jan 01 01:00:00 GMT 1970)
					articulo.setVencimientoMasCercano(new java.util.Date(0L));
				}
				articulo.setStock(rs.getLong("stock"));
				articulo.setStockMinimo(rs.getLong("minimum_stock"));
				Usuario usr = new Usuario();
				usr.setNombre(rs.getString("username"));
				articulo.setUsuario(usr);
				articulo.setStatus(rs.getBoolean("status"));
			}			
			
			//Cargo los proveedores
			proveedores = new HashMap<Integer, DTProveedor>();
			query = "SELECT s.supplier_id, ps.product_number, s.comercialname "
					+ "FROM products_suppliers ps "
					+ "JOIN suppliers s ON ps.supplier_id = s.supplier_id "
					+ "WHERE ps.product_id = ?;";
			stmt = c.prepareStatement(query);
			stmt.setLong(1, idArticulo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				DTProveedor nuevo = new DTProveedor();
				nuevo.setIdProveedor(rs.getInt("supplier_id"));
				nuevo.setCodigoIdentificador(rs.getLong("product_number"));
				nuevo.setNombreComercial(rs.getString("comercialname"));
				proveedores.put(nuevo.getIdProveedor(), nuevo);				
			}
			articulo.setProveedores(proveedores);
			
			//Cargo las drogas
			query = "SELECT pd.drug_id "
					+ "FROM product_drugs pd "
					+ "WHERE pd.product_id = ?;";
			stmt = c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setLong(1, idArticulo);
			rs = stmt.executeQuery();
			drogas = new long[getRowCount(rs)];
			int i = 0;
			while (rs.next()) {
				drogas[i] = rs.getLong("drug_id");
				i++;
			}
			articulo.setDrogas(drogas);
			
			//Cargo las acciones terapeuticas
			query = "SELECT pta.therapeutic_action_id "
					+ "FROM product_therap_actions pta "
					+ "WHERE pta.product_id = ?;";
			stmt = c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setLong(1, idArticulo);
			rs = stmt.executeQuery();
			accionesTer = new long[getRowCount(rs)];
			i = 0;
			while (rs.next()) {
				accionesTer[i] = rs.getLong("therapeutic_action_id");
				i++;
			}
			articulo.setAccionesTer(accionesTer);
			
			c.commit();
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return articulo;
	}
	
	private int getRowCount(ResultSet resultSet) {
	    if (resultSet == null) {
	        return 0;
	    }
	    try {
	        resultSet.last();
	        return resultSet.getRow();
	    } catch (SQLException exp) {
	        exp.printStackTrace();
	    } finally {
	        try {
	            resultSet.beforeFirst();
	        } catch (SQLException exp) {
	            exp.printStackTrace();
	        }
	    }
	    return 0;
	}

	@Override
	public List<Articulo> obtenerArticulosDelProveedor(long idProveedor)
			throws Excepciones {
		// TODO Traer todos los articulos del proveedor
		PreparedStatement stmt = null;
		List<Articulo> returnArticulos = new ArrayList<Articulo>();
		try {
			Connection c = Conexion.getConnection();
			String  query = "SELECT * FROM products p " +
					"JOIN products_suppliers ps ON ps.product_id = p.product_id " +
					"WHERE ps.supplier_id = ? AND p.authorization_type = 'S' "
					+ "ORDER BY p.description;";
			stmt = c.prepareStatement(query);
			stmt.setLong(1, idProveedor);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Articulo articulo = new Articulo();
				
				articulo.setIdArticulo(rs.getLong("product_id"));
				articulo.setIdMarca(rs.getInt("brand_id"));
				String aux = rs.getString("product_type");
				if (aux != null) {
					articulo.setTipoArticulo(aux.charAt(0));
				}
				articulo.setDescripcion(rs.getString("description"));
				articulo.setPresentacion(rs.getString("presentation"));
				articulo.setClave1(rs.getString("key1"));
				articulo.setClave2(rs.getString("key2"));
				articulo.setClave3(rs.getString("key3"));
				articulo.setEsPsicofarmaco(rs.getBoolean("is_psychotropic"));
				articulo.setEsEstupefaciente(rs.getBoolean("is_narcotic"));
				articulo.setEsHeladera(rs.getBoolean("is_refrigerator"));
				aux = rs.getString("sale_code");
				if (aux != null) {
					articulo.setCodigoVenta(aux.charAt(0));
				}
				aux = rs.getString("authorization_type");
				if (aux != null) {
					articulo.setTipoAutorizacion(aux.charAt(0));
				}
				articulo.setPrecioUnitario(rs.getBigDecimal("unit_price"));
				articulo.setPrecioVenta(rs.getBigDecimal("sale_price"));
				articulo.setPorcentajePrecioVenta(rs
						.getBigDecimal("sale_price_porcentage"));
				BigDecimal bd = rs.getBigDecimal("recipe_price");
				if (bd != null){
					articulo.setPrecioConReceta(bd);
				}else{
					articulo.setPrecioConReceta(new BigDecimal(0));
				}
				bd = rs.getBigDecimal("recipe_discount");
				if (bd != null){
					articulo.setPorcentajeDescuentoReceta(bd);
				}else{
					articulo.setPorcentajeDescuentoReceta(new BigDecimal(0));
				}
				articulo.setCostoLista(rs.getBigDecimal("list_cost"));
				bd = rs.getBigDecimal("offer_cost");
				if (bd != null){
					articulo.setCostoOferta(bd);
				}else{
					 articulo.setCostoOferta(new BigDecimal(0));
				}
				bd = rs.getBigDecimal("last_cost");
				if (bd != null){
					articulo.setUltimoCosto(bd);
				}else{
					articulo.setUltimoCosto(new BigDecimal(0));
				}
				bd = rs.getBigDecimal("avg_cost");
				if (bd != null){
					articulo.setCostoPromedio(bd);
				}else{
					articulo.setCostoPromedio(new BigDecimal(0));
				}
				char auxTipoIva = rs.getString("tax_type_id").charAt(0);
				if (auxTipoIva != 0) {
					TipoIva ti = new TipoIva();
					ti.setTipoIVA(auxTipoIva);
					articulo.setTipoIva(ti);;
				}
				articulo.setCodigoBarras(rs.getString("barcode"));
				Timestamp timestamp = rs.getTimestamp("nearest_due_date");
				if (timestamp != null) {
	
					articulo.setVencimientoMasCercano(new java.util.Date(
							timestamp.getTime()));
				}else{
					//Fecha mínima de java.util.Date (new Date(0L) = Thu Jan 01 01:00:00 GMT 1970)
					articulo.setVencimientoMasCercano(new java.util.Date(0L));
				}
				articulo.setStock(rs.getLong("stock"));
				articulo.setStockMinimo(rs.getLong("minimum_stock"));
				Usuario usr = new Usuario();
				usr.setNombre(rs.getString("username"));
				articulo.setUsuario(usr);
				articulo.setStatus(rs.getBoolean("status"));
				
				returnArticulos.add(articulo);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		
		return returnArticulos;
	}

	@Override
	public void modificarPreciosDeArticulo(Map<Long, BigDecimal> preciosModificados)
			throws Excepciones {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		try {
			Connection c = Conexion.getConnection();
			for (Map.Entry<Long, BigDecimal> entry : preciosModificados.entrySet())
			{
				String  query = "UPDATE products SET unit_price = ? ";
				query += "WHERE product_id = ?;";
				stmt = c.prepareStatement(query);
				stmt.setBigDecimal(1, entry.getValue());
				stmt.setLong(2, entry.getKey());

				
				stmt.executeUpdate();
				stmt.close();
			}

			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}
	
	public List<DTVencimiento> articulosQueSeVencenEnPeriodo(Date desde, Date hasta) throws Excepciones{
		List<DTVencimiento> ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT p.product_id, p.description, p.stock, p.nearest_due_date "
				+ "FROM products p "
				+ "WHERE p.authorization_type = 'S' AND "
				+ "p.nearest_due_date BETWEEN ? AND ?;";
		try{
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			ret = new ArrayList<DTVencimiento>();
			stmt.setTimestamp(1, new Timestamp(desde.getTime()));
			stmt.setTimestamp(2, new Timestamp(hasta.getTime()));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				DTVencimiento nuevo = new DTVencimiento();
				nuevo.setIdArticulo(rs.getLong("product_id"));
				nuevo.setDescripcion(rs.getString("description"));
				nuevo.setStock(rs.getLong("stock"));
				nuevo.setVencimientoMasCercano(rs.getTimestamp("nearest_due_date"));
				ret.add(nuevo);
			}
			
		} catch (Exception e){
			e.printStackTrace();
			throw (new  Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	@Override
	public void modificarVencimientosDeArticulos(Map<Long, Date> cambios) throws Excepciones {
		PreparedStatement stmt = null;
		String query = "UPDATE products SET NEAREST_DUE_DATE = ?, last_modified = LOCALTIMESTAMP "
				+ "WHERE product_id = ?;";
		try{
			Connection c = Conexion.getConnection();
			c.setAutoCommit(false);
			
			for (Long id : cambios.keySet()){
				stmt = c.prepareStatement(query);
				stmt.setTimestamp(1, new Timestamp(cambios.get(id).getTime()));
				stmt.setLong(2, id);
				stmt.executeUpdate();
			}			
			
			c.commit();
			c.setAutoCommit(true);
			stmt.close();
		} catch (Exception e){
			e.printStackTrace();
			throw (new  Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA,
					Excepciones.ERROR_SISTEMA));
		}
	}
}
