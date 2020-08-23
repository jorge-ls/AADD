package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ViajeJPA
 *
 */
@Entity
@Table(name="ViajeCocheJPA")
@NamedQuery( name="findViajeById",
query="SELECT v	FROM ViajeJPA v WHERE v.id =:viaje")
public class ViajeJPA implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="asientos")
	private int plazas;
	private double precio;
	@CollectionTable(name="NotasViaje")
	@ElementCollection(fetch = FetchType.EAGER)
	private ArrayList<String> notas;
	@OneToMany(cascade={CascadeType.REMOVE}, mappedBy="viaje")
	@OrderBy("estado ASC")
	@CollectionTable(name="ReservasViaje")
	@ElementCollection(fetch = FetchType.EAGER)
	private List<ReservaJPA> reservas;
	@OneToOne(cascade={CascadeType.REMOVE})
	private ParadaJPA origen;
	@OneToOne(cascade={CascadeType.REMOVE})
	private ParadaJPA destino;
	@ManyToOne
	private Coche coche;
	
	private static final long serialVersionUID = 1L;

	public ViajeJPA() {
		super();
		reservas = new LinkedList<ReservaJPA>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPlazas() {
		return plazas;
	}
		
	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}
		
	public double getPrecio() {
		return precio;
	}
		
	public void setPrecio(double precio) {
		this.precio = precio;
	}
		
	public ArrayList<String> getNotas() {
		return notas;
	}
		
	public void setNotas(ArrayList<String> notas) {
		this.notas = notas;
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

	public ParadaJPA getOrigen() {
		return origen;
	}
	
	public void setOrigen(ParadaJPA origen) {
		this.origen = origen;
	}
	
	public ParadaJPA getDestino() {
		return destino;
	}
	
	public void setDestino(ParadaJPA destino) {
		this.destino = destino;
	}

	public ReservaJPA getReservaUsuario(Usuario usuario){
		for (ReservaJPA reserva : reservas) {
			if (reserva.getUsuario().getUsuario().equals(usuario.getUsuario())){
				System.out.println("ViajeJPA.getReservaUsuario()");
				return reserva;
			}
		}
		return null;
	}
   
	public Coche getCoche() {
		return coche;
	}

	public void setCoche(Coche coche) {
		this.coche = coche;
	}
	
	public Usuario getConductorViaje(){
		return coche.getUsuario();
	}
	

}
