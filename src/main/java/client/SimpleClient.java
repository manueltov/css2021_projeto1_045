package client;

import application.CustomerService;
import application.SaleService;
import facade.exceptions.ApplicationException;
import facade.startup.SaleSys;

/**
 * A simple application client that uses both services.
 *
 * @author fmartins
 * @version 1.2 (11/02/2015)
 */
public class SimpleClient {
	
	/**
	 * An utility class should not have public constructors
	 */
	private SimpleClient() {
	}

    /**
     * A simple interaction with the application services
     *
     * @param args Command line parameters
     */
    public static void main(String[] args) {

        SaleSys app = new SaleSys();
        try {
            app.run();
            
            // Access both available services
            CustomerService cs = app.getCustomerService();

            // this client is a bit different since we want to illustrate
            // the idea of having a stateful service that knows about the
            // current sale. This is different from the approach taken so 
            // far. We will refactor the code later after introducing stateless
            // and stateful sessions.
            SaleService ss1 = app.getSaleService();
            SaleService ss2 = app.getSaleService();

            // the interaction
            // adds a customer.
            cs.addCustomer(168027852, "Customer 1", 217500255, 2);

            // creates a new sale
            ss1.newSale(168027852);
            
            // creates another sale
            ss2.newSale(168027852);

            // adds two products to the first sale
            ss1.addProductToSale(123, 10);
            ss1.addProductToSale(124, 5);
            	
            // adds a product to the second sale
            ss2.addProductToSale(123, 3);

            // get the discount amounts
            double discount1 = ss1.getSaleDiscount();
            double discount2 = ss2.getSaleDiscount();
            System.out.println(discount1);
            System.out.println(discount2);
            
            app.stopRun();
        } catch (ApplicationException e) {
        	System.out.println("Error: " + e.getMessage());
        	// for debugging purposes only. Typically, in the application
        	// this information can be associated with a "details" button when
        	// the error message is displayed.
        	if (e.getCause() != null)
        		System.out.println("Cause: ");
        	e.printStackTrace();
        }
    }
}