package dao;

import java.util.Collection;
import java.util.List;
import model.Orden;
import model.ReservaJPA;
import model.Usuario;
import model.ViajeJPA;

public interface ViajeDAO {
	public ViajeJPA createViaje(Usuario usuario,int plazas, double precio);
	public ReservaJPA buscarReservaUsuario(int idViaje,Usuario usuario);
	public void aņadirReserva(int viajeId,ReservaJPA reserva);
	public List<ViajeJPA> getListaViajes(Usuario usuario, boolean pendientes, boolean realizados, boolean propios, Orden ordenFecha, 
			Orden ordenCiudad);	
	
	public List<ViajeJPA> findAll();
	public List<ReservaJPA> buscarReservasViaje(int idViaje);
	public ViajeJPA findViajeId(int id);
	public Usuario getConductorViaje(int idViaje);
	public List<Usuario> buscarUsuariosViaje(int idViaje);
	public List<ViajeJPA> getListaViajesRealizados(Usuario usu);
	public Collection<ViajeJPA> getListaViajesAReservar(String usuario);

}
