package business.event;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.empresa.Empresa;
import business.eventtype.EventType;
import business.exceptions.EventNotFoundException;

public class EventCatalog {
	
	private EntityManager em;

	public EventCatalog(EntityManager em) {
		this.em = em;
	}

	public boolean nameIsAvailable(String name) {
		try {
			TypedQuery<String> query = em.createNamedQuery(Event.GET_ALL_NAMES, String.class);
			return !query.getResultList().contains(name);
		} catch (Exception e) {
			return false; 
		}	
	}

	public void addNewEvent(String nome, EventType eventType, List<TimeFrame> timeFrames, Empresa empresa) {
		Event e = new Event(nome,eventType,timeFrames,empresa);
		em.persist(e);		
	}

	public Event getEventByName(String event) throws EventNotFoundException {
		try {
			TypedQuery<Event> query = em.createNamedQuery(Event.FIND_BY_NAME, Event.class);
			query.setParameter(Event.EVENT_NAME, event);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new EventNotFoundException("Event with name " + event + " does not exist", e);
		}
	}
	


}
