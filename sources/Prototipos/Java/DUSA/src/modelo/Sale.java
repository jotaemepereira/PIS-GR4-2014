package modelo;

import java.util.Date;

public class Sale {
	
	private int saleId;
	private int clientId;
	private int userId;
	private Date date;
	private char saleType;
	
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
}
