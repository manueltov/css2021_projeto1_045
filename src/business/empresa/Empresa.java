package business.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

import business.eventtype.EventType;

@Entity
@NamedQuery(name=Empresa.FIND_BY_ID, query="SELECT e FROM Empresa e WHERE e.id = :" + 
		Empresa.EMPRESA_ID)
public class Empresa {

	public static final String FIND_BY_ID = "Empresa.findById";
	public static final String EMPRESA_ID = "id";


	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany
	@JoinTable(name = "EVENTS_ALLOWED")
	private List<EventType> tiposDeEventos;
	
	
	Empresa(){}
	
	public Empresa(List<EventType> authEvents) {
		tiposDeEventos = new ArrayList<>();
		for (EventType eventType : authEvents) {
			tiposDeEventos.add(eventType);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public List<EventType> getTiposDeEventos() {
		return tiposDeEventos;
	}

	public boolean haveLicense(EventType eventType) {
		for (EventType ev : tiposDeEventos) {
			if(ev.getTipo().contentEquals(eventType.getTipo())) {
				return true;
			}
		}
		return false;
	}
	
	
}
