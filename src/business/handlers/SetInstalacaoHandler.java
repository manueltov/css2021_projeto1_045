package business.handlers;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.event.Event;
import business.event.EventCatalog;
import business.event.TimeFrame;
import business.eventactivity.EventActivityCatalog;
import business.instalacao.Instalacao;
import business.instalacao.InstalacaoCatalog;
import business.instalacao.InstalacaoSentada;
import business.seat.SeatType;
import dateUtils.DateUtils;
import facade.exceptions.ApplicationException;

public class SetInstalacaoHandler {
	private EntityManagerFactory emf;
	private Event event;
	private Instalacao instalacao;
	private Date saleDate;

	public SetInstalacaoHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Iterable<Instalacao> getInstalacoes() throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		InstalacaoCatalog instalacaoCatalog = new InstalacaoCatalog(em);
		try {
			em.getTransaction().begin();
			Iterable<Instalacao> aux = instalacaoCatalog.getInstalacoes(); 
			em.getTransaction().commit();
			return aux;
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("ERROR: Not possible to fetch instalacoes.",e);
		}
		finally {
			em.close();
		}
	}

	public void setEvent(String event) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		try {
			em.getTransaction().begin();
			this.event = eventCatalog.getEventByName(event);
			em.getTransaction().commit();
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("ERROR: Not possible to set event.",e);
		}
		finally {
			em.close();
		}
	}

	public void setInstalacao(String instalacao) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		InstalacaoCatalog instalacaoCatalog = new InstalacaoCatalog(em);
		try {
			em.getTransaction().begin();
			Instalacao i = instalacaoCatalog.getInstalacaoByName(instalacao);
			if((i instanceof InstalacaoSentada) && this.event.getEventType().getTipoDeLugares() != SeatType.SENTADO) {
				throw new ApplicationException("Invalid instalacao for this type of event");
			}
			if(!(i instanceof InstalacaoSentada) && this.event.getEventType().getTipoDeLugares() == SeatType.SENTADO)
				throw new ApplicationException("Invalid instalacao for this type of event");
			if(i.getCapacity() > this.event.getEventType().getMaxWatch()) {
				throw new ApplicationException("Invalid instalacao for this event because of capacity");
			}
			TimeFrame[] datas = event.getDatas();
			for (int j = 0; j < datas.length; j++) {
				if(!i.availableOn(datas[j].getDate())) {
					throw new ApplicationException("Instalacao not free at all dates for this event");
				}
			}
			this.instalacao = i;
			em.getTransaction().commit();
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("ERROR: Not possible to set instalacao.",e);
		}
		finally {
			em.close();
		}
	}

	public void setIndividualPrice(double price) throws ApplicationException{
		if(price <= 0) {
			throw new ApplicationException("Price is not valid");
		}
		EntityManager em = emf.createEntityManager();
		this.event.setIndividualPrice(price);
		em.merge(this.event);
	}

	public void setPassePrice(double price) throws ApplicationException{
		if(price <= 0) {
			throw new ApplicationException("Price is not valid");
		}
		EntityManager em = emf.createEntityManager();
		this.event.setPassePrice(price);
		em.merge(this.event);
	}

	public void setDate(Date date) throws ApplicationException {
		if(DateUtils.isPast(date)) {
			throw new ApplicationException("Date can not be in the past.");
		}
		if(date.after(this.event.getFirstDate())) {
			throw new ApplicationException("Date can not be after the first event date.");
		}
		this.saleDate = date;
	}

	public void setInstalacaoToEvent() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventActivityCatalog eventActivityCatalog = new EventActivityCatalog(em);
		if(instalacao == null) {
			throw new ApplicationException("Not possible to set place to event");
		}
		try {
			em.getTransaction().begin();
			eventActivityCatalog.createNewActivities(event,saleDate,instalacao);
			em.getTransaction().commit();
		}catch (Exception e) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Not possible to set place to event",e);
		}
		finally {
			em.close();
		}
	
	}


}
