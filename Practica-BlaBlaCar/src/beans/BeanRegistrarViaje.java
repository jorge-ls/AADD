package beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import controlador.ControladorBlaBlaCar;
import model.ParadaJPA;
import model.Usuario;
import model.ViajeJPA;

@ManagedBean(name = "beanRegistrarViaje")
@SessionScoped
public class BeanRegistrarViaje implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin login;
	private int plazas, cpOrigen, cpDestino;
	private double precio;
	private String ciudadOrigen, ciudadDestino, calleOrigen, calleDestino;
	private Date fechaOrigen, fechaDestino;
	
	public BeanLogin getLogin() {
		return login;
	}

	public void setLogin(BeanLogin login) {
		this.login = login;
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public int getCpOrigen() {
		return cpOrigen;
	}

	public void setCpOrigen(int cpOrigen) {
		this.cpOrigen = cpOrigen;
	}

	public int getCpDestino() {
		return cpDestino;
	}

	public void setCpDestino(int cpDestino) {
		this.cpDestino = cpDestino;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(String ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public String getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public Date getFechaOrigen() {
		return fechaOrigen;
	}

	public void setFechaOrigen(Date fechaOrigen) {
		this.fechaOrigen = fechaOrigen;
	}

	public Date getFechaDestino() {
		return fechaDestino;
	}

	public void setFechaDestino(Date fechaDestino) {
		this.fechaDestino = fechaDestino;
	}

	public String getCalleOrigen() {
		return calleOrigen;
	}

	public void setCalleOrigen(String calleOrigen) {
		this.calleOrigen = calleOrigen;
	}

	public String getCalleDestino() {
		return calleDestino;
	}

	public void setCalleDestino(String calleDestino) {
		this.calleDestino = calleDestino;
	}

	public String registroViaje() {
		if (login.getUsuario() != null) {
			Usuario usu = ControladorBlaBlaCar.getInstancia().existeUsuario(login.getUsuario());
			//Date fechaActual = new Date();
			Calendar calendar = Calendar.getInstance();
			/*fechaActual.setHours(0);
			fechaActual.setMinutes(0);
			fechaActual.setSeconds(0);*/
			calendar.set(Calendar.HOUR_OF_DAY,0);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND,0);
			calendar.set(Calendar.MILLISECOND,0);
			Date fechaActual = calendar.getTime();
			//long milisegundos = fechaActual.getTime() % 1000;
			//fechaActual.setTime(fechaActual.getTime() - milisegundos);
			if (plazas > 0 && fechaOrigen.getTime() <= fechaDestino.getTime() && fechaActual.getTime() <= fechaOrigen.getTime()) {
				if (ControladorBlaBlaCar.getInstancia().hasCar(usu)) {
					ViajeJPA viaje = ControladorBlaBlaCar.getInstancia().registrarViaje(usu, plazas, precio);
					if (viaje != null) {			
						ParadaJPA paradaOrigen = ControladorBlaBlaCar.getInstancia().registrarParadaOrigen(viaje.getId(), ciudadOrigen, calleOrigen, 
								cpOrigen, fechaOrigen);
						ParadaJPA paradaDestino = ControladorBlaBlaCar.getInstancia().registrarParadaDestino(viaje.getId(), ciudadDestino, calleDestino, 
								cpDestino, fechaDestino);
						if (paradaOrigen != null && paradaDestino != null) {
							setPlazas(0);
							setPrecio(0.0);
							setCalleDestino("");
							setCalleOrigen("");
							setCiudadDestino("");
							setCiudadOrigen("");
							setCpDestino(0);
							setCpOrigen(0);
							setFechaOrigen(null);
							setFechaDestino(null);
							return "faceletsExito";
						} else {
							setPlazas(0);
							setPrecio(0.0);
							setCalleDestino("");
							setCalleOrigen("");
							setCiudadDestino("");
							setCiudadOrigen("");
							setCpDestino(0);
							setCpOrigen(0);
							setFechaOrigen(null);
							setFechaDestino(null);
							return "faceletsRegistrarViaje";
						}
					} else {
						setPlazas(0);
						setPrecio(0.0);
						setCalleDestino("");
						setCalleOrigen("");
						setCiudadDestino("");
						setCiudadOrigen("");
						setCpDestino(0);
						setCpOrigen(0);
						setFechaOrigen(null);
						setFechaDestino(null);
						return "faceletsFallo";
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Necesitas tener un coche para registrar un viaje", null));
					return "faceletsAnyadirCoche";
				}
			} else {
				if (plazas <= 0)
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Número de plazas demasiado pequeño", null));
				if (fechaOrigen.getTime() > fechaDestino.getTime())
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"La fecha de salida no puede ser posterior a la de llegada", null));
				if (fechaActual.getTime() > fechaOrigen.getTime())
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"La fecha de salida no puede ser anterior a la actual", null));
				return "faceletsRegistrarViaje";
			}
		} else {
			return "faceletsLogin";
		}
	}
}
