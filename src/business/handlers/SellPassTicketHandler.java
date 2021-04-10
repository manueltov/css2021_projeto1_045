package business.handlers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.event.Event;
import business.event.EventCatalog;
import business.eventactivity.EventActivityCatalog;
import business.seat.SeatType;
import business.ticket.SeatTicketCatalog;
import business.ticket.TicketCatalog;
import facade.dto.FakePaymentINFO;
import facade.exceptions.ApplicationException;

public class SellPassTicketHandler {
	
	private EntityManagerFactory emf;
	private Event event;
	private SeatType seatType;
	
	public SellPassTicketHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public long setEvent(String eventName) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		EventActivityCatalog eventActivityCatalog = new EventActivityCatalog(em);
		try {
			em.getTransaction().begin();
			Event e = eventCatalog.getEventByName(eventName);
			if(e.getPassPrice() < 0) {
				throw new ApplicationException("You can not buy pass tickets for this event.");
			}
			event = e;
			long numberOfTicketsPass = 0;
			seatType = event.getEventType().getTypeOfSeats();
			if(seatType == SeatType.STANDING)
				numberOfTicketsPass = eventActivityCatalog.getNumberOfPassStandingTickets(e.getId());
			else {
				numberOfTicketsPass = eventActivityCatalog.getNumberOfPassSeatedTickets(e.getId());
			}
			em.getTransaction().commit();
			if(numberOfTicketsPass == 0) {
				throw new ApplicationException("No tickets pass available for this event.");
			}
			return numberOfTicketsPass;
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Not possible to set event",e);
		}
		finally {
			em.close();
		}
	}
	
	public FakePaymentINFO buy(int tickets,String email) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		SeatTicketCatalog seatTicketCatalog = new SeatTicketCatalog(em);
		TicketCatalog ticketCatalog = new TicketCatalog(em);
		try {
			em.getTransaction().begin();
			if(seatType == SeatType.STANDING) {
				ticketCatalog.sellPassTicket(event.getId(),tickets,email);
			}
			else {
				seatTicketCatalog.sellPassTicket(event.getId(),tickets,email);
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
	