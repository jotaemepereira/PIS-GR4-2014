package model;

import java.util.Iterator;
import java.util.List;

import javax.mail.*; 
import javax.mail.internet.AddressException;

public class Mail {

	private String mailEmisor;
	private String password;
	private String destinatarios;
	private String asunto;
	private String contenido;
	
	public void setDestinatarios(String mailDest){
		this.destinatarios = mailDest;                                              
	}
	public void setEmisor(String mailEmisor, String password){
		this.mailEmisor = mailEmisor;
		this.password = password;
	}
	public void setAsunto(String asunto ){
		this.asunto = asunto;
	}
	public void setContenido (List<Cambio> camb){
		
		Iterator <Cambio> it = camb.iterator();
		this.contenido = new String();
		this.contenido += "Se informa que se realizaron los siguientes cambios:";
		this.contenido += "\n" + "\n";
		while(it.hasNext()){
			contenido += (it.next().toString());
			this.contenido += "\n";
			
		}
				
	}
public void setContenidoPocasVentas (List<PocasVentas> pv ){
		
		Iterator <PocasVentas> it = pv.iterator();
		this.contenido = new String();
		this.contenido += "Se informa que se hicieron pocas ventas en los siguientes productos:";
		this.contenido += "\n" + "\n";
		while(it.hasNext()){
			PocasVentas p = it.next();
			contenido += ("Descripción Articulo: " + p.getDesc() +
					     "   Mínimo de Stock: " + p.getMin() + 
					     "   Cantidad Vendida: " + p.getCantVendida());
			this.contenido += "\n";
		
		}
				
	}
	public void Enviar() throws AddressException, MessagingException{
	
		GoogleMail.Send(this.mailEmisor,this.password ,  this.destinatarios, this.asunto, this.contenido);

	}
}
