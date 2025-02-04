package beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.jms.JMSException;
import javax.naming.NamingException;

import jms.PublicadorSugerencia;

@ManagedBean(name = "beanSugerencias")
@SessionScoped
public class BeanSugerencias implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String titulo, descripcion;
	private Map<String, String> sugerenciasRecibidas = new LinkedHashMap<String, String>();
	private String[] sugerenciasRecibidasSeleccionadas = null;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Map<String, String> getSugerenciasRecibidas() {
		return sugerenciasRecibidas;
	}

	public void setSugerenciasRecibidas(Map<String, String> sugerenciasRecibidas) {
		this.sugerenciasRecibidas = sugerenciasRecibidas;
	}


	public String[] getSugerenciasRecibidasSeleccionadas() {
		return sugerenciasRecibidasSeleccionadas;
	}

	public void setSugerenciasRecibidasSeleccionadas(String[] sugerenciasRecibidasSeleccionadas) {
		this.sugerenciasRecibidasSeleccionadas = sugerenciasRecibidasSeleccionadas;
	}

	public void enviarSugerencia(ActionEvent event) {
		String resultado = "";
		try {
			PublicadorSugerencia.enviar(titulo, descripcion);
			resultado = "Sugerencia realizada correctamente.";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, resultado, null));
		} catch (NamingException | JMSException e) {
			resultado = "Fallo al realizar la sugerencia.";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, null));
			e.printStackTrace();
		}
	
		setTitulo("");
		setDescripcion("");
	}
}
