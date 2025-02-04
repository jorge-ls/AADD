package controlador;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

import dao.CocheDAO;
import dao.FactoriaDAO;
import dao.FactoriaDAOLocal;
import dao.ParadaDAO;
import dao.ReservaDAO;
import dao.UsuarioDAO;
import dao.ViajeDAO;
import model.Coche;
import model.Orden;
import model.ParadaJPA;
import model.ReservaJPA;
import model.Usuario;
import model.ViajeJPA;

@Stateful(name="ControladorBlaBlaCarRemoto")
public class ControladorBlaBlaCar implements ControladorBlaBlaCarRemoto {
	
	private Usuario usuarioActual = null;
	@EJB(beanName="Factoria")
	private FactoriaDAOLocal factoria;
	@Resource
	private SessionContext contexto;

	@PostConstruct
	public void configurarBlaBlaCarEJB(){
		factoria.setFactoriaDAO(FactoriaDAO.JPA);
	}
	
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
	public List<Usuario> getUsuarios(){
		UsuarioDAO usuDAO = factoria.getUsuarioDAO();
		return usuDAO.buscarUsuarios();
	}
	
	public Usuario existeUsuario(String usuario){
		UsuarioDAO usuDAO = factoria.getUsuarioDAO();
		return usuDAO.buscarUsuario(usuario);
	}
	
	public Usuario registroUsuario(String usuario, String clave, String email, String telefono){
		UsuarioDAO usuDAO = factoria.getUsuarioDAO();
		return usuDAO.createUsuario(usuario, clave, email, telefono);
	}
	
	public boolean loginUsuario(String usuario, String clave){
		UsuarioDAO usuDAO = factoria.getUsuarioDAO();
		Usuario usu = usuDAO.buscarUsuario(usuario);
		if (usu !=null){
			usuarioActual = usu;
			return usu.getClave().equals(clave);
		}
		return false;
	}
	
	public boolean anyadirCoche(Usuario usuario, String matricula, String modelo, int a�o, int confort){
		CocheDAO cocheDAO = factoria.getCocheDAO();
		Coche coche = cocheDAO.createCoche(usuario, matricula, modelo, a�o, confort);
		return coche != null;
	}

	public ViajeJPA registrarViaje(Usuario usuario, int plazas, double precio) { 
		ViajeDAO viajeDAO = factoria.getViajeDAO();
		ViajeJPA viaje = viajeDAO.createViaje(usuario, plazas, precio);
		return viaje;
	}
	
	public ParadaJPA registrarParadaOrigen(int idViaje, String ciudad, String calle, int cp, Date fecha) {
		ParadaDAO paradaDAO = factoria.getParadaDAO();
		ParadaJPA parada = paradaDAO.createParadaOrigen(idViaje, ciudad,calle, cp, fecha);
		return parada;
	}
	
	public ParadaJPA registrarParadaDestino(int idViaje, String ciudad, String calle, int CP, Date fecha) {
		ParadaDAO paradaDAO = factoria.getParadaDAO();
		ParadaJPA parada = paradaDAO.createParadaDestino(idViaje, ciudad, calle, CP, fecha);
		return parada;
	}
	
	public ReservaJPA reservarViaje(Usuario usuario, ViajeJPA viaje, String comentario){
		ReservaDAO reservaDAO = factoria.getReservaDAO();
		ReservaJPA reserva = reservaDAO.createReserva(usuario, viaje, comentario);
		return reserva;
	}

	public boolean aceptarViaje(int idViaje, Usuario usuario){
		ViajeDAO viajeDAO = factoria.getViajeDAO();
		ReservaJPA reserva = viajeDAO.buscarReservaUsuario(idViaje, usuario);
		ReservaDAO reservaDAO = factoria.getReservaDAO();
		if (reserva != null){
			reservaDAO.aceptarReserva(reserva.getId());
			return true;
		}
		return false;
	}
	

	public boolean rechazarViaje(int idViaje,Usuario usuario){
		ViajeDAO viajeDAO = factoria.getViajeDAO();
		ReservaJPA reserva = viajeDAO.buscarReservaUsuario(idViaje,usuario);
		ReservaDAO reservaDAO = factoria.getReservaDAO();
		if (reserva != null){
			reservaDAO.rechazarReserva(reserva.getId());
			return true;
		}
		return false;
	}
	
	public boolean isViajeReservado(int idViaje,Usuario usuario){
		ViajeDAO viajeDAO = factoria.getViajeDAO();
		ReservaJPA reserva = viajeDAO.buscarReservaUsuario(idViaje,usuario);
		if (reserva != null){
			return true;
		}
		return false;
	}
	
	public List<ViajeJPA> listarViajesUsuario(Usuario usuario, boolean pendientes, boolean realizados, boolean propios, Orden ordenFecha, 
			Orden ordenCiudad) {
		return factoria.getViajeDAO().getListaViajes(usuario, pendientes, realizados, propios, ordenFecha, ordenCiudad);
	}
	
	public Collection<ViajeJPA> listarViajes(){
		ViajeDAO viaDAO = factoria.getViajeDAO();
		Collection<ViajeJPA> viajes = viaDAO.findAll();
		return viajes;
	}
	
	public ViajeJPA buscarViaje(int idViaje){
		ViajeDAO viaDAO = factoria.getViajeDAO();
		return viaDAO.findViajeId(idViaje);
	}
	
	public Usuario buscarUsuarioReserva(int reservaId){
		ReservaDAO reDAO = factoria.getReservaDAO();
		return reDAO.buscarUsuarioReserva(reservaId);
	}
	
	public Usuario getConductorViaje(int idViaje){
		ViajeDAO viaDAO = factoria.getViajeDAO();
		return viaDAO.getConductorViaje(idViaje);
	}
	
	public List<Usuario> getUsuariosViaje(int idViaje,String usuario){
		UsuarioDAO usuDAO = factoria.getUsuarioDAO();
		List<Usuario> usuarios = usuDAO.getUsuariosViaje(idViaje, usuario);
		Usuario usu = getConductorViaje(idViaje);
		if (!usu.getUsuario().equals(usuario))
			usuarios.add(usu);
		return usuarios;
	}
	
	public List<ReservaJPA> getReservasPendientes(int idViaje){
		ViajeDAO viaDAO = factoria.getViajeDAO();
		return viaDAO.buscarReservasViaje(idViaje);
	}

	public boolean isConductorViaje(Usuario usu, ViajeJPA viaje) {
		Usuario conductorViaje = viaje.getCoche().getUsuario();
		return usu.getUsuario().equals(conductorViaje.getUsuario());
	}


	@Override
	public boolean hasCar(Usuario usu) {
		UsuarioDAO usuDAO = factoria.getUsuarioDAO();
		return usuDAO.tieneCoche(usu.getUsuario());
	}


	@Override
	public Collection<ViajeJPA> listarViajesAReservar(String usuario){
		ViajeDAO viaDAO = factoria.getViajeDAO();
		return viaDAO.getListaViajesAReservar(usuario);
	}

	@Override
	public List<ViajeJPA> listarViajesRealizados(Usuario usu) {
		ViajeDAO viaDAO = factoria.getViajeDAO();
		return viaDAO.getListaViajesRealizados(usu);
	}


	







}
