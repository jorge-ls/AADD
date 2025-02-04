package controlador;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Orden;
import model.ParadaJPA;
import model.ReservaJPA;
import model.Usuario;
import model.ViajeJPA;
import servlet.VerSugerencias;

public class ControladorBlaBlaCar {
	
	private static ControladorBlaBlaCar unicaInstancia = null;
	private ControladorBlaBlaCarRemoto controladorBlaBlaCarRemoto;
	
	private ControladorBlaBlaCar() {
		try {
			controladorBlaBlaCarRemoto = (ControladorBlaBlaCarRemoto)
					new InitialContext().lookup("java:global/Practica-BlaBlaCar-EJB/ControladorBlaBlaCarRemoto");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static ControladorBlaBlaCar getInstancia(){
		if (unicaInstancia == null){
			unicaInstancia = new ControladorBlaBlaCar();
		}
		return unicaInstancia;
	}

	public List<Usuario> getUsuarios(){
		return controladorBlaBlaCarRemoto.getUsuarios();
	}
	
	public Usuario existeUsuario(String usuario){
		return controladorBlaBlaCarRemoto.existeUsuario(usuario);
	}
	
	public Usuario registroUsuario(String usuario, String clave, String email, String telefono){
		return controladorBlaBlaCarRemoto.registroUsuario(usuario, clave, email, telefono);
	}
	
	public boolean loginUsuario(String usuario, String clave){
		return controladorBlaBlaCarRemoto.loginUsuario(usuario, clave);
	}
	
	public boolean anyadirCoche(Usuario usuario, String matricula, String modelo, int a�o, int confort){
		return controladorBlaBlaCarRemoto.anyadirCoche(usuario, matricula, modelo, a�o, confort);

	}

	public ViajeJPA registrarViaje(Usuario usuario, int plazas, double precio) { 
		return controladorBlaBlaCarRemoto.registrarViaje(usuario, plazas, precio);
	}
	
	public ParadaJPA registrarParadaOrigen(int idViaje, String ciudad, String calle, int cp, Date fecha) {
		return controladorBlaBlaCarRemoto.registrarParadaOrigen(idViaje, ciudad, calle, cp, fecha);
	}
	
	public ParadaJPA registrarParadaDestino(int idViaje, String ciudad, String calle, int CP, Date fecha) {
		return controladorBlaBlaCarRemoto.registrarParadaDestino(idViaje, ciudad, calle, CP, fecha);
	}
	
	public ReservaJPA reservarViaje(Usuario usuario, ViajeJPA viaje, String comentario){
		return controladorBlaBlaCarRemoto.reservarViaje(usuario, viaje, comentario);
	}

	public boolean aceptarViaje(int idViaje, Usuario usuario){
		return controladorBlaBlaCarRemoto.aceptarViaje(idViaje, usuario);
	}
	

	public boolean rechazarViaje(int idViaje,Usuario usuario){
		return controladorBlaBlaCarRemoto.rechazarViaje(idViaje, usuario);
	}
	
	public boolean isViajeReservado(int idViaje,Usuario usuario){
		return controladorBlaBlaCarRemoto.isViajeReservado(idViaje, usuario);
	}
	
	public List<ViajeJPA> listarViajesUsuario(Usuario usuario, boolean pendientes, boolean realizados, boolean propios, Orden ordenFecha, 
			Orden ordenCiudad) {
		return controladorBlaBlaCarRemoto.listarViajesUsuario(usuario, pendientes, realizados, propios, ordenFecha, ordenCiudad);
	}
	
	public Collection<ViajeJPA> listarViajes(){
		return controladorBlaBlaCarRemoto.listarViajes();
	}
	
	public ViajeJPA buscarViaje(int idViaje){
		return controladorBlaBlaCarRemoto.buscarViaje(idViaje);
	}
	
	public Usuario buscarUsuarioReserva(int reservaId){
		return controladorBlaBlaCarRemoto.buscarUsuarioReserva(reservaId);
	}
	
	public Usuario getConductorViaje(int idViaje){
		return controladorBlaBlaCarRemoto.getConductorViaje(idViaje);
	}
	
	public List<Usuario> getUsuariosViaje(int idViaje, String usuario){
		return controladorBlaBlaCarRemoto.getUsuariosViaje(idViaje, usuario);
	}
	
	public List<ReservaJPA> getReservasPendientes(int idViaje){
		return controladorBlaBlaCarRemoto.getReservasPendientes(idViaje);
	}

	public boolean isConductorViaje(Usuario usu, ViajeJPA viaje) {
		return controladorBlaBlaCarRemoto.isConductorViaje(usu, viaje);
	}

	public boolean hasCar(Usuario usu) {
		return controladorBlaBlaCarRemoto.hasCar(usu);
	}
	

	public Collection<ViajeJPA> listarViajesAReservar(String usuario) {
		return controladorBlaBlaCarRemoto.listarViajesAReservar(usuario);
	}

	public List<ViajeJPA> listarViajesRealizados(Usuario usu) {
		return controladorBlaBlaCarRemoto.listarViajesRealizados(usu);
	}

	public List<String> getSugerenciasRecibidas() {
		return VerSugerencias.getSugerenciasRecibidas();
	}
}
