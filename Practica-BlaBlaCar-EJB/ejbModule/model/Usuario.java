package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

@Entity
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String usuario;
	private String clave;
	private String email;
	private String telefono;
	private boolean administrador;
	@OneToOne
	private Coche coche;
	@OneToMany(cascade={CascadeType.REMOVE}, mappedBy="usuario")
	@OrderBy("estado ASC")
	@CollectionTable(name="ReservasUsuario")
	@ElementCollection(fetch = FetchType.EAGER)
	private List<ReservaJPA> reservas;
	
	
	public Usuario() {
		super();
		reservas = new LinkedList<ReservaJPA>();
		administrador = false;
	}
	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
	
	public Coche getCoche() {
		return coche;
	}
	
	public void setCoche(Coche coche) {
		this.coche = coche;
	}
	
	public List<ReservaJPA> getReservas() {
		return reservas;
	}
	
	public void setReservas(List<ReservaJPA> reservas) {
		this.reservas = reservas;
	}
	
	public void addReserva(ReservaJPA reserva){
		reservas.add(reserva);
	}
	
	
}
