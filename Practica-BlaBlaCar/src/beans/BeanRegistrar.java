package beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import controlador.ControladorBlaBlaCar;

@ManagedBean(name = "beanRegistrar")
@SessionScoped
public class BeanRegistrar implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size (min = 6, max = 128)
	private String usuario; 
	private String clave1, clave2, email, telefono;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave1() {
		return clave1;
	}

	public void setClave1(String clave1) {
		this.clave1 = clave1;
	}

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String registro() {
		if (!clave1.equals(clave2)) {
			FacesContext faces = FacesContext.getCurrentInstance();
			faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Las contraseņas deben ser iguales", null));
			return "faceletsRegistro";
		}
		
		if (ControladorBlaBlaCar.getInstancia().existeUsuario(usuario)!=null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"El usuario ya existe", null));
			return "faceletsRegistro";
		}
		
		if (ControladorBlaBlaCar.getInstancia().registroUsuario(usuario, clave1, email, telefono) != null) {
			setUsuario("");
			setClave1("");
			setClave2("");
			setEmail("");
			setTelefono("");
			return "faceletsLogin";
		} else {
			return "faceletsFallo";
		}
	}
	
}
