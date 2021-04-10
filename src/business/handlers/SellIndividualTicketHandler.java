package business.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.event.Event;
import business.event.EventCatalog;
import business.event.TimeFrame;
import business.eventactivity.EventActivity;
import business.eventactivity.EventActivityCatalog;
import business.installation.SeatedInstallation;
import business.seat.Seat;
import business.ticket.SeatTicket;
import business.ticket.SeatTicketCatalog;
import facade.dto.FakePaymentINFO;
import facade.exceptions.ApplicationException;

public class SellIndividualTicketHandler {
	
	private EntityManagerFactory emf;
	private Event event;
	private List<TimeFrame> tfs;
	private EventActivity eventActivity;
	private List<SeatTicket> tickets;
	private String email;
	
	public SellIndividualTicketHandler(EntityManagerFactory emf) {
		this.emf = emf;
		tickets = new ArrayList<>();
	}
	
	public List<TimeFrame> setEvent(String eventName) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		EventActivityCatalog eventActivityCatalog = new EventActivityCatalog(em);
		try {
			em.getTransaction().begin();
			Event e = eventCatalog.getEventByName(eventName);
			if(!(e.getInstallation() instanceof SeatedInstallation)) {
				throw new ApplicationException("You can not buy individual tickets for this event.");
			}
			tfs = eventActivityCatalog.getTimeFramesOfEvent(e.getId());
			event = e;
			em.getTransaction().commit();
			return tfs;
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Not possible to set event",e);
		}
		finally {
			em.close();
		}
	}
	
	public List<Seat> setDate(Date d) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventActivityCatalog eventActivityCatalog = new EventActivityCatalog(em);
		SeatTicketCatalog seatTicketCatalog	= new SeatTicketCatalog(em);
		try {
			em.getTransaction().begin();
			this.eventActivity = eventActivityCatalog.getEventActivityOfEventDate(event.getId(),d);
			List<Seat> seats = seatTicketCatalog.getOpenSeatsOfActivity(eventActivity.getId()); 
			em.getTransaction().commit();
			return seats;
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Not possible to set date.",e);
		}
		finally {
			em.close();
		}
	}
	
	public void addSeatToTicket(String row,int number) throws ApplicationException {
		if(event == null || eventActivity == null) {
			throw new ApplicationException("You can not yet perform this action");
		}
		
		EntityManager em = emf.createEntityManager();
		SeatTicketCatalog seatTicketCatalog = new SeatTicketCatalog(em);
		try {
			em.getTransaction().begin();
			SeatTicket st = seatTicketCatalog.getTicketOfRowAndNumber(eventActivity.getId(),row,number);
			if(!st.isAvailable()) {
				throw new ApplicationException("You can not buy this seat.");
			}
			tickets.add(st);
			em.getTransaction().commit();
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Not possible to add ticket.",e);
		}
		finally {
			em.close();
		}
	}
	
	public void setEmail(String email) throws ApplicationException {
		if(tickets.isEmpty() || eventActivity == null) {
			throw new ApplicationException("You can not yet perfom this action");
		}
		this.email = email;
	}
	
	public FakePaymentINFO reserveTickets() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		SeatTicketCatalog seatTicketCatalog = new SeatTicketCatalog(em);
		
		try {
			em.getTransaction().begin();
			for (SeatTicket st : tickets) {
				seatTicketCatalog.reserve(email,st);
			}
			em.getTransaction().commit();
			return new FakePaymentINFO(email);
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Not possible to finalize reservation",e);
		}
		finally {
			em.close();
		}
	}
}
