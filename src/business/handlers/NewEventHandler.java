package business.handlers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.company.Company;
import business.company.CompanyCatalog;
import business.event.EventCatalog;
import business.event.TimeFrame;
import business.eventtype.EventType;
import business.eventtype.EventTypeCatalog;
import facade.exceptions.ApplicationException;

public class NewEventHandler {
	private EntityManagerFactory emf;
	private EventType eventType;
	private Company company;
	private String name;
	private List<TimeFrame> timeFrames;

	public NewEventHandler(EntityManagerFactory emf) {
		this.emf = emf;
		timeFrames = new ArrayList<>();
	}

	public Iterable<EventType> tryCreateEvent() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventTypeCatalog eventTypeCatalog = new EventTypeCatalog(em);

		try {
			em.getTransaction().begin();
			Iterable<EventType> eventTypes = eventTypeCatalog.getEventTypes();
			em.getTransaction().commit();
			return eventTypes;
		}catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("ERROR: Not possible to fetch event types.");
		}
		finally {
			em.close();
		}
	}
	
	public void createEvent() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		try {
			em.getTransaction().begin();
			if(name == null) {
				throw new ApplicationException("No name defined");
			}
			if(company == null) {
				throw new ApplicationException("No company defined");
			}
			if(timeFrames.isEmpty()) {
				throw new ApplicationException("No dates defined");
			}
			
			eventCatalog.addNewEvent(name,eventType,timeFrames,company);
			em.getTransaction().commit();
		}catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Not possible to create new event",e);
		}finally {
			em.close();
		}
	}
	
	public void setType(String type) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventTypeCatalog eventTypeCatalog = new EventTypeCatalog(em);
		try {
			em.getTransaction().begin();
			eventType = eventTypeCatalog.getEventTypeByName(type);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Event type "+type+" not found.", e);
		} finally {
			em.close();
		}
	}
	
	public void setName(String name) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		try {
			em.getTransaction().begin();
			if(!eventCatalog.nameIsAvailable(name)) {
				throw new ApplicationException("");
			}
			em.getTransaction().commit();
			this.name = name;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Name "+name+" not available.", e);
		} finally {
			em.close();
		}
	}
	
	
	public void addDate(Date date,Date start,Date end) throws ApplicationException {
		try {
			if(start.after(end)) {
				throw new ApplicationException("Start after end");
			}
			for (TimeFrame tf : timeFrames) {
				System.out.println(date);
				System.out.println(tf.getDate());
				if(!date.after(tf.getDate())){
					throw new ApplicationException("Before already decided dates");
				}
			}
			timeFrames.add(new TimeFrame(date,start, end));
		}catch (Exception e) {
			throw new ApplicationException("Not possible to add new dates",e);
		}	
	}
	
	public void setCompany(int company) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		CompanyCatalog companyCatalog = new CompanyCatalog(em);
		try {
			em.getTransaction().begin();
			Company e = companyCatalog.getCompanyById(company);
			if(!e.haveLicense(eventType)) {
				throw new ApplicationException("Company not allowed");
			}
			this.company = e;
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Company not found or not allowed for that event.", e);
		} finally {
			em.close();
		}
	}
}
