package servlet;

import java.util.LinkedList;
import java.util.List;

public class VerSugerencias {

	private static List<String> sugerenciasRecibidas = new LinkedList<String>();
	
	/*public VerSugerencias() {
		sugerenciasRecibidas = new LinkedList<>();
	}*/

	public static List<String> getSugerenciasRecibidas() {
		return sugerenciasRecibidas;
	}

	public void setSugerenciasRecibidas(List<String> sugerenciasRecibidas) {
		VerSugerencias.sugerenciasRecibidas = sugerenciasRecibidas;
	}
	
	public static void anyadir(String sugerencia) {
		sugerenciasRecibidas.add(sugerencia);
	}
}
