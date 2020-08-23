package model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Coche
 *
 */
@Entity
public class Coche implements Serializable {

	   
	@Id
	private String matricula;
	private String modelo;
	private int confort;
	private int año;
	@OneToOne(mappedBy="coche")
	private Usuario usuario;
	@OneToMany (cascade={CascadeType.REMOVE},mappedBy="coche")
	private List<ViajeJPA> viajes;
	
	private static final long serialVersionUID = 1L;

	public Coche() {
		super();
	} 
	
	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	} 
	
	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public int getConfort() {
		return this.confort;
	}

	public void setConfort(int confort) {
		this.confort = confort;
	}  
	
	public int getAño() {
		return this.año;
	}

	public void setAño(int año) {
		this.año = año;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void addViaje(ViajeJPA viaje) {
		viajes.add(viaje);
	}

	public List<ViajeJPA> getViajes() {
		return viajes;
	}

	public void setViajes(List<ViajeJPA> viajes) {
		this.viajes = viajes;
	}
	
}
