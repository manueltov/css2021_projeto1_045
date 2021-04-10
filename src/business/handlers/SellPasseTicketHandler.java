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

public class SellPasseTicketHandler {
	
	private EntityManagerFactory emf;
	private Event evento;
	private SeatType seatType;
	
	public SellPasseTicketHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public long setEvento(String event) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		EventActivityCatalog eventActivityCatalog = new EventActivityCatalog(em);
		try {
			em.getTransaction().begin();
			Event e = eventCatalog.getEventByName(event);
			if(e.getPassePrice() < 0) {
				throw new ApplicationException("You can not buy passe tickets for this event.");
			}
			evento = e;
			long numberOfBilhetesPasse = 0;
			seatType = evento.getEventType().getTipoDeLugares();
			if(seatType == SeatType.EM_PE)
				numberOfBilhetesPasse = eventActivityCatalog.getNumberOfBilhetesPasseEmPe(e.getId());
			else {
				numberOfBilhetesPasse = eventActivityCatalog.getNumberOfBilhetesPasseSentado(e.getId());
			}
			em.getTransaction().commit();
			if(numberOfBilhetesPasse == 0) {
				throw new ApplicationException("No bilhetes passe available for this event.");
			}
			return numberOfBilhetesPasse;
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
			if(seatType == SeatType.EM_PE) {
				ticketCatalog.sellBilhetePasse(evento.getId(),tickets,email);
			}
			else {
				seatTicketCatalog.sellBilhetePasse(evento.getId(),tickets,email);
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
	