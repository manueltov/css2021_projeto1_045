package business.eventactivity;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.event.Event;
import business.event.TimeFrame;
import business.exceptions.TicketNotFoundException;
import business.exceptions.TimeFrameNotFoundException;
import business.installation.Installation;
import business.ticket.Ticket;
import business.ticket.TicketStatus;

public class EventActivityCatalog {


	private EntityManager em;

	public EventActivityCatalog(EntityManager em) {
		this.em = em;
	}

	public void createNewActivities(Event event, Date saleDate, Installation installation) {
		event.setInstallation(installation);
		event.setSaleDate(saleDate);
		TimeFrame[] dates = event.getDates();
		for (int i = 0; i < dates.length; i++) {
			TimeFrame tf = dates[i];
			EventActivity ea = new EventActivity(event, tf,installation);
			installation.addActivity(ea);
			event.addNewActivities(ea);
			em.merge(ea);
			
		}
		em.merge(event);
		em.merge(installation);
		
	}

	public List<TimeFrame> getTimeFramesOfEvent(int eventId) throws TimeFrameNotFoundException {
		try {
			TypedQuery<TimeFrame> query = em.createNamedQuery(EventActivity.GET_TIME_FRAMES_OF_EVENT, TimeFrame.class);
			query.setParameter(EventActivity.EVENT_ACTIVITY_EVENT_ID, eventId);
			return query.getResultList();
		} catch (Exception e) {
			throw new TimeFrameNotFoundException("TimeFrame of event with id " + eventId + " does not exist", e);
		}
	}

	public List<Ticket> getTicketsOfActivity(int eventId, Date d) throws TicketNotFoundException {
		try {
			TypedQuery<Ticket> query = em.createNamedQuery(EventActivity.GET_ORDERED_TICKETS_OF_DATE_EVENT, Ticket.class);
			query.setParameter(EventActivity.EVENT_ACTIVITY_EVENT_ID, eventId);
			query.setParameter(EventActivity.EVENT_ACTIVITY_TIME_FRAME_DATE, d);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TicketNotFoundException("Ticket of event id " + eventId + " does not exist", e);
		}
	}

	public EventActivity getEventActivityOfEventDate(int id, Date d) throws TicketNotFoundException {
		try {
			TypedQuery<EventActivity> query = em.createNamedQuery(EventActivity.GET_ACTIVITY_OF_EVENT_DATE, EventActivity.class);
			query.setParameter(EventActivity.EVENT_ACTIVITY_EVENT_ID, id);
			query.setParameter(EventActivity.EVENT_ACTIVITY_TIME_FRAME_DATE, d);
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TicketNotFoundException("Ticket of event id " + id + " does not exist", e);
		}
	}

	public long getNumberOfPassStandingTickets(int id) throws TicketNotFoundException {
		try {
			TypedQuery<Long> query = em.createNamedQuery(EventActivity.GET_NUMBER_OF_TICKETS_AVAILABLE, Long.class);
			query.setParameter(EventActivity.EVENT_ACTIVITY_EVENT_ID, id);
			query.setParameter(EventActivity.TICKETS_STATUS, TicketStatus.AVAILABLE);
			return query.getSingleResult();		
		} catch (Exception e) {
			e.printStackTrace();
			throw new TicketNotFoundException("Ticket of event with id " + id + " does not exist", e);
		}
	}

	public long getNumberOfPassSeatedTickets(int id) throws TicketNotFoundException {
		try {
			TypedQuery<Long> query = em.createNamedQuery(EventActivity.GET_NUMBER_OF_TICKETS_AVAILABLE_SEAT, Long.class);
			query.setParameter(EventActivity.EVENT_ACTIVITY_EVENT_ID, id);
			query.setParameter(EventActivity.TICKETS_STATUS, TicketStatus.AVAILABLE);
			return query.getSingleResult();		
		} catch (Exception e) {
			e.printStackTrace();
			throw new TicketNotFoundException("Ticket of event with id " + id + " does not exist", e);
		}
	}
	
	
}
