package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.*; 
import javax.mail.internet.AddressException;

public class Mail {

	private String mailEmisor;
	private String password;
	private ArrayList<String> destinatarios;
	private String asunto;
	private String contenido;
	
	public void setDestinatarios(String mailDest){
		this.destinatarios.add(mailDest);                                               
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
		this.contenido = "Se informa que se realizaron los siguientes cambios:";
		this.contenido.concat("\n");
		this.contenido.concat("\n");
		while(it.hasNext()){
			this.contenido.concat(it.next().toString());
			this.contenido.concat("\n");
		}
				
	}
	public void Enviar() throws AddressException, MessagingException{
		
		String dests = new String();
		Iterator<String> it = destinatarios.iterator();
		dests.concat(it.next());
		while (it.hasNext()){
			dests.concat(", ");
			dests.concat(it.next());
		}
		// voy a poner usr y pass en una tabla en la bd 
		GoogleMail.Send(this.mailEmisor,this.password ,  dests, this.asunto, this.contenido);

	}
}

