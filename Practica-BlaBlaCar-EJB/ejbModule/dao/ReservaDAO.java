package dao;

import model.ReservaJPA;
import model.Usuario;
import model.ViajeJPA;

public interface ReservaDAO {
	
	public ReservaJPA createReserva(Usuario usuario,ViajeJPA viaje,String comentario);
	public void aceptarReserva(int reservaId);
	public void rechazarReserva(int reservaId);
	public Usuario buscarUsuarioReserva(int reservaId);
}
