package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ParadaJPA
 *
 */
@Entity

public class ParadaJPA implements Serializable {

	@Id
	@GeneratedValue
	private int id;
	private String ciudad;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	@Embedded
	private Direccion direccion;
	
	private static final long serialVersionUID = 1L;

	public ParadaJPA() {
		super();
	}
   
	public int getId() {
		return this.id;
	}
		
	public void setId(int id) {
		this.id = id;
	}
		
	public String getCiudad() {
		return this.ciudad;
	}
		
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
		
	public Date getFecha() {
		return this.fecha;
	}
		
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}
		
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
}
