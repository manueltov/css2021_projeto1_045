package facade.startup;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import business.handlers.NewEventHandler;
import business.handlers.SetInstalacaoHandler;
import facade.exceptions.ApplicationException;
import facade.services.EventService;
import facade.services.InstalacaoService;

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
	
	public InstalacaoService getInstalacaoService() {
		return new InstalacaoService(new SetInstalacaoHandler(emf));
	}

}
