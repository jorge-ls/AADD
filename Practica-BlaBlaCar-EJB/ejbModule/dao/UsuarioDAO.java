package dao;

import java.util.List;

import model.Coche;
import model.ReservaJPA;
import model.Usuario;

public interface UsuarioDAO {
	
	Usuario createUsuario(String usuario,String clave,String email,String telefono);
	Usuario buscarUsuario(String nombre);
	public void a�adirCoche(String nombre, Coche coche);
	boolean tieneCoche(String nombre);
	void a�adirReserva(String nombre, ReservaJPA reserva);
	List<Usuario> buscarUsuarios();
	List<Usuario> getUsuariosViaje(int idViaje, String usuario);

}
