package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoriaJPADAO extends FactoriaDAO{
	
	private EntityManagerFactory emf;
	
	
	protected FactoriaJPADAO() {
		emf = Persistence.createEntityManagerFactory("aadd");
	}
	
	public UsuarioDAO getUsuarioDAO(){
		synchronized (emf) {
			return new UsuarioJPADAO(emf.createEntityManager());
		}
	}
	

	@Override
	public ViajeDAO getViajeDAO() {
		synchronized (emf) {
			return new ViajeJPADAO(emf.createEntityManager());
		}
	}

	@Override
	public ParadaDAO getParadaDAO() {
		synchronized (emf) {
			return new ParadaJPADAO(emf.createEntityManager());
		}
	}
	@Override
	public CocheDAO getCocheDAO() {
		synchronized (emf) {
			return new CocheJPADAO(emf.createEntityManager());
		}
	}

	@Override
	public ReservaDAO getReservaDAO() {
		synchronized (emf) {
			return new ReservaJPADAO(emf.createEntityManager());
		}
	}
	
	public void finalice () throws Throwable{
		emf.close();
		super.finalize();
		
	}

}
