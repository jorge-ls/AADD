package beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import controlador.ControladorBlaBlaCar;
import model.ReservaJPA;
import model.Usuario;

@ManagedBean(name = "beanReservasPendientes")
@SessionScoped
public class BeanReservasPendientes implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ReservaJPA> reservas = new LinkedList<ReservaJPA>(); ;
	@ManagedProperty(value = "#{beanViajesPropios}")
	private BeanViajesPropios viajesUsuario;
	private ReservaJPA reservaSeleccionada;
	
	public List<ReservaJPA> getReservas() {
		int idViaje = viajesUsuario.getViajeSeleccionado().getId();
		reservas= ControladorBlaBlaCar.getInstancia().getReservasPendientes(idViaje);
		return reservas;
	}
	
	
	public void setReservas(List<ReservaJPA> reservas) {
		this.reservas = reservas;
	}
	
	public BeanViajesPropios getViajesUsuario() {
		return viajesUsuario;
	}
	
	public void setViajesUsuario(BeanViajesPropios viajesUsuario) {
		this.viajesUsuario = viajesUsuario;
	}
	
	public ReservaJPA getReservaSeleccionada() {
		return reservaSeleccionada;
	}
	
	public void setReservaSeleccionada(ReservaJPA reservaSeleccionada) {
		this.reservaSeleccionada = reservaSeleccionada;
	}
	
	
	public String aceptarViaje(){
		int idViaje = viajesUsuario.getViajeSeleccionado().getId();
		Usuario usu = ControladorBlaBlaCar.getInstancia().buscarUsuarioReserva(reservaSeleccionada.getId());
		if (usu!=null){
			if (ControladorBlaBlaCar.getInstancia().aceptarViaje(idViaje, usu)){
				return "faceletsExito";
			}
		}
		return "faceletsFallo";
	}
	
	public String rechazarViaje(){
		int idViaje = viajesUsuario.getViajeSeleccionado().getId();
		Usuario usu = ControladorBlaBlaCar.getInstancia().buscarUsuarioReserva(reservaSeleccionada.getId());
		if (usu!=null){
			if (ControladorBlaBlaCar.getInstancia().rechazarViaje(idViaje, usu)){
				return "faceletsExito";
			}
		}
		return "faceletsFallo";
	}
	
	
}
