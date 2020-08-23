package beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import controlador.ControladorBlaBlaCar;
import model.Usuario;

@ManagedBean(name = "beanAnyadirCoche")
@SessionScoped
public class BeanAnyadirCoche implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin login;
	private String matricula, modelo;
	private int anyo, confort;
	
	public BeanLogin getLogin() {
		return login;
	}

	public void setLogin(BeanLogin login) {
		this.login = login;
	}
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public int getConfort() {
		return confort;
	}

	public void setConfort(int confort) {
		this.confort = confort;
	}

	public String anyadirCoche() {
		if (login.getUsuario() != null) {
			Usuario usu = ControladorBlaBlaCar.getInstancia().existeUsuario(login.getUsuario());
			if (usu != null && ControladorBlaBlaCar.getInstancia().anyadirCoche(usu, matricula, modelo, anyo, confort)) {
				setMatricula("");
				setModelo("");
				setAnyo(0);
				setConfort(0);
				return "faceletsExito";
			} else {
				setMatricula("");
				setModelo("");
				setAnyo(0);
				setConfort(0);
				return "faceletsAnyadirCoche";
			}
		} else {
			return "faceletsLogin";
		}
	}
	
}
