package beans;


import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import javax.naming.NamingException;

import controlador.ControladorBlaBlaCar;
import jms.SuscriptorSugerencias;

@ManagedBean(name = "beanLogin")
@SessionScoped
public class BeanLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private String clave;
	@ManagedProperty(value = "#{beanSugerencias}")
	private BeanSugerencias sugerencias;
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public BeanSugerencias getSugerencias() {
		return sugerencias;
	}

	public void setSugerencias(BeanSugerencias sugerencias) {
		this.sugerencias = sugerencias;
	}

	public String validacion() {
		if (ControladorBlaBlaCar.getInstancia().existeUsuario(usuario) != null) {
			if (ControladorBlaBlaCar.getInstancia().loginUsuario(usuario, clave)) {
				try {
					SuscriptorSugerencias.registrarApartado();
				} catch (NamingException | JMSException e) {
					e.printStackTrace();
				}
				return "faceletsWelcome";
			} else {
				setUsuario("");
				setClave("");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de login", null));
				return "faceletsLogin";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, usuario + " no existe", null));
			setUsuario("");
			setClave("");
			return "faceletsLogin";
		}
	}
	
	
}
