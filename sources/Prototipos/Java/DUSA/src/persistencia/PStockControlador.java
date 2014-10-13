package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import datatypes.DTProveedor;
import model.Articulo;
import model.Enumerados;
import model.LineaPedido;
import model.Pedido;
import interfaces.IStockPersistencia;

public class PStockControlador implements IStockPersistencia {

	@Override
	public void persistirArticulo(Articulo articulo) throws Excepciones{
		PreparedStatement stmt = null;
		
		String query = "INSERT INTO PRODUCTS " +
						"(PRODUCT_TYPE, DESCRIPTION, KEY1, KEY2, KEY3, IS_PSYCHOTROPIC, IS_NARCOTIC, IS_REFRIGERATOR, SALE_CODE, AUTHORIZATION_TYPE,"
						+ " UNIT_PRICE, SALE_PRICE, SALE_PRICE_PORCENTAGE,LIST_COST, OFFER_COST, LAST_COST, AVG_COST, TAX_TYPE, BARCODE, LAST_PRICE_DATE"
						+ ", NEAREST_DUE_DATE, STOCK, MINIMUM_STOCK, LAST_MODIFIED, STATUS) " +
						" VALUES " +
						" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, LOCALTIMESTAMP, ?, ?, ?, LOCALTIMESTAMP, ?) RETURNING PRODUCT_ID;";
		Connection c;
		try {
			c = Conexion.getConnection();
			c.setAutoCommit(false);
			try {
				// Seteo los datos a insertar en la bd
				stmt = c.prepareStatement(query);
				stmt.setString(1, String.valueOf(articulo.getTipoArticulo()));//Null
				stmt.setString(2, articulo.getDescripcion());//Not Null
				stmt.setString(3, articulo.getClave1());//Null
				stmt.setString(4, articulo.getClave2());//Null
				stmt.setString(5, articulo.getClave3());//Null
				stmt.setBoolean(6, articulo.isEsPsicofarmaco());//Not Null
				stmt.setBoolean(7, articulo.isEsEstupefaciente());//Not Null
				stmt.setBoolean(8, articulo.isEsHeladera());//Not Null
				stmt.setString(9, String.valueOf(articulo.getCodigoVenta()));//Null
				stmt.setString(10, String.valueOf(Enumerados.habilitado.HABILITADO));//Not Null
				stmt.setBigDecimal(11, articulo.getPrecioUnitario());//Not Null
				stmt.setBigDecimal(12, articulo.getPrecioVenta());//Not Null
				stmt.setBigDecimal(13, articulo.getPorcentajePrecioVenta());//Not Null
				stmt.setBigDecimal(14, articulo.getCostoLista());//Not Null
				stmt.setBigDecimal(15, articulo.getCostoOferta());//Null
				stmt.setBigDecimal(16, articulo.getUltimoCosto());//Null
				stmt.setBigDecimal(17, articulo.getCostoPromedio());//Null
				stmt.setInt(18, articulo.getTipoIva());//Null
				stmt.setString(19, articulo.getCodigoBarras());//Null
				//stmt.setDate(18, new java.sql.Date(articulo.getFechaUltimoPrecio().getTime()));//Not Null
				stmt.setNull(20, java.sql.Types.TIMESTAMP);//Null Vencimiento Más Cercano
				stmt.setInt(21, articulo.getStock());//Not Null
				stmt.setInt(22, articulo.getStockMinimo());//Null
				stmt.setBoolean(23, true);//Not Null
				
				ResultSet rs = stmt.executeQuery();
				//Obtengo la clave del nuevo artículo creado
				int key = 0;
				while (rs.next()){
					key = rs.getInt(1);
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
					stmt.setInt(2, key);
					stmt.setLong(3, next.getCodigoIdentificador());
					stmt.setString(4, next.getIdLinea());
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
	public List<Articulo> buscarArticulos(String busqueda) throws Excepciones{
		List<Articulo> listaArticulos = new ArrayList<Articulo>();
		
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
							" BARCODE:" + regexpBusqueda);
		parameters.set("wt", "json");
		parameters.set("fl", "DESCRIPTION id");

		try {
			SolrDocumentList response = solr.query(parameters).getResults();
			System.out.println(response);
			Long cant = response.getNumFound();
			for(int i = 0; i < cant; i++){
				System.out.println(response.get(i));
				
				SolrDocument item = response.get(i);
				
				Articulo articulo = new Articulo();
				articulo.setDescripcion(item.getFieldValue("DESCRIPTION").toString());
				articulo.setIdArticulo(Integer.parseInt(item.getFieldValue("id").toString()));
				
				listaArticulos.add(articulo);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
			throw (new Excepciones(Excepciones.MENSAJE_ERROR_SISTEMA, Excepciones.ERROR_SISTEMA));
		}
		
		return listaArticulos;
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
	
	@Override
	public void persistirPedido(Pedido p){
		// TODO Auto-generated method stub
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
			stmt.setInt(2, 0); //Hay que ver cual es para hardcodearlo en un logar solo.
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

}