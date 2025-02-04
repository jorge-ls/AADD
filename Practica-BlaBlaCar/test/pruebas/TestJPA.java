package pruebas;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;
import controlador.ControladorBlaBlaCar;
import model.Coche;
import model.EstadoReserva;
import model.Orden;
import model.ParadaJPA;
import model.ReservaJPA;
import model.Usuario;
import model.ViajeJPA;

public class TestJPA {
	
	private EntityManager em;
	
	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("aadd");
		em = emf.createEntityManager();
	}
	
	
	@Test
	public void testRegistroViaje() {
		String nombre = "user1";
		String clave = "8910";
		String email = "usuario1@um.es";
		String telefono = "968273945";
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		String matricula = "8476 DHR";
		String modelo = "X 45";
		int anyo = 2015;
		int confort = 3;
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, matricula, modelo, anyo, confort);
		usuario = em.find(Usuario.class, nombre);
		ViajeJPA viaje = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 125.0);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");

		java.util.Date fecha = null;
		try {
			fecha = formatoDelTexto.parse("01/01/2014");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		ControladorBlaBlaCar.getInstancia().registrarParadaOrigen(viaje.getId(), "Murcia", "C/Mayor,25", 30001, fecha);
		ControladorBlaBlaCar.getInstancia().registrarParadaDestino(viaje.getId(), "Madrid", "C/Gran V�a,25", 54385, fecha);	
		
		ViajeJPA viajeJPA = em.find(ViajeJPA.class, viaje.getId());
		assertNotNull(viajeJPA);
		assertNotNull(viajeJPA.getOrigen());
		assertNotNull(viajeJPA.getDestino());
		assertEquals(usuario.getCoche().getMatricula(), viajeJPA.getCoche().getMatricula());
	}
	
	@Test
	public void testRegistrarParadaOrigen() {
		String nombre = "user2";
		String clave = "1234";
		String email = "email@um.es";
		String telefono = "945283545";
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		String matricula = "8477 DHR";
		String modelo = "X 45";
		int anyo = 2015;
		int confort = 3;
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, matricula, modelo, anyo, confort);
		usuario = em.find(Usuario.class, nombre);
		ViajeJPA viaje = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 5, 30.0);
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date fecha = null;
		try {
			fecha = formatoDelTexto.parse("01/01/2014");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		ParadaJPA paradaOrigen = ControladorBlaBlaCar.getInstancia().registrarParadaOrigen(viaje.getId(), "Murcia", "C/Mayor,25", 30001, fecha);
		
		ParadaJPA origen = em.find(ParadaJPA.class, paradaOrigen.getId());
		ViajeJPA v = em.find(ViajeJPA.class, viaje.getId());
		assertNotNull(origen);
		assertEquals(paradaOrigen.getId(), origen.getId());
		assertEquals(origen.getId(), v.getOrigen().getId());
	}

	@Test
	public void testRegistrarParadaDestino() {
		String nombre = "user3";
		String clave = "1234";
		String email = "email@um.es";
		String telefono = "945283545";
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		String matricula = "8478 DHR";
		String modelo = "X 45";
		int anyo = 2015;
		int confort = 3;
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, matricula, modelo, anyo, confort);
		usuario = em.find(Usuario.class, nombre);
		ViajeJPA viaje = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 5, 30.0);
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date fecha = null;
		try {
			fecha = formatoDelTexto.parse("01/01/2014");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		ParadaJPA paradaDestino = ControladorBlaBlaCar.getInstancia().registrarParadaDestino(viaje.getId(), "Madrid", "C/Gran V�a,25", 54385, fecha);
		
		ParadaJPA destino = em.find(ParadaJPA.class, paradaDestino.getId());
		ViajeJPA v = em.find(ViajeJPA.class, viaje.getId());
		assertNotNull(destino);
		assertEquals(paradaDestino.getId(), destino.getId());
		assertEquals(destino.getId(), v.getDestino().getId());
	}
	
	@Test
	public void testLoginUsuario(){
		String nombre = "user4";
		String clave = "8910";
		String email = "usuario1@um.es";
		String telefono = "968273945";
		ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		boolean login = ControladorBlaBlaCar.getInstancia().loginUsuario(nombre, clave);
		assertTrue(login);
	}
	
	@Test
	public void testRegistroUsuario(){
		String nombre = "user5";
		String clave = "3456";
		String email = "user@um.es";
		String telefono = "943273945";
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		Usuario usuarioJPA = em.find(Usuario.class, nombre);
		assertNotNull(usuario);
		assertNotNull(usuarioJPA);
	}
	
	@Test
	public void testExisteUsuario() {
		Usuario user = ControladorBlaBlaCar.getInstancia().registroUsuario("user18", "1234", "user18@um.es", "634978279");
		Usuario userObtenido = ControladorBlaBlaCar.getInstancia().existeUsuario(user.getUsuario());
		assertEquals(user.getUsuario(), userObtenido.getUsuario());
	}
	
	@Test
	public void testAnyadirCoche() {
		String nombre = "user6";
		String clave = "1234";
		String email = "email@um.es";
		String telefono = "945283545";
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);

		String matricula = "3794 GHD";
		String modelo = "X 45";
		int anyo = 2015;
		int confort = 3;
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, matricula, modelo, anyo, confort);
		
		Coche coche = em.find(Coche.class, matricula);
		assertNotNull(coche);
		Usuario usu = em.find(Usuario.class, nombre);
		assertNotNull(usu.getCoche());
	}
	
	@Test
	public void testReservarViaje(){
		String nombre = "user7";
		String clave = "1234";
		String email = "email@um.es";
		String telefono = "945283545";
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		String matricula = "8479 DHR";
		String modelo = "X 45";
		int anyo = 2015;
		int confort = 3;
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, matricula, modelo, anyo, confort);
		usuario = em.find(Usuario.class, nombre);
		ViajeJPA viaje = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 4, 100.0);
		nombre = "user8";
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		ReservaJPA reserva = ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje, "comentario");
		ReservaJPA reser = em.find(ReservaJPA.class, reserva.getId());
		assertNotNull(reserva);
		assertNotNull(reser);
		assertEquals(viaje.getId(), reser.getViaje().getId());
		assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
		//Usuario usu = em.find(Usuario.class, usuario1.getUsuario());
		assertEquals(reser.getId(), usuario1.getReservas().get(0).getId());
	}
	
	@Test
	public void testAceptarViaje(){
		String nombre = "user9";
		String clave = "1234";
		String email = "email@um.es";
		String telefono = "945283545";
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		String matricula = "8480 DHR";
		String modelo = "X 45";
		int anyo = 2015;
		int confort = 3;
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, matricula, modelo, anyo, confort);
		usuario = em.find(Usuario.class, nombre);
		ViajeJPA viaje= ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 40.0);
		nombre = "user10";
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		ReservaJPA reserva = ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje, "comentario");
		boolean viajeAceptado = ControladorBlaBlaCar.getInstancia().aceptarViaje(viaje.getId(), usuario1);
		ReservaJPA reser = em.find(ReservaJPA.class, reserva.getId());
		assertTrue(viajeAceptado);
		assertEquals(EstadoReserva.ACEPTADA, reser.getEstado());
	}
	
	@Test
	public void testRechazarViaje(){
		String nombre = "user11";
		String clave = "1234";
		String email = "email@um.es";
		String telefono = "945283545";
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		String matricula = "8481 DHR";
		String modelo = "X 45";
		int anyo = 2015;
		int confort = 3;
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, matricula, modelo, anyo, confort);
		usuario = em.find(Usuario.class, nombre);
		ViajeJPA viaje= ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 60.0);
		nombre = "user12";
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		ReservaJPA reserva = ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje, "comentario");
		boolean viajeRechazado = ControladorBlaBlaCar.getInstancia().rechazarViaje(viaje.getId(), usuario1);
		ReservaJPA reser = em.find(ReservaJPA.class, reserva.getId());
		assertTrue(viajeRechazado);
		assertEquals(EstadoReserva.RECHAZADA, reser.getEstado());
	} 
	@Test
	public void testListarViajesUsuario() { 
		String nombre = "user13";
		String clave = "1234";
		String email = "email@um.es";
		String telefono = "945283545";
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		String matricula = "8482 DHR";
		String modelo = "X 45";
		int anyo = 2015;
		int confort = 3;
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, matricula, modelo, anyo, confort);
		usuario = em.find(Usuario.class, nombre);
		ViajeJPA viaje1 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 125.0);
		ViajeJPA viaje2 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 125.0);
		nombre = "user14";
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario(nombre, clave, email, telefono);
		matricula = "8483 HDH";
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario1, matricula, modelo, anyo, confort);
		usuario1 = em.find(Usuario.class, nombre);
		ViajeJPA viaje3 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario1, 3, 125.0);
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date fecha = null;
		try {
			fecha = formatoDelTexto.parse("01/01/2014");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		ControladorBlaBlaCar.getInstancia().registrarParadaOrigen(viaje1.getId(), "Albacete", "C/�valos", 64875, fecha);
		ControladorBlaBlaCar.getInstancia().registrarParadaDestino(viaje1.getId(), "Madrid", "C/Gran V�a,25", 54385, fecha);
		try {
			fecha = formatoDelTexto.parse("30/12/2020");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		ControladorBlaBlaCar.getInstancia().registrarParadaOrigen(viaje2.getId(), "Barcelona", "C/Diagonal", 348856, fecha);
		ControladorBlaBlaCar.getInstancia().registrarParadaDestino(viaje2.getId(), "Murcia", "C/Gran V�a,25", 54385, fecha);
		try {
			fecha = formatoDelTexto.parse("15/07/2015");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		ControladorBlaBlaCar.getInstancia().registrarParadaOrigen(viaje3.getId(), "Valencia", "C/No se", 94574, fecha);
		ControladorBlaBlaCar.getInstancia().registrarParadaDestino(viaje3.getId(), "Alicante", "C/Gran V�a,25", 56589, fecha);
		
		ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje1, "comentario");
		ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje2, "comentario");
		ControladorBlaBlaCar.getInstancia().aceptarViaje(viaje1.getId(), usuario1);
		ControladorBlaBlaCar.getInstancia().reservarViaje(usuario, viaje3, "comentario");
		ControladorBlaBlaCar.getInstancia().aceptarViaje(viaje3.getId(), usuario);
		
		//usuario = em.find(Usuario.class, usuario.getUsuario());
		Collection<ViajeJPA> lista = ControladorBlaBlaCar.getInstancia().listarViajesUsuario(usuario, true, true, true, Orden.NINGUNO, Orden.NINGUNO);
		List<ViajeJPA> list = (List<ViajeJPA>) lista;
		assertEquals(viaje3.getId(), list.get(0).getId());
		assertEquals(viaje1.getId(), list.get(1).getId());
		assertEquals(viaje2.getId(), list.get(2).getId());
		
		lista = ControladorBlaBlaCar.getInstancia().listarViajesUsuario(usuario1, true, true, true, Orden.NINGUNO, Orden.NINGUNO);
		list = (List<ViajeJPA>) lista;
		assertEquals(viaje2.getId(), list.get(0).getId());
		assertEquals(viaje1.getId(), list.get(1).getId());
		assertEquals(viaje3.getId(), list.get(2).getId());
	
		lista = ControladorBlaBlaCar.getInstancia().listarViajesUsuario(usuario1, true, true, false, Orden.NINGUNO, Orden.ASCENDENTE);
		list = (List<ViajeJPA>) lista;
		assertEquals(viaje1.getId(),list.get(0).getId());
		assertEquals(viaje2.getId(),list.get(1).getId());
		
		lista = ControladorBlaBlaCar.getInstancia().listarViajesUsuario(usuario, false, false, true, Orden.NINGUNO, Orden.DESCENDENTE);
		list = (List<ViajeJPA>) lista;
		assertEquals(viaje2.getId(), list.get(0).getId());
		assertEquals(viaje1.getId(), list.get(1).getId());
		
		lista = ControladorBlaBlaCar.getInstancia().listarViajesUsuario(usuario, false, true, true, Orden.ASCENDENTE, Orden.NINGUNO);
		list = (List<ViajeJPA>) lista;
		assertEquals(viaje1.getId(), list.get(0).getId());
		assertEquals(viaje3.getId(), list.get(1).getId());
		assertEquals(viaje2.getId(), list.get(2).getId());
		
		lista = ControladorBlaBlaCar.getInstancia().listarViajesUsuario(usuario, false, false, true, Orden.DESCENDENTE, Orden.NINGUNO);
		list = (List<ViajeJPA>) lista;
		assertEquals(viaje2.getId(), list.get(0).getId());
		assertEquals(viaje1.getId(), list.get(1).getId());
	}
	
	@Test
	public void testBuscarViaje() {
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario("user16", "1234", "user16@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, "7681FYS", "Opel Corsa", 2005, 2);
		ViajeJPA viaje1 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 12);
		
		ViajeJPA viajeObtenido = ControladorBlaBlaCar.getInstancia().buscarViaje(viaje1.getId());
		assertEquals(viaje1.getId(), viajeObtenido.getId());
	}
	
	@Test
	public void testGetConductorViaje() {
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario("user17", "1234", "user17@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, "5656FYS", "Opel Corsa", 2005, 2);
		ViajeJPA viaje1 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 12);
		
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().getConductorViaje(viaje1.getId());
		viaje1 = em.find(ViajeJPA.class, viaje1.getId());
		assertEquals(viaje1.getCoche().getUsuario().getUsuario(), usuario1.getUsuario());
	}
	
	@Test
	public void testIsConductorViaje() {
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario("user19", "1234", "user17@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, "6842FYS", "Opel Corsa", 2005, 2);
		ViajeJPA viaje1 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 12);
		
		assertTrue(ControladorBlaBlaCar.getInstancia().isConductorViaje(usuario, viaje1));
	}
	
	@Test
	public void testIsViajeReservado() {
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario("user20", "1234", "user20@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, "6842FYS", "Opel Corsa", 2005, 2);
		ViajeJPA viaje1 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 12);
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario("user21", "1234", "user21@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje1, "");
		
		assertTrue(ControladorBlaBlaCar.getInstancia().isViajeReservado(viaje1.getId(), usuario1));
	}
	
	@Test
	public void testBuscarUsuarioReserva() {
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario("user21", "1234", "user21@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, "6842FYS", "Opel Corsa", 2005, 2);
		ViajeJPA viaje1 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 12);
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario("user22", "1234", "user22@um.es", "648537948");
		ReservaJPA reserva1 = ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje1, "");
		
		assertEquals(ControladorBlaBlaCar.getInstancia().buscarUsuarioReserva(reserva1.getId()).getUsuario(), usuario1.getUsuario());
	}
	
	@Test
	public void testGetUsuariosViaje() {
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario("user23", "1234", "user23@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, "6842FYS", "Opel Corsa", 2005, 2);
		ViajeJPA viaje1 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 12);
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario("user24", "1234", "user24@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje1, "");
		Usuario usuario2 = ControladorBlaBlaCar.getInstancia().registroUsuario("user25", "1234", "user25@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().reservarViaje(usuario2, viaje1, "");
		ControladorBlaBlaCar.getInstancia().aceptarViaje(viaje1.getId(), usuario1);
		ControladorBlaBlaCar.getInstancia().rechazarViaje(viaje1.getId(), usuario2);
		
		assertEquals(ControladorBlaBlaCar.getInstancia().getUsuariosViaje(viaje1.getId(), usuario.getUsuario()).size(), 2);
	}
	
	@Test
	public void testGetReservasPendientes() {
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario("user24", "1234", "user24@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, "6842FYS", "Opel Corsa", 2005, 2);
		ViajeJPA viaje1 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 12);
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario("user25", "1234", "user25@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje1, "");
		Usuario usuario2 = ControladorBlaBlaCar.getInstancia().registroUsuario("user26", "1234", "user26@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().reservarViaje(usuario2, viaje1, "");
		ControladorBlaBlaCar.getInstancia().aceptarViaje(viaje1.getId(), usuario1);
		
		assertEquals(ControladorBlaBlaCar.getInstancia().getReservasPendientes(viaje1.getId()).size(), 1);
	}
	
	@Test
	public void testHasCar() {
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario("user27", "1234", "user27@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, "6842FYS", "Opel Corsa", 2005, 2);
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario("user28", "1234", "user28@um.es", "648537948");
		
		assertTrue(ControladorBlaBlaCar.getInstancia().hasCar(usuario));
		assertFalse(ControladorBlaBlaCar.getInstancia().hasCar(usuario1));
	}
	
	@Test
	public void testListarViajesRealizados() {
		Usuario usuario = ControladorBlaBlaCar.getInstancia().registroUsuario("user29", "1234", "user29@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().anyadirCoche(usuario, "6842FYS", "Opel Corsa", 2005, 2);
		ViajeJPA viaje1 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 12);
		ViajeJPA viaje2 = ControladorBlaBlaCar.getInstancia().registrarViaje(usuario, 3, 12);
		Date fecha = new Date();
		ControladorBlaBlaCar.getInstancia().registrarParadaOrigen(viaje1.getId(), "Madrid", "c/ apolo", 70011, fecha);
		ControladorBlaBlaCar.getInstancia().registrarParadaOrigen(viaje2.getId(), "Madrid", "c/ apolo", 70011, fecha);
		Usuario usuario1 = ControladorBlaBlaCar.getInstancia().registroUsuario("user30", "1234", "user30@um.es", "648537948");
		ControladorBlaBlaCar.getInstancia().reservarViaje(usuario1, viaje1, "");
		ControladorBlaBlaCar.getInstancia().aceptarViaje(viaje1.getId(), usuario1);
		
		assertEquals(ControladorBlaBlaCar.getInstancia().listarViajesRealizados(usuario1), 1);
	}
}
