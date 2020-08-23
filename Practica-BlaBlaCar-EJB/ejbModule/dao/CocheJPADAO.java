package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.Coche;
import model.Usuario;

public class CocheJPADAO implements CocheDAO {

	
	private EntityManager em;
	
	public CocheJPADAO(EntityManager em) {
		this.em = em;
	}
	
	public Coche createCoche(Usuario usuario, String matricula, String modelo, int año, int confort) {
		if (em.find(Coche.class, matricula) == null && usuario != null && usuario.getCoche() == null) {
			Coche coche = new Coche();
			coche.setMatricula(matricula);
			coche.setModelo(modelo);
			coche.setAño(año);
			coche.setConfort(confort);
			coche.setUsuario(usuario);
			Usuario usu = em.find(Usuario.class, usuario.getUsuario());
			usu.setCoche(coche);
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			try{
				em.persist(coche);
				tx.commit();
			} catch (Exception e){
				return null;
			}
			return coche;
		}
		return null;
	}
	

}
