package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Reserva
 *
 */

@Entity
public class ReservaJPA implements Serializable {

	@Id
	@GeneratedValue
	private int id;
	private String comentario;
	@ManyToOne
	private ViajeJPA viaje;
	@Enumerated(EnumType.STRING)
	private EstadoReserva estado;
	@ManyToOne
	private Usuario usuario;
	
	private static final long serialVersionUID = 1L;
	
	public ReservaJPA() {
		super();
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getComentario() {
		return this.comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public EstadoReserva getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}
	
	public ViajeJPA getViaje() {
		return viaje;
	}
	
	public void setViaje(ViajeJPA viaje) {
		this.viaje = viaje;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
