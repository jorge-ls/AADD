package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.EstadoReserva;
import model.ReservaJPA;
import model.Usuario;
import model.ViajeJPA;

public class ReservaJPADAO implements ReservaDAO {

private EntityManager em;
	
	public ReservaJPADAO(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public ReservaJPA createReserva(Usuario usuario, ViajeJPA viaje, String comentario) {
		ReservaJPA reserva = new ReservaJPA();
		reserva.setComentario(comentario);
		reserva.setUsuario(usuario);
		reserva.setViaje(viaje);
		reserva.setEstado(EstadoReserva.PENDIENTE);
		EntityTransaction tx = em.getTransaction();
		//Usuario usu = em.find(Usuario.class, usuario.getUsuario());
		usuario.addReserva(reserva);
		//ViajeJPA via = em.find(ViajeJPA.class, viaje.getId());
		viaje.addReserva(reserva);
		tx.begin();
		try{
			em.persist(reserva);
			tx.commit();
		} catch (Exception e){
			return null;
		}
		return reserva;
	}
	
	@Override
	public void aceptarReserva(int reservaId){
		ReservaJPA reserva = em.find(ReservaJPA.class, reservaId);
		reserva.setEstado(EstadoReserva.ACEPTADA);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			tx.commit();
		} catch (Exception e){
			
		}
	}
	
	@Override
	public void rechazarReserva(int reservaId){
		ReservaJPA reserva = em.find(ReservaJPA.class, reservaId);
		reserva.setEstado(EstadoReserva.RECHAZADA);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			tx.commit();
		} catch (Exception e){
			
		}
	}

	@Override
	public Usuario buscarUsuarioReserva(int reservaId) {
		ReservaJPA reserva = em.find(ReservaJPA.class,reservaId);
		return reserva.getUsuario();
	}
	
	

}
