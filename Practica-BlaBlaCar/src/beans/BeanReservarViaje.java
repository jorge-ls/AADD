package beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import controlador.ControladorBlaBlaCar;
import model.Usuario;
import model.ViajeJPA;

@ManagedBean(name = "beanReservarViaje")
@SessionScoped
public class BeanReservarViaje implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ViajeJPA viajeSeleccionado;
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin login;
	private String comentario;
	private Collection<ViajeJPA> viajes = new LinkedList<ViajeJPA>();
	
	public Collection<ViajeJPA> getViajes() {
		viajes = ControladorBlaBlaCar.getInstancia().listarViajesAReservar(login.getUsuario());
		return viajes;
	}
	
	public void setViajes(Collection<ViajeJPA> viajes) {
		this.viajes = viajes;
	}
	
	public ViajeJPA getViajeSeleccionado() {
		return viajeSeleccionado;
	}
	
	public void setViajeSeleccionado(ViajeJPA viajeSeleccionado) {
		System.out.println("Viaje Seleccionado");
		this.viajeSeleccionado = viajeSeleccionado;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public BeanLogin getLogin() {
		return login;

	}
	
	public void setLogin(BeanLogin login) {
		this.login = login;
	}
	
	public String reservarViaje(){
		System.out.println("Viaje seleccionado = " + viajeSeleccionado.getId());
		ViajeJPA viaje = ControladorBlaBlaCar.getInstancia().buscarViaje(viajeSeleccionado.getId());
		if (login.getUsuario() != null) {
			Usuario usu = ControladorBlaBlaCar.getInstancia().existeUsuario(login.getUsuario());
			if (usu != null && !ControladorBlaBlaCar.getInstancia().isConductorViaje(usu, viaje)
					&& !ControladorBlaBlaCar.getInstancia().isViajeReservado(viajeSeleccionado.getId(), usu)){
				ControladorBlaBlaCar.getInstancia().reservarViaje(usu, viaje, comentario);
				setComentario("");
				return "faceletsExito";
			} else {
				setComentario("");
				return "faceletsFallo";
			}
		} else {
			return "faceletsLogin";
		}
	}

}
