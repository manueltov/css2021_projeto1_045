package facade.startup;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import application.CustomerService;
import application.SaleService;
import business.AddCustomerHandler;
import business.ProcessSaleHandler;
import facade.exceptions.ApplicationException;

/**
 * Implements the startup use case. It creates the application, 
 * a simple client that interacts with the application, passing
 * it the application handlers. 
 * 
 * @author fmartins
 * @version 1.0 (08/03/2015)
 *
 */
public class SaleSys {

	private CustomerService customerService;
	private EntityManagerFactory emf;

	public void run() throws ApplicationException {
		// Connects to the database
		try {
			emf = Persistence.createEntityManagerFactory("domain-model-jpa");
			customerService = new CustomerService(new AddCustomerHandler(emf));
			// exceptions thrown by JPA are not checked
		} catch (Exception e) {
			throw new ApplicationException("Error connecting database", e);
		}
	}

	public void stopRun() {
		// Closes the database connection
		emf.close();
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public SaleService getSaleService() {
		// always provides a new service because the service (in fact the ProcessSaleHandler) knows 
		// about the current sale (it is stateful)
		return new SaleService(new ProcessSaleHandler(emf));
	}
}
