package beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import controlador.ControladorBlaBlaCar;
import model.Orden;
import model.Usuario;
import model.ViajeJPA;

@ManagedBean(name = "beanListarViajesUsuario")
@SessionScoped
public class BeanListarViajesUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ViajeJPA> listaViajes = new LinkedList<ViajeJPA>();
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin login;
	private boolean pendientes, realizados, propios;
	private Orden ordenFecha, ordenCiudad;
	private String tipoOrdenacion;
	
	public List<ViajeJPA> getListaViajes() {
		if (login.getUsuario() != null) {
			Usuario usu = ControladorBlaBlaCar.getInstancia().existeUsuario(login.getUsuario());
			if (tipoOrdenacion != null)
				switch(tipoOrdenacion) {
				case "Ninguno":
					ordenFecha = Orden.NINGUNO;
					ordenCiudad = Orden.NINGUNO;
					break;
				case "Ascendente por ciudad":
					ordenFecha = Orden.NINGUNO;
					ordenCiudad = Orden.ASCENDENTE;
					break;
				case "Descendente por ciudad":
					ordenFecha = Orden.NINGUNO;
					ordenCiudad = Orden.DESCENDENTE;
					break;
				case "Ascendente por fecha":
					ordenFecha = Orden.ASCENDENTE;
					ordenCiudad = Orden.NINGUNO;
					break;
				case "Descendente por fecha":
					ordenFecha = Orden.DESCENDENTE;
					ordenCiudad = Orden.NINGUNO;
					break;
				}
			else {
				ordenFecha = Orden.NINGUNO;
				ordenCiudad = Orden.NINGUNO;
			}
			listaViajes = ControladorBlaBlaCar.getInstancia().listarViajesUsuario(usu, pendientes, realizados, propios, ordenFecha, ordenCiudad);
		}
		return listaViajes;
	}
	

	public void setListaViajes(List<ViajeJPA> listaViajes) {
		this.listaViajes = listaViajes;
	}

	public BeanLogin getLogin() {
		return login;
	}

	public void setLogin(BeanLogin login) {
		this.login = login;
	}

	public boolean isPendientes() {
		return pendientes;
	}

	public void setPendientes(boolean pendientes) {
		this.pendientes = pendientes;
	}

	public boolean isRealizados() {
		return realizados;
	}

	public void setRealizados(boolean realizados) {
		this.realizados = realizados;
	}

	public boolean isPropios() {
		return propios;
	}

	public void setPropios(boolean propios) {
		this.propios = propios;
	}

	public Orden getOrdenFecha() {
		return ordenFecha;
	}

	public void setOrdenFecha(Orden ordenFecha) {
		this.ordenFecha = ordenFecha;
	}

	public Orden getOrdenCiudad() {
		return ordenCiudad;
	}

	public void setOrdenCiudad(Orden ordenCiudad) {
		this.ordenCiudad = ordenCiudad;
	}
	
	public String getTipoOrdenacion() {
		return tipoOrdenacion;
	}

	public void setTipoOrdenacion(String tipoOrdenacion) {
		this.tipoOrdenacion = tipoOrdenacion;
	}
	
	public String listarViajes() {
		if (login.getUsuario() != null) {
			listaViajes = getListaViajes();
			return "faceletsListarViajes";
		} else {
			return "faceletsLogin";
		}
	}
}