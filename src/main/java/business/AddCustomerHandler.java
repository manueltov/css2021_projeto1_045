package business;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import facade.exceptions.ApplicationException;
import facade.exceptions.VatInvalidException;

/**
 * Handles the add customer use case. This represents a very 
 * simplified use case with just one operation: addCustomer.
 * 
 * @author fmartins
 * @author alopes
 * @version 1.2 (12/03/2021)
 *
 */
public class AddCustomerHandler {
	
	/**
	 * Entity manager factory for accessing the persistence service 
	 */
	private EntityManagerFactory emf;
	
	/**
	 * Creates a handler for the add customer use case given
	 * the application's entity manager factory
	 *  
	 * @param emf The entity manager factory of tha application
	 */
	public AddCustomerHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Adds a new customer with a valid Number. It checks that there is no other 
	 * customer in the database with the same VAT.
	 * 
	 * @param vat The VAT of the customer
	 * @param denomination The customer's name
	 * @param phoneNumber The customer's phone 
	 * @param discountType The type of discount applicable to the customer
	 * @throws ApplicationException When the VAT number is invalid (we check it according 
	 * to the Portuguese legislation) or something else went wrong during the process.
	 */
	public void addCustomer (int vat, String denomination, int phoneNumber, int discountType) 
			throws ApplicationException {
		// the entity manager and catalogs for this transaction
		EntityManager em = emf.createEntityManager();
		DiscountCatalog discountCatalog = new DiscountCatalog(em);
		CustomerCatalog customerCatalog = new CustomerCatalog(em);
		
	    if(!Customer.isValidVAT(vat)) {
				throw new VatInvalidException(Integer.toString(vat));	
		}
		// the transaction
		try {
			em.getTransaction().begin();
			Discount discount = discountCatalog.getDiscount(discountType);
			customerCatalog.addCustomer(vat, denomination, phoneNumber, discount);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error adding customer with VAT" + vat, e);
		} finally {
			em.close();
		}
	}	
}
