package dao;

import javax.ejb.Stateless;

@Stateless(name="Factoria")
public class FactoriaDAO implements FactoriaDAOLocal {

	public static final int JPA = 0;
	protected FactoriaDAO factoria = null;
	
	public UsuarioDAO getUsuarioDAO(){return factoria.getUsuarioDAO();}
	public ViajeDAO getViajeDAO(){return factoria.getViajeDAO();}
	public ParadaDAO getParadaDAO(){return factoria.getParadaDAO();}
	public CocheDAO getCocheDAO(){return factoria.getCocheDAO();}
	public ReservaDAO getReservaDAO() {return factoria.getReservaDAO();}

	@Override
	public void setFactoriaDAO(int tipo) {
		switch(tipo) {
		case JPA:
			factoria = new FactoriaJPADAO();
			break;
		}
	}

}
