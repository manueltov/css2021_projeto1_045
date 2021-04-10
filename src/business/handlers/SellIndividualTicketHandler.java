package business.handlers;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.event.Event;
import business.event.EventCatalog;
import business.event.TimeFrame;
import business.eventactivity.EventActivity;
import business.eventactivity.EventActivityCatalog;
import business.instalacao.InstalacaoSentada;
import business.seat.Seat;
import business.seat.SeatCatalog;
import facade.exceptions.ApplicationException;

public class SellIndividualTicketHandler {
	
	private EntityManagerFactory emf;
	private Event evento;
	private TimeFrame[] tfs;
	private EventActivity eventActivity;
	
	public SellIndividualTicketHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public TimeFrame[] setEvento(String event) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		try {
			em.getTransaction().begin();
			Event e = eventCatalog.getEventByName(event);
			if(!(e.getInstalacao() instanceof InstalacaoSentada)) {
				throw new ApplicationException("You can not buy individual tickets for this event.");
			}
			tfs = e.getOpenTimeFrames();
			evento = e;
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
	
	public Iterable<Seat> setDate(Date d) throws ApplicationException {
		
		EntityManager em = emf.createEntityManager();
		EventActivityCatalog eventActivityCatalog = new EventActivityCatalog(em);
		
		try {
			em.getTransaction().begin();
			
		}
	}
	
	public void addSeatToTicket(String row,int number) throws ApplicationException {
		if(evento == null || eventActivity == null) {
			throw new ApplicationException("You can not yet perform this action");
		}
		
		EntityManager em = emf.createEntityManager();
		SeatCatalog seatCatalog = new SeatCatalog(em);
		
		try {
			em.gettr
		}
	}
	
}
