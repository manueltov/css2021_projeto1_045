package business.ticket;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.eventactivity.EventActivity;
import business.exceptions.SeatTicketNotFoundException;
import business.seat.Seat;
import facade.exceptions.ApplicationException;

public class SeatTicketCatalog {

	private EntityManager em;

	public SeatTicketCatalog(EntityManager em) {
		this.em = em;
	}

	public List<SeatTicket> getRandomOpenTicketOfActivity(int activity_id,int tickets) throws SeatTicketNotFoundException {
		try {
			TypedQuery<SeatTicket> query = em.createNamedQuery(SeatTicket.GET_OPEN_TICKET_OF_ACTIVITY, SeatTicket.class);
			query.setMaxResults(tickets);
			query.setParameter(SeatTicket.EVENT_ACTIVITY_ID, activity_id);
			query.setParameter(SeatTicket.TICKET_STATUS, TicketStatus.AVAILABLE);
			return query.getResultList();
		} catch (Exception e) {
			throw new SeatTicketNotFoundException("No Seat ticket open for EventActivity " + activity_id, e);
		}
	}

	public SeatTicket getTicketOfRowAndNumber(int activity_id,String row, int number) throws SeatTicketNotFoundException {
		try {
			TypedQuery<SeatTicket> query = em.createNamedQuery(SeatTicket.GET_TICKET_OF_SEAT, SeatTicket.class);
			query.setParameter(SeatTicket.SEAT_TICKET_ROW, row);
			query.setParameter(SeatTicket.SEAT_TICKET_NUMBER, number);
			query.setParameter(SeatTicket.EVENT_ACTIVITY_ID, activity_id);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new SeatTicketNotFoundException("Seat ticket with row " + row + " and number "+number+" does not exist", e);
		}
	}

	public List<Seat> getOpenSeatsOfActivity(int id) throws SeatTicketNotFoundException {
		try {
			TypedQuery<Seat> query = em.createNamedQuery(SeatTicket.GET_OPEN_SEATS_OF_ACTIVITY, Seat.class);
			query.setParameter(SeatTicket.EVENT_ACTIVITY_ID, id);
			query.setParameter(SeatTicket.TICKET_STATUS,TicketStatus.AVAILABLE);
			return query.getResultList();
		} catch (Exception e) {
			throw new SeatTicketNotFoundException("Seat Tickets of event activity with id " + id + " not found", e);
		}
	}

	public void reserve(String email, SeatTicket st) throws ApplicationException {
		if(!st.reserve()) {
			throw new ApplicationException("Already reserved.");
		}
		st.setEmail(email);
		em.merge(st);
	}

	public void sellPassTicket(int eventID,int tickets, String email) throws ApplicationException{
		List<EventActivity> activities = new ArrayList<>();
		try {
			TypedQuery<EventActivity> query = em.createNamedQuery(EventActivity.GET_ACTIVITIES_OF_EVENT, EventActivity.class);
			query.setParameter(EventActivity.EVENT_ACTIVITY_EVENT_ID, eventID);
			activities.addAll(query.getResultList());
			List<SeatTicket> sts = new ArrayList<>();
			for (EventActivity eventActivity : activities) {
				List<SeatTicket> aux = getRandomOpenTicketOfActivity(eventActivity.getId(),tickets);
				sts.addAll(aux);
			}
			for (SeatTicket st : sts) {
				reserve(email, st);
			}

		}catch (Exception e) {
			throw new ApplicationException("Error while selecting tickets.");
		}

	}
}
