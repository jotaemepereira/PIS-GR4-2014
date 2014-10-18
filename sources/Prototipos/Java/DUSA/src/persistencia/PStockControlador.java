package persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.primefaces.json.JSONObject;

import controladores.Excepciones;
import datatypes.DTBusquedaArticulo;
import datatypes.DTProveedor;
import datatypes.DTVenta;
import model.AccionTer;
import model.Articulo;
import model.Droga;
import model.Enumerados;
import model.LineaPedido;
import model.Pedido;
import interfaces.IStockPersistencia;

public class PStockControlador implements IStockPersistencia {

	@Override
	public void persistirArticulo(Articulo articulo) throws Excepciones{
		PreparedStatement stmt = null;
		
		String query = "INSERT INTO PRODUCTS " +
						"(BRAND_ID, PRODUCT_TYPE, DESCRIPTION, PRESENTATION, KEY1, KEY2, KEY3, IS_PSYCHOTROPIC, IS_NARCOTIC, IS_REFRIGERATOR, SALE_CODE, AUTHORIZATION_TYPE,"
						+ " UNIT_PRICE, SALE_PRICE, SALE_PRICE_PORCENTAGE,LIST_COST, OFFER_COST, LAST_COST, AVG_COST, TAX_TYPE, BARCODE, LAST_PRICE_DATE"
						+ ", NEAREST_DUE_DATE, STOCK, MINIMUM_STOCK, LAST_MODIFIED, STATUS) " +
						" VALUES " +
						" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, LOCALTIMESTAMP, ?, ?, ?, LOCALTIMESTAMP, ?) RETURNING PRODUCT_ID;";
		Connection c;
		try {
			c = Conexion.getConnection();
			c.setAutoCommit(false);
			try {
				// Seteo los datos a insertar en la bd
				stmt = c.prepareStatement(query);
				stmt.setInt(1, articulo.getIdMarca());
				stmt.setString(2, String.valueOf(articulo.getTipoArticulo()));//Null
				stmt.setString(3, articulo.getDescripcion());//Not Null
				stmt.setString(4, articulo.getPresentacion());//Null
				stmt.setString(5, articulo.getClave1());//Null
				stmt.setString(6, articulo.getClave2());//Null
				stmt.setString(7, articulo.getClave3());//Null
				stmt.setBoolean(8, articulo.isEsPsicofarmaco());//Not Null
				stmt.setBoolean(9, articulo.isEsEstupefaciente());//Not Null
				stmt.setBoolean(10, articulo.isEsHeladera());//Not Null
				stmt.setString(11, String.valueOf(articulo.getCodigoVenta()));//Null
				stmt.setString(12, String.valueOf(Enumerados.habilitado.HABILITADO));//Not Null
				stmt.setBigDecimal(13, articulo.getPrecioUnitario());//Not Null
				stmt.setBigDecimal(14, articulo.getPrecioVenta());//Not Null
				stmt.setBigDecimal(15, articulo.getPorcentajePrecioVenta());//Not Null
				stmt.setBigDecimal(16, articulo.getCostoLista());//Not Null
				stmt.setBigDecimal(17, articulo.getCostoOferta());//Null
				stmt.setBigDecimal(18, articulo.getUltimoCosto());//Null
				stmt.setBigDecimal(19, articulo.getCostoPromedio());//Null
				stmt.setInt(20, articulo.getTipoIva());//Null
				stmt.setString(21, articulo.getCodigoBarras());//Null
				stmt.setNull(22, java.sql.Types.TIMESTAMP);//Null Vencimiento Más Cercano
				stmt.setLong(23, articulo.getStock());//Not Null
				stmt.setLong(24, articulo.getStockMinimo());//Null
				stmt.setBoolean(25, true);//Not Null
				
				ResultSet rs = stmt.executeQuery();
				//Obtengo la clave del nuevo artículo creado
				long key = 0;
				while (rs.next()){
					key = rs.getLong(1);
				}
				
				//Para cada proveedor asociado inserto una fila en products_suppliers
				List<DTProveedor> proveedores = new ArrayList<DTProveedor>(articulo.getProveedores().values()); 
				Iterator<DTProveedor> i = proveedores.iterator();
				while (i.hasNext()){
					DTProveedor next = i.next();
					query = "INSERT INTO PRODUCTS_SUPPLIERS " +
							"(SUPPLIER_ID, PRODUCT_ID, PRODUCT_NUMBER, LINE_ID) " +
							"VALUES " +
							"(?, ?, ?, ?)";
					stmt = c.prepareStatement(query);
					stmt.setInt(1, next.getIdProveedor());
					stmt.setLong(2, key);
					stmt.setLong(3, next.getCodigoIdentificador());
					stmt.setString(4, next.getIdLinea());
					stmt.executeUpdate();
				}
				
				//Para cada droga seleccionada inserto una fila en product_drugs
				for(long idDroga : articulo.getDrogas()){
					query = "INSERT INTO PRODUCT_DRUGS " +
							"(PRODUCT_ID, DRUG_ID) " +
							"VALUES " +
							"(?, ?)";
					stmt = c.prepareStatement(query);
					stmt.setLong(1, key);
					stmt.setLong(2, idDroga);
					stmt.executeUpdate();
				}
				
				//Para cada acción terapéutica seleccionada inserto una fila en product_therap_actions
				for(long idAccTer : articulo.getAccionesTer()){
					query = "INSERT INTO PRODUCT_THERAP_ACTIONS " +
							"(PRODUCT_ID, THERAPEUTIC_ACTION_ID) " +
							"VALUES " +
							"(?, ?)";
					stmt = c.prepareStatement(query);
					stmt.setLong(1, key);
					stmt.setLong(2, idAccTer);
					stmt.executeUpdate();
				}
				
				//Commiteo todo y cierro conexion
				c.commit();
				stmt.close();
				c.close();
			} catch ( Exception e ) {
				//Hago rollback de las cosas y lanzo excepcion
				c.rollback();
				e.printStackTrace();				
				throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
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
	

	@Override
	public boolean existeArticulo(String descripcion) throws Excepciones {
		int cant = 0;
		PreparedStatement stmt = null;
		String query = "SELECT COUNT(*) AS cant FROM products " +
						"WHERE DESCRIPTION = ?";
				
		try {
			Connection c = Conexion.getConnection();
			
			stmt = c.prepareStatement(query);
			stmt.setString(1, descripcion);
			ResultSet rs = stmt.executeQuery();
			//Obtengo la cantidad de proveedores con ese rut
			while(rs.next()){
				cant = rs.getInt("cant");
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		return (cant > 0);
	}

	public List<Articulo> buscarArticulo(String descripcion){

		return null;

	}

	@Override
	public Date obtenerFechaUltimoPedido() throws Excepciones {
		// TODO Auto-generated method stub
		//Codigo en la base para obtener el ultimo pedido
		// TODO Auto-generated method stub
		
		Date ret = null;
		Statement stmt = null;
		String query = "SELECT order_date FROM orders_dusa" +
				" GROUP BY order_date" +
				" ORDER BY order_date DESC" +
				" LIMIT 1;";
		try {
			Connection c = Conexion.getConnection();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ret = rs.getDate("order_date");
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
		return ret;
	}

	
	@Override
	public List<DTBusquedaArticulo> buscarArticulos(String busqueda) throws Excepciones{
		List<DTBusquedaArticulo> listaArticulos = new ArrayList<DTBusquedaArticulo>();
		
		String urlString = "http://localhost:8080/solr";
		SolrServer solr = new HttpSolrServer(urlString);
		SolrQuery parameters = new SolrQuery();
		parameters.setRequestHandler("/articulos/select");
		String regexpBusqueda = "*" + busqueda + "*";
		parameters.set("q", "KEY1:" + regexpBusqueda + 
							" KEY2:" + regexpBusqueda + 
							" KEY3:" + regexpBusqueda + 
							" CRITERIO_INTERNO:" + regexpBusqueda + 
							" DESCRIPTION:" + regexpBusqueda + 
							" BARCODE:" + regexpBusqueda +
							" DROGAS: " + regexpBusqueda +
							" PRESENTATION: " + regexpBusqueda + 
							" ACCIONES_TERAPEUTICAS: " + regexpBusqueda +
							" MARCA: " + regexpBusqueda);
		parameters.set("wt", "json");
		parameters.set("fl", "DESCRIPTION id BARCODE DROGAS PRESENTATION ACCIONES_TERAPEUTICAS MARCA");

		try {
			SolrDocumentList response = solr.query(parameters).getResults();
			System.out.println(response);
			Long cant = response.getNumFound();
			for(int i = 0; i < cant; i++){
				System.out.println(response.get(i));
				
				SolrDocument item = response.get(i);
				
				DTBusquedaArticulo articulo = new DTBusquedaArticulo();
				articulo.setIdArticulo(Integer.parseInt(item.getFieldValue("id").toString()));
				articulo.setCodigoBarras(item.getFieldValue("BARCODE").toString());
				articulo.setDescripcion(item.getFieldValue("DESCRIPTION").toString());
				if(item.getFieldValue("DROGAS") == null){
					articulo.setDroga("");
				}else{
					articulo.setDroga(item.getFieldValue("DROGAS").toString().replace("[", "").replace("]", ""));
				}
				if(item.getFieldValue("PRESENTATION") == null){
					articulo.setPresentacion("");
				}else{
					articulo.setPresentacion(item.getFieldValue("PRESENTATION").toString());
				}
				if(item.getFieldValue("ACCIONES_TERAPEUTICAS") == null){
					articulo.setAccionesTerapeuticas("");
				}else{
					articulo.setAccionesTerapeuticas(item.getFieldValue("ACCIONES_TERAPEUTICAS").toString().replace("[", "").replace("]", ""));
				}
				if(item.getFieldValue("MARCA") == null){
					articulo.setMarca("");
				}else{
					articulo.setMarca(item.getFieldValue("MARCA").toString());
				}
				listaArticulos.add(articulo);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		
		return listaArticulos;
	}
	
	@Override
	public DTVenta getDatosArticuloVenta(int idArticulo) throws Excepciones{
		DTVenta articulo = new DTVenta();
		PreparedStatement stmt = null;
		String query = "SELECT SALE_PRICE, IS_PSYCHOTROPIC, IS_NARCOTIC, AUTHORIZATION_TYPE, STOCK "
				+ "FROM PRODUCTS "
				+ "WHERE PRODUCT_ID = ?";
		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			stmt.setInt(1, idArticulo);
			ResultSet rs = stmt.executeQuery();
			//Obtengo la cantidad de proveedores con ese rut
			while (rs.next()){
				articulo.setPrecioVenta(rs.getBigDecimal("SALE_PRICE"));
				articulo.setRecetaVerde(rs.getBoolean("IS_PSYCHOTROPIC"));
				articulo.setRecetaNaranja(rs.getBoolean("IS_NARCOTIC"));
				articulo.setStock(rs.getInt("STOCK"));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			e.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
		
		articulo.setCantidad(0);
		articulo.setRecetaBlanca(false);
		
		/*
		BigDecimal desc1 = new BigDecimal(10);
    	BigDecimal desc2 = new BigDecimal(30);
    	BigDecimal desc3 = new BigDecimal(50);
		articulo.setDescuento1(desc1);
		articulo.setDescuento2(desc2);
		articulo.setDescuento3(desc3);
		*/
			
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
			System.out.println(response);
		} catch (SolrServerException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
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
		parameters.setParam("clean", true);
		parameters.setParam("commit", true);
		
		SolrDocumentList response;
		try {
			response = solr.query(parameters).getResults();
			System.out.println(response);
		} catch (SolrServerException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
	}
	
	
	/**
	 * @author santiago 
	 * no deberia ser solo a los articulos de DUSA ?
	 * @throws Excepciones 
	 */
	@Override
	public int getStock(long idArticulo) throws Excepciones{
	//consulta que obtiene el stock de un articulo
		
		PreparedStatement stmt = null;
		
		String query = "SELECT stock  FROM products WHERE product_id=" +idArticulo +";";
		int ret;
		try {
			
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ret = (int) rs.getLong(1);
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			e.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
		return ret ;


	}
	/**
	 * @author Guille
	 * @throws Excepciones 
	 */
	@Override
	public void persistirPedido(Pedido p) throws Excepciones{
		// TODO Auto-generated method stub
		
		PreparedStatement stmt = null;
		
		String query = "INSERT INTO ORDERS_DUSA "
						+ "(USER_ID, PAYMENT_TYPE, ORDER_DATE) VALUES "
						+ " (?, ?, LOCALTIMESTAMP) RETURNING ORDER_DUSA_ID;";
		try {
			
			Connection c = Conexion.getConnection();
			try {
				
				stmt = c.prepareStatement(query);
				stmt.setInt(1, p.getIdUsuario());
				stmt.setString(2, "" + p.getFormaDePago() + "");
				
				ResultSet rs = stmt.executeQuery();
				//Obtengo la clave del pedido persistido
				long pedidoID = 0;
				while (rs.next()){
					pedidoID = rs.getLong(1);
				}
				//Cierro canal y su dependencia, porque voy abrir uno nuevo.
				rs.close();
				stmt.close();
				//Para cada linea pedido asociado inserto una fila en ORDER_DUSA_DETAILS
				for (Iterator<LineaPedido> iter = p.getLineas().iterator(); iter.hasNext();) {
					
					LineaPedido lPedido = iter.next();
					
					query = "INSERT INTO ORDER_DUSA_DETAILS "
							+ "(ORDER_DUSA_ID, PRODUCT_ID, QUANTITY) VALUES "
							+ " (?, ?);";
					stmt = c.prepareStatement(query);
					stmt.setLong(1, pedidoID);
					stmt.setLong(2, lPedido.getIdArticulo().longValue());
					stmt.setInt	(3, lPedido.getCantidad());
					
					stmt.executeUpdate();
				}
				stmt.close();
				c.close();
			} catch ( Exception e ) {
				//Regreso al estado anterior de la base y lanzo la excepcion correspondiente
				
				c.rollback();
				e.printStackTrace();
				throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
			}
		} catch (Excepciones e){
			//Paso las excepciones personalizadas. 
			throw e;
		} catch (Exception e) {
			// Error de conexión con la base de datos
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
	}
	
	
	/**
	 * @author santiago 
	 * no deberia ser solo a los articulos de DUSA ?
	 */
	@Override
	public List<Long> obtenerIdTodosLosArticulos() throws Excepciones{
		List<Long> idArts = new ArrayList<Long>();
		PreparedStatement stmt = null;
		String query = "SELECT product_id FROM products; ";
		
		try {
			
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				idArts.add(rs.getLong(1));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
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
		String query = "SELECT count(*) AS cant" + 
						"FROM products_suppliers ps " + 
						"WHERE ps.product_id = ? AND " + 
								"ps.supplier_id = ?;";
		
		try {
			
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			stmt.setLong(1, idArticulo);
			stmt.setInt(2, 0); //Hay que ver cual es para hardcodearlo en un lugar solo.
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				cant = rs.getInt("cant");
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			e.printStackTrace();
			throw (new Excepciones("Error sistema", Excepciones.ERROR_SISTEMA));
		}
		return cant > 0;
	}


	@Override
	public List<Droga> obtenerDrogas() throws Excepciones {
		List<Droga> ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT d.drug_id, d.description " +
						"FROM DRUGS d ";
		
		try {
			Connection c = Conexion.getConnection();
			c.setAutoCommit(false);
			stmt = c.prepareStatement(query);
			stmt.setFetchSize(100);
			ResultSet rs = stmt.executeQuery();
			ret = new ArrayList<Droga>();
			while(rs.next()){
				Droga nuevo = new Droga();
				nuevo.setIdDroga(rs.getLong("drug_id"));
				nuevo.setDescripcion(rs.getString("description"));
				ret.add(nuevo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		return ret;	
	}


	@Override
	public List<AccionTer> obtenerAccionesTerapeuticas()
			throws Excepciones {
		List<AccionTer> ret = null;
		PreparedStatement stmt = null;
		String query = "SELECT ta.therapeutic_action_id, ta.description " +
						"FROM THERAPEUTIC_ACTIONS ta ";
		
		try {
			Connection c = Conexion.getConnection();
			c.setAutoCommit(false);
			stmt = c.prepareStatement(query);
			stmt.setFetchSize(100);
			ResultSet rs = stmt.executeQuery();
			ret = new ArrayList<AccionTer>();
			while(rs.next()){
				AccionTer nuevo = new AccionTer();
				nuevo.setIdAccionTer(rs.getLong("therapeutic_action_id"));
				nuevo.setDescripcion(rs.getString("description"));
				ret.add(nuevo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e){
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
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
		String query = "SELECT * " +
						"FROM products p INNER JOIN products_suppliers ps ON p.product_id = ps.product_id " +
							"INNER JOIN (SELECT supplier_id, comercialname FROM suppliers) as s ON s.supplier_id = ps.supplier_id " +
						"WHERE p.product_id = ?;";
		try {
			Connection c = Conexion.getConnection();
			stmt = c.prepareStatement(query);
			stmt.setLong(1, idArticulo);
			ResultSet rs = stmt.executeQuery();
			ret = new HashMap<Integer, DTProveedor>();
			while(rs.next()){
				
				if (articulo == null) {
					
					articulo = new Articulo();
					
					articulo.setIdArticulo(rs.getLong(""));
					articulo.setTipoArticulo(rs.getString("product_type").charAt(0));
					articulo.setDescripcion(rs.getString("description"));
					articulo.setClave1(rs.getString("key1"));
					articulo.setClave2(rs.getString("key2"));
					articulo.setClave3(rs.getString("key3"));
					articulo.setEsPsicofarmaco(rs.getBoolean("is_psychotropic"));
					articulo.setEsEstupefaciente(rs.getBoolean("is_narcotic"));
					articulo.setEsHeladera(rs.getBoolean("is_refrigerator"));
					articulo.setCodigoVenta(rs.getString("sale_code").charAt(0));
					articulo.setTipoAutorizacion(rs.getString("authorization_type").charAt(0));
					articulo.setPrecioUnitario(rs.getBigDecimal("unit_price"));
					articulo.setPrecioVenta(rs.getBigDecimal("sale_price"));
					articulo.setPorcentajePrecioVenta(rs.getBigDecimal("sale_price_porcentage"));
					articulo.setCostoLista(rs.getBigDecimal("list_cost"));
					articulo.setCostoOferta(rs.getBigDecimal("offer_cost"));
					articulo.setUltimoCosto(rs.getBigDecimal("last_cost"));
					articulo.setCostoPromedio(rs.getBigDecimal("avg_cost"));
					articulo.setTipoIva(rs.getBigDecimal("tax_type").intValue());
					articulo.setCodigoBarras(rs.getString("barcode"));
					Timestamp timestamp = rs.getTimestamp("nearest_due_date");
					if (timestamp != null) {
						
						articulo.setVencimientoMasCercano(new java.util.Date(timestamp.getTime()));
					}
					articulo.setStock(rs.getLong("stock"));
					articulo.setStockMinimo(rs.getLong("minimum_stock"));
					articulo.setStatus(rs.getBoolean("status"));
				}
				
				DTProveedor dtProveedor = new DTProveedor();
				dtProveedor.setIdProveedor(rs.getInt("supplier_id"));
				dtProveedor.setCodigoIdentificador(rs.getLong("product_number"));
				dtProveedor.setNombreComercial(rs.getString("comercialname"));
				dtProveedor.setIdLinea(rs.getString("line_id"));
				
				ret.put(dtProveedor.getIdProveedor(), dtProveedor);
			}
			if (articulo != null) {
				
				articulo.setProveedores(ret);
			}
			rs.close();
			stmt.close();
		} catch (Exception e){
			e.printStackTrace();
			throw(new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		
		return articulo;
	}
}

