package model;

public class PocasVentas {
	private long id;
	private String desc;
	private int cantVendida;
	private long min;
	
	public PocasVentas(Long id ,String d, int c, long m){
		this.id = id;
		this.desc = d;
		this.cantVendida = c;
		this.min = m;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCantVendida() {
		return cantVendida;
	}

	public void setCantVendida(int cantVendida) {
		this.cantVendida = cantVendida;
	}

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	
}
