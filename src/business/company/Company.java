package business.company;

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
@NamedQuery(name=Company.FIND_BY_ID, query="SELECT e FROM Company e WHERE e.id = :" + 
		Company.COMPANY_ID)
public class Company {

	public static final String FIND_BY_ID = "Company.findById";
	public static final String COMPANY_ID = "id";


	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany
	@JoinTable(name = "EVENTS_ALLOWED")
	private List<EventType> typesOfEvents;
	
	
	Company(){}
	
	public Company(List<EventType> authEvents) {
		typesOfEvents = new ArrayList<>();
		for (EventType eventType : authEvents) {
			typesOfEvents.add(eventType);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public List<EventType> getTypesOfEvents() {
		return typesOfEvents;
	}

	public boolean haveLicense(EventType eventType) {
		for (EventType ev : typesOfEvents) {
			if(ev.getType().contentEquals(eventType.getType())) {
				return true;
			}
		}
		return false;
	}
	
	
}
