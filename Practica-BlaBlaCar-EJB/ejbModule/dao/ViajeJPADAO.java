package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import model.Coche;
import model.EstadoReserva;
import model.Orden;
import model.ReservaJPA;
import model.Usuario;
import model.ViajeJPA;

public class ViajeJPADAO implements ViajeDAO {

	
	private EntityManager em;
	
	public ViajeJPADAO(EntityManager em){
		this.em = em;
	}
	@Override
	public ViajeJPA createViaje(Usuario usuario, int plazas, double precio) {
		usuario = em.find(Usuario.class, usuario.getUsuario());
		if (usuario.getCoche() != null) {
			ViajeJPA viaje = new ViajeJPA();
			viaje.setPlazas(plazas);
			viaje.setPrecio(precio);
			ArrayList<String> notas = new ArrayList<String>(Arrays.asList("Nota1","Nota2"));
			viaje.setNotas(notas);
			Coche coche = usuario.getCoche();
			viaje.setCoche(coche);
			coche.addViaje(viaje);
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			try{
				em.persist(viaje);
				tx.commit();
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
			return viaje;
		} else {
			return null;
		}
	}
	
	@Override
	public void aņadirReserva(int viajeId,ReservaJPA reserva) {
		ViajeJPA viaje = em.find(ViajeJPA.class,viajeId);
		viaje.addReserva(reserva);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			tx.commit();
		} catch (Exception e){
		}
	}
	
	@Override
	public ReservaJPA buscarReservaUsuario(int idViaje, Usuario usuario) {
		ViajeJPA viaje = em.find(ViajeJPA.class,idViaje);
		String consulta = "SELECT r FROM ReservaJPA r WHERE r.viaje = :viaje AND r.usuario = :usuario";
		Query query = em.createQuery(consulta);
		query.setParameter("viaje",viaje);
		query.setParameter("usuario",usuario);
		List<ReservaJPA> reservas = query.getResultList();
		if (reservas.size() > 0)
			return reservas.get(0);
		return null;
	}
	
	public List<ReservaJPA> buscarReservasViaje(int idViaje){
		String consulta = "SELECT r FROM ReservaJPA r WHERE r.viaje.id = :idViaje and r.estado = :estado";
		Query query = em.createQuery(consulta);
		query.setParameter("idViaje",idViaje);
		query.setParameter("estado", EstadoReserva.PENDIENTE);
		List<ReservaJPA> reservas = query.getResultList();
		if (reservas.size() > 0)
			return reservas;
		return null;
	}
	
	public List<Usuario> buscarUsuariosViaje(int idViaje){
		Date fechaDestino = em.find(ViajeJPA.class,idViaje).getDestino().getFecha();
		Date fechaActual = new Date();
		if (fechaDestino.getTime() <= fechaActual.getTime()){
			String consulta = "SELECT r FROM ReservaJPA r WHERE r.viaje.id = :idViaje and r.estado = :estado";
			Query query = em.createQuery(consulta);
			query.setParameter("idViaje", idViaje);
			query.setParameter("estado", EstadoReserva.ACEPTADA);
			List<ReservaJPA> reservas = query.getResultList();
			List<Usuario> usuarios = new LinkedList<Usuario>();
			for (ReservaJPA reserva : reservas) {
				usuarios.add(em.find(Usuario.class,reserva.getUsuario().getUsuario()));
			}
			return usuarios;
		}
		return null;
	}
	
	@Override
	public List<ViajeJPA> getListaViajes(Usuario usuario, boolean pendientes, boolean realizados, boolean propios, Orden ordenFecha, 
			Orden ordenCiudad) {
		List<ViajeJPA> viajes = new LinkedList<ViajeJPA>();
		//Usuario usu = em.find(Usuario.class, usuario.getUsuario());
		if (pendientes) {
			/*List<ReservaJPA> reservas = usuario.getReservas();
			for (ReservaJPA reserva : reservas)
				if (em.find(ReservaJPA.class, reserva.getId()).getEstado() == EstadoReserva.PENDIENTE)
					viajes.add(em.find(ViajeJPA.class, reserva.getViaje().getId()));*/
			String consulta = "SELECT r FROM ReservaJPA r WHERE r.usuario.usuario = :nombre and r.estado = :estado";
			Query query = em.createQuery(consulta);
			query.setParameter("nombre", usuario.getUsuario());
			query.setParameter("estado", EstadoReserva.PENDIENTE);
			List<ReservaJPA> reservas = query.getResultList();
			for (ReservaJPA reserva : reservas) {
				viajes.add(em.find(ViajeJPA.class, reserva.getViaje().getId()));
			}
		}
		if (realizados) {
			String consulta = "SELECT r FROM ReservaJPA r WHERE r.usuario.usuario = :nombre and r.estado = :estado";
			Query query = em.createQuery(consulta);
			query.setParameter("nombre", usuario.getUsuario());
			query.setParameter("estado", EstadoReserva.ACEPTADA);
			List<ReservaJPA> reservas = query.getResultList();
			for (ReservaJPA reserva : reservas) {

				Date fechaActual = new Date();
				Date fechaDestino = em.find(ReservaJPA.class, reserva.getId()).getViaje().getDestino().getFecha();
				if (fechaDestino.getTime() <= fechaActual.getTime()){
				viajes.add(em.find(ViajeJPA.class, reserva.getViaje().getId()));
				}
				
			}
			/*List<ReservaJPA> reservas = usuario.getReservas();
			for (ReservaJPA reserva : reservas) {
				Date fechaActual = new Date();
				Date fechaDestino = em.find(ReservaJPA.class, reserva.getId()).getViaje().getDestino().getFecha();
				if (fechaDestino.getTime() <= fechaActual.getTime() && em.find(ReservaJPA.class, reserva.getId()).getEstado() == EstadoReserva.ACEPTADA)
					viajes.add(em.find(ViajeJPA.class, reserva.getViaje().getId()));
			}*/
		}
		if (propios) {
			if (usuario.getCoche() != null){
				Coche coche = em.find(Coche.class, usuario.getCoche().getMatricula());
				List<ViajeJPA> viajesPropios = coche.getViajes();
				for (ViajeJPA viaje : viajesPropios) {
					viajes.add(em.find(ViajeJPA.class, viaje.getId()));
				}
			}
		}
		if (ordenCiudad.equals(Orden.ASCENDENTE))
			viajes.sort((o1, o2) -> o1.getOrigen().getCiudad().compareTo(o2.getOrigen().getCiudad()));
		else if (ordenCiudad.equals(Orden.DESCENDENTE))
			viajes.sort((o1, o2) -> o2.getOrigen().getCiudad().compareTo(o1.getOrigen().getCiudad()));
		else if (ordenFecha.equals(Orden.ASCENDENTE))
			viajes.sort((o1, o2) -> o1.getOrigen().getFecha().compareTo(o2.getOrigen().getFecha()));
		else if (ordenFecha.equals(Orden.DESCENDENTE))
			viajes.sort((o1, o2) -> o2.getOrigen().getFecha().compareTo(o1.getOrigen().getFecha()));
		
		return viajes;
	}
	
	public List<ViajeJPA> findAll(){
		Query query = em.createQuery("SELECT v FROM ViajeJPA v");
		List<ViajeJPA> viajes = query.getResultList();
		return viajes;
	}
	@Override
	public ViajeJPA findViajeId(int id) {
		ViajeJPA viaje = em.find(ViajeJPA.class,id);
		return viaje;
	}
	@Override
	public Usuario getConductorViaje(int idViaje) {
		ViajeJPA viaje = em.find(ViajeJPA.class, idViaje);
		Coche coche = viaje.getCoche();
		return coche.getUsuario();
	}
	
	
	@Override
	public Collection<ViajeJPA> getListaViajesAReservar(String usuario) {
		Usuario usu = em.find(Usuario.class,usuario);
		List<ViajeJPA> viajes = findAll();
		List<ViajeJPA> viajesReservar = new LinkedList<ViajeJPA>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		Date fechaActual = calendar.getTime();
		//Date fechaActual = new Date();
		fechaActual.setHours(0);
		fechaActual.setMinutes(0);
		fechaActual.setSeconds(0);
		long milisegundos = fechaActual.getTime() % 1000;
		fechaActual.setTime(fechaActual.getTime() - milisegundos);
		for (ViajeJPA viaje : viajes) {

			if (viaje.getOrigen() != null && viaje.getOrigen().getFecha().getTime() >= fechaActual.getTime() && 
					!viaje.getConductorViaje().getUsuario().equals(usuario) 
						&& buscarReservaUsuario(viaje.getId(), usu) == null) {
				viajesReservar.add(viaje);
			}
		}
		return viajesReservar;
	}

	@Override
	public List<ViajeJPA> getListaViajesRealizados(Usuario usu) {
		List<ViajeJPA> viajesRealizados = getListaViajes(usu, false, true, false, Orden.NINGUNO, Orden.NINGUNO);
		List<ViajeJPA> viajesPropios = getListaViajes(usu, false, false, true, Orden.NINGUNO, Orden.NINGUNO);
		Date fechaActual = new Date();
		for (ViajeJPA viaje : viajesPropios) {
			if (viaje.getDestino() != null && viaje.getDestino().getFecha().getTime() <= fechaActual.getTime())
				viajesRealizados.add(viaje);
		}
		return viajesRealizados;
	}

	
}
