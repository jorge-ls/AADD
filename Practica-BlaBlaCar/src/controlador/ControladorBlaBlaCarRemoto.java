package controlador;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import model.Orden;
import model.ParadaJPA;
import model.ReservaJPA;
import model.Usuario;
import model.ViajeJPA;

@Remote
public interface ControladorBlaBlaCarRemoto {

	List<Usuario> getUsuarios();
	Usuario existeUsuario(String usuario);
	Usuario registroUsuario(String usuario, String clave, String email, String telefono);
	boolean loginUsuario(String usuario, String clave);
	boolean anyadirCoche(Usuario usuario, String matricula, String modelo, int a�o, int confort);
	ViajeJPA registrarViaje(Usuario usuario, int plazas, double precio);
	ParadaJPA registrarParadaOrigen(int idViaje, String ciudad, String calle, int cp, Date fecha);
	ParadaJPA registrarParadaDestino(int idViaje, String ciudad, String calle, int CP, Date fecha);
	ReservaJPA reservarViaje(Usuario usuario, ViajeJPA viaje, String comentario);
	boolean aceptarViaje(int idViaje, Usuario usuario);
	boolean rechazarViaje(int idViaje,Usuario usuario);
	boolean isViajeReservado(int idViaje,Usuario usuario);
	List<ViajeJPA> listarViajesUsuario(Usuario usuario, boolean pendientes, boolean realizados, boolean propios, Orden ordenFecha, 
			Orden ordenCiudad);
	Collection<ViajeJPA> listarViajes();
	ViajeJPA buscarViaje(int idViaje);
	Usuario buscarUsuarioReserva(int reservaId);
	Usuario getConductorViaje(int idViaje);
	List<Usuario> getUsuariosViaje(int idViaje, String usuario);
	List<ReservaJPA> getReservasPendientes(int idViaje);
	boolean isConductorViaje(Usuario usu, ViajeJPA viaje);
	boolean hasCar(Usuario usu);
	Collection<ViajeJPA> listarViajesAReservar(String usuario);
	List<ViajeJPA> listarViajesRealizados(Usuario usu);
}
