package beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.jms.JMSException;
import javax.naming.NamingException;

import controlador.ControladorBlaBlaCar;
import jms.PublicadorValoracion;
import jms.SuscriptorApartado;
import model.Usuario;
import model.ViajeJPA;


@ManagedBean(name="beanValoracion")
@SessionScoped
public class BeanValoracion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String puntuacion;
	private String comentario;
	private Usuario conductor;
	private List<Usuario> usuarios;
	private Usuario usuarioSeleccionado;
	@ManagedProperty(value = "#{beanViajesRealizados}")
	private BeanViajesRealizados viajesRealizados;
	@ManagedProperty(value = "#{beanLogin}")
	private BeanLogin login;
	private int idViajeSeleccionado;
	private Map<String, String> mensajesRecibidos;
	private String[] mensajesRecibidosSeleccionados = null;
	
	public BeanValoracion(){
		usuarios = new LinkedList<Usuario>();
		mensajesRecibidos = new LinkedHashMap<String, String>();
	}
	public BeanLogin getLogin() {
		return login;
	}
	
	public void setLogin(BeanLogin login) {
		this.login = login;
	}
	
	public int getIdViajeSeleccionado() {
		return idViajeSeleccionado;
	}
	
	public void setIdViajeSeleccionado(int idViajeSeleccionado) {
		this.idViajeSeleccionado = idViajeSeleccionado;
	}
	
	public String getPuntuacion() {
		return puntuacion;
	}
	
	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}
	
	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}
	
	public List<Usuario> getUsuarios() {
		if (viajesRealizados.getViajeSeleccionado() != null) {
			int idViaje = viajesRealizados.getViajeSeleccionado().getId();
			usuarios = ControladorBlaBlaCar.getInstancia().getUsuariosViaje(idViaje, login.getUsuario());
		}
		return usuarios;
	}
	
	public Usuario getConductor() {
		int idViaje = viajesRealizados.getViajeSeleccionado().getId();
		conductor = ControladorBlaBlaCar.getInstancia().getConductorViaje(idViaje);
		return conductor;
	}
	
	public void setConductor(Usuario conductor) {
		this.conductor = conductor;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void setPuntuacion(String puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	public String[] getMensajesRecibidosSeleccionados() {
		return mensajesRecibidosSeleccionados;
	}
	
	public void setMensajesRecibidosSeleccionados(
			String[] mensajesRecibidosSeleccionados) {
		this.mensajesRecibidosSeleccionados =
				mensajesRecibidosSeleccionados;
	}
	
	public Map<String, String> getMensajesRecibidos() {
		return mensajesRecibidos;
	}
	
	public void setMensajesRecibidos(Map<String, String> mensajesRecibidos)
	{
		this.mensajesRecibidos = mensajesRecibidos;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public BeanViajesRealizados getViajesRealizados() {
		return viajesRealizados;
	}

	public void setViajesRealizados(BeanViajesRealizados viajesRealizados) {
		this.viajesRealizados = viajesRealizados;
	}
	
	public void enviarTexto(ActionEvent event) {
		String resultado = "";
		if (puntuacion == null || puntuacion.equals("")) {
			resultado = "Se tiene que elegir una puntuacion de 1 a 10";
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",resultado));
		}
		
		PublicadorValoracion publicador = new PublicadorValoracion();
		try {

			boolean isConductor = false;
			ViajeJPA viajeSeleccionado = viajesRealizados.getViajeSeleccionado();
			if (ControladorBlaBlaCar.getInstancia().isConductorViaje(usuarioSeleccionado,viajeSeleccionado)){
				isConductor = true;
			}
			publicador.enviar(viajeSeleccionado.getId(),usuarioSeleccionado.getUsuario(),puntuacion,comentario,isConductor);
			resultado = "Envio realizado correctamente.";
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Successful",resultado));
		} catch (NamingException | JMSException e) {
			resultado = "Error durante el envio.";
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",resultado));
			e.printStackTrace();
		}
		
		puntuacion = "";
		comentario = "";
	}
	
	public void recibirTodosTexto(ActionEvent event) {
		List<ViajeJPA> viajes = viajesRealizados.getListaViajes();
		SuscriptorApartado suscriptor = new SuscriptorApartado();

		try {
			suscriptor.registrarApartado(viajes);
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}
	}

}
