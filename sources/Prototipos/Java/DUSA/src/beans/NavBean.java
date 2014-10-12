package beans;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@SessionScoped
public class NavBean implements Serializable {
	
	private String page = "/ventas/nueva.xhtml";
	
	public void navAPagina(String destination){
		page = destination;
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
