package business.handlers;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.event.Event;
import business.event.EventCatalog;
import business.event.TimeFrame;
import business.eventactivity.EventActivityCatalog;
import business.installation.InstallationCatalog;
import business.installation.SeatedInstallation;
import business.installation.Installation;
import business.seat.SeatType;
import dateUtils.DateUtils;
import facade.exceptions.ApplicationException;

public class SetInstallationHandler {
	private EntityManagerFactory emf;
	private Event event;
	private Installation installation;
	private Date saleDate;

	public SetInstallationHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public Iterable<Installation> getInstallations() throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		InstallationCatalog installationCatalog = new InstallationCatalog(em);
		try {
			em.getTransaction().begin();
			Iterable<Installation> aux = installationCatalog.getInstallations(); 
			em.getTransaction().commit();
			return aux;
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("ERROR: Not possible to fetch installations.",e);
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

	public void setInstallation(String installation) throws ApplicationException{
		EntityManager em = emf.createEntityManager();
		InstallationCatalog installationCatalog = new InstallationCatalog(em);
		try {
			em.getTransaction().begin();
			Installation i = installationCatalog.getInstallationByName(installation);
			if((i instanceof SeatedInstallation) && this.event.getEventType().getTypeOfSeats() != SeatType.SEATED) {
				throw new ApplicationException("Invalid installation for this type of event");
			}
			if(!(i instanceof SeatedInstallation) && this.event.getEventType().getTypeOfSeats() == SeatType.SEATED)
				throw new ApplicationException("Invalid installation for this type of event");
			if(i.getCapacity() > this.event.getEventType().getMaxWatch()) {
				throw new ApplicationException("Invalid installation for this event because of capacity");
			}
			TimeFrame[] datas = event.getDates();
			for (int j = 0; j < datas.length; j++) {
				if(!i.availableOn(datas[j].getDate())) {
					throw new ApplicationException("Instalacao not free at all dates for this event");
				}
			}
			this.installation = i;
			em.getTransaction().commit();
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("ERROR: Not possible to set installation.",e);
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

	public void setPassPrice(double price) throws ApplicationException{
		if(price <= 0) {
			throw new ApplicationException("Price is not valid");
		}
		EntityManager em = emf.createEntityManager();
		this.event.setPassPrice(price);
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

	public void setInstallationForEvent() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventActivityCatalog eventActivityCatalog = new EventActivityCatalog(em);
		if(installation == null) {
			throw new ApplicationException("Not possible to set place to event");
		}
		try {
			em.getTransaction().begin();
			eventActivityCatalog.createNewActivities(event,saleDate,installation);
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
