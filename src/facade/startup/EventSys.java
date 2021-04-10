package facade.startup;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import business.handlers.NewEventHandler;
import business.handlers.SellIndividualTicketHandler;
import business.handlers.SellPassTicketHandler;
import business.handlers.SetInstallationHandler;
import facade.exceptions.ApplicationException;
import facade.services.EventService;
import facade.services.InstallationService;
import facade.services.PassTicketService;
import facade.services.IndividualTicketService;

public class EventSys {
	
	private EntityManagerFactory emf;
	public void run() throws ApplicationException {
		// Connects to the database
		try {
			emf = Persistence.createEntityManagerFactory("domain-model-jpa");
			// exceptions thrown by JPA are not checked
		} catch (Exception e) {
			throw new ApplicationException("Error connecting database", e);
		}
	}

	public void stopRun() {
		// Closes the database connection
		emf.close();
	}

	public EventService getEventService() {
		return new EventService(new NewEventHandler(emf));
	}
	
	public InstallationService getInstallationService() {
		return new InstallationService(new SetInstallationHandler(emf));
	}

	public IndividualTicketService getTicketService() {
		return new IndividualTicketService(new SellIndividualTicketHandler(emf));
	}

	public PassTicketService getPassTicketService() {
		return  new PassTicketService(new SellPassTicketHandler(emf));
	}

}
