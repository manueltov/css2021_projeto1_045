package business.ticket;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.eventactivity.EventActivity;
import business.exceptions.TicketNotFoundException;
import facade.exceptions.ApplicationException;

public class TicketCatalog {
	
	private EntityManager em;

	public TicketCatalog(EntityManager em) {
		this.em = em;
	}
	
	
	

	public void reserve(String email, Ticket t) throws ApplicationException {
		if(!t.reserve()) {
			throw new ApplicationException("Already reserved.");
		}
		t.setEmail(email);
		em.merge(t);
	}

	public void sellPassTicket(int eventID,int tickets, String email) throws ApplicationException{
		List<Ticket> ts = new ArrayList<>();
		List<EventActivity> activities = new ArrayList<>();
		try {
			TypedQuery<EventActivity> query = em.createNamedQuery(EventActivity.GET_ACTIVITIES_OF_EVENT, EventActivity.class);
			query.setParameter(EventActivity.EVENT_ACTIVITY_EVENT_ID, eventID);
			activities.addAll(query.getResultList());
			for (int i = 0; i < tickets; i++) {
				for (EventActivity eventActivity : activities) {
					ts.addAll(getRandomOpenTicketOfActivity(eventActivity.getId(),tickets));
				}
			}
		}catch (Exception e) {
			throw new ApplicationException("Error while selecting tickets.");
		}
		for (Ticket t : ts) {
			reserve(email, t);
		}
	}


	private List<Ticket> getRandomOpenTicketOfActivity(int id, int tickets) throws TicketNotFoundException {
		try {
			TypedQuery<Ticket> query = em.createNamedQuery(Ticket.GET_OPEN_TICKET_OF_ACTIVITY, Ticket.class);
			query.setMaxResults(tickets);
			query.setParameter(Ticket.EVENT_ACTIVITY_ID, id);
			query.setParameter(Ticket.TICKET_STATUS, TicketStatus.AVAILABLE);
			return query.getResultList();
		} catch (Exception e) {
			throw new TicketNotFoundException("No ticket open for EventActivity " + id, e);
		}
	}
	

}
