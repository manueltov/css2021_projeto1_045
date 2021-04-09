package business.handlers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.empresa.Empresa;
import business.empresa.EmpresaCatalog;
import business.event.EventCatalog;
import business.event.TimeFrame;
import business.eventtype.EventType;
import business.eventtype.EventTypeCatalog;
import facade.exceptions.ApplicationException;

public class NewEventHandler {
	private EntityManagerFactory emf;
	private EventType eventType;
	private Empresa empresa;
	private String nome;
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
			if(nome == null) {
				throw new ApplicationException("No name defined");
			}
			if(empresa == null) {
				throw new ApplicationException("No company defined");
			}
			if(timeFrames.isEmpty()) {
				throw new ApplicationException("No dates defined");
			}
			
			eventCatalog.addNewEvent(nome,eventType,timeFrames,empresa);
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
	
	public void setTipo(String tipo) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventTypeCatalog eventTypeCatalog = new EventTypeCatalog(em);
		try {
			em.getTransaction().begin();
			eventType = eventTypeCatalog.getEventTypeByName(tipo);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Event type "+tipo+" not found.", e);
		} finally {
			em.close();
		}
	}
	
	public void setNome(String nome) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		try {
			em.getTransaction().begin();
			if(!eventCatalog.nameIsAvailable(nome)) {
				throw new ApplicationException("");
			}
			em.getTransaction().commit();
			this.nome = nome;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Nome "+nome+" not available.", e);
		} finally {
			em.close();
		}
	}
	
	
	public void addDate(Date date,Date inicio,Date fim) throws ApplicationException {
		try {
			if(inicio.after(fim)) {
				throw new ApplicationException("");
			}
			for (TimeFrame tf : timeFrames) {
				if(!date.after(tf.getDate())){
					throw new ApplicationException("");
				}
			}
			timeFrames.add(new TimeFrame(date,inicio, fim));
		}catch (Exception e) {
			throw new ApplicationException("Not possible to add new dates",e);
		}	
	}
	
	public void setEmpresa(int empresa) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EmpresaCatalog empresaCatalog = new EmpresaCatalog(em);
		try {
			em.getTransaction().begin();
			Empresa e = empresaCatalog.getEmpresaById(empresa);
			if(!e.haveLicense(eventType)) {
				throw new ApplicationException("Company not allowed");
			}
			this.empresa = e;
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
