package model;

import java.util.Date;
import java.util.List;

public class Venta {
	
	private int saleId;
	private int clientId;
	private int userId;
	private Date date;
	private char saleType;
	private List<LineaVenta> lines;
	
	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public char getSaleType() {
		return saleType;
	}

	public void setSaleType(char saleType) {
		this.saleType = saleType;
	}

	public List<LineaVenta> getLines() {
		return lines;
	}

	public void setLines(List<LineaVenta> lines) {
		this.lines = lines;
	}
}
