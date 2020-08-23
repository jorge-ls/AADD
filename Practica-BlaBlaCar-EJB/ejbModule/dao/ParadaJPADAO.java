package dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.Direccion;
import model.ParadaJPA;
import model.ViajeJPA;

public class ParadaJPADAO implements ParadaDAO {
	
	private EntityManager em;
	
	public ParadaJPADAO(EntityManager em){
		this.em = em;
	}

	@Override
	public ParadaJPA createParadaOrigen(int idViaje, String ciudad, String calle, int CP, Date fecha) {
		ParadaJPA parada = new ParadaJPA();
		parada.setCiudad(ciudad);
		parada.setFecha(fecha);
		Direccion dir = new Direccion();
		dir.setCalle(calle);
		dir.setCP(CP);
		parada.setDireccion(dir);
		ViajeJPA viaje = em.find(ViajeJPA.class, idViaje);
		viaje.setOrigen(parada);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.persist(parada);
			tx.commit();
		} catch (Exception e){
			return null;
		}
		return parada;
	}

	@Override
	public ParadaJPA createParadaDestino(int idViaje, String ciudad, String calle, int CP, Date fecha) {
		ParadaJPA parada = new ParadaJPA();
		parada.setCiudad(ciudad);
		parada.setFecha(fecha);
		Direccion dir = new Direccion();
		dir.setCalle(calle);
		dir.setCP(CP);
		parada.setDireccion(dir);
		ViajeJPA viaje = em.find(ViajeJPA.class, idViaje);
		viaje.setDestino(parada);
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.persist(parada);
			tx.commit();
		} catch (Exception e){
			return null;
		}
		return parada;
	}
	

}
