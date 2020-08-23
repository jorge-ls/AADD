package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.ControladorBlaBlaCar;


/**
 * Servlet implementation class ServletVerSugerencias
 */
@WebServlet("/ServletVerSugerencias")
public class ServletVerSugerencias extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletVerSugerencias() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("<html><head><title>Ver Sugerencias</title></head><body>");
		response.getWriter().append("<h2>Lista de sugerencias</h2>");
		List<String> sugerenciasRecibidas = ControladorBlaBlaCar.getInstancia().getSugerenciasRecibidas();
		for (String suge : sugerenciasRecibidas) {
			response.getWriter().append("<p>" + suge + "</p>");
		}
		response.getWriter().append("<form action='faceletsSugerencias.xhtml'><input type='submit' value='Volver' /></form></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
