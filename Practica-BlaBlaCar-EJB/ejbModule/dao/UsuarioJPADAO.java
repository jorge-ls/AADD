package dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Coche;
import model.EstadoReserva;
import model.ReservaJPA;
import model.Usuario;
import model.ViajeJPA;

public class UsuarioJPADAO implements UsuarioDAO {
	
	private EntityManager em;
	
	public UsuarioJPADAO(EntityManager em){
		this.em = em;
	}

	@Override
	public Usuario createUsuario(String usuario,String clave, String email,String telefono){
		Usuario c = new Usuario();
		c.setUsuario(usuario);
		c.setClave(clave);
		c.setEmail(email);
		c.setTelefono(telefono);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.persist(c);
			tx.commit();
		} catch (Exception e){
			return null;
		}
		return c;
	}

	@Override
	public Usuario buscarUsuario(String nombre) {
		Usuario usu = em.find(Usuario.class, nombre);
		return usu;
	}

	@Override
	public boolean tieneCoche(String nombre) {
		Usuario usu = em.find(Usuario.class, nombre);
		if (usu.getCoche() == null) 
			return false;
		return true;
	}

	@Override
	public void aņadirCoche(String nombre, Coche coche) {
		Usuario usuario = em.find(Usuario.class, nombre);
		usuario.setCoche(coche);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			tx.commit();
		} catch (Exception e){
		}
	}

	@Override
	public void aņadirReserva(String nombre, ReservaJPA reserva) {
		Usuario usuario = em.find(Usuario.class,nombre);
		usuario.addReserva(reserva);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			tx.commit();
		} catch (Exception e){
			
		}
	}

	@Override
	public List<Usuario> buscarUsuarios() {
		Query query = em.createQuery("SELECT u FROM Usuario u");
		List<Usuario> usuarios = query.getResultList();
		return usuarios;
	}

	@Override
	public List<Usuario> getUsuariosViaje(int idViaje, String usuario) {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		Query query = em.createQuery("SELECT r FROM ReservaJPA r WHERE r.viaje.id = :id AND r.estado = :estado");
		query.setParameter("id", idViaje);
		query.setParameter("estado", EstadoReserva.ACEPTADA);
		List<ReservaJPA> reservas = query.getResultList();
		for (ReservaJPA reserva : reservas) {
			if (!reserva.getUsuario().getUsuario().equals(usuario))
				usuarios.add(reserva.getUsuario());
		}
		return usuarios;
	}
	
}
