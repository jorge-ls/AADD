package beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import controlador.ControladorBlaBlaCar;
import model.Usuario;
import model.ViajeJPA;

@ManagedBean(name = "beanViajesRealizados")
@SessionScoped
public class BeanViajesRealizados implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<ViajeJPA> listaViajes = new LinkedList<ViajeJPA>();
	private ViajeJPA viajeSeleccionado;
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin login;

	public List<ViajeJPA> getListaViajes() {
		if (login.getUsuario() != null) {
			Usuario usu = ControladorBlaBlaCar.getInstancia().existeUsuario(login.getUsuario());
			listaViajes = ControladorBlaBlaCar.getInstancia().listarViajesRealizados(usu);
		}
		return listaViajes;
	}

	public void setListaViajes(List<ViajeJPA> listaViajes) {
		this.listaViajes = listaViajes;
	}

	public ViajeJPA getViajeSeleccionado() {
		return viajeSeleccionado;
	}

	public void setViajeSeleccionado(ViajeJPA viajeSeleccionado) {
		this.viajeSeleccionado = viajeSeleccionado;
	}
	
	public BeanLogin getLogin() {
		return login;
	}

	public void setLogin(BeanLogin login) {
		this.login = login;
	}

	public String visualizarValoraciones() {
		return "enviarValoracion";
	}
	
	public String listaViajes() {
		if (login.getUsuario() != null) {
			getListaViajes();
			return "faceletsViajesRealizados";
		} else {
			return "faceletsLogin";
		}
	}

}
