package business;

import java.util.Date;

import javax.persistence.EntityManager;

import facade.exceptions.ApplicationException;

/**
 * A catalog for sales
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
public class SaleCatalog {

	/**
	 * Entity manager for accessing the persistence service 
	 */
	private EntityManager em;

	/**
	 * Constructs a sale's catalog giving an entity manager
	 */
	public SaleCatalog(EntityManager em) {
		this.em = em;
	}

	/**
	 * Creates a new sale and adds it to the repository
	 * 
	 * @param customer The customer the sales belongs to
	 * @return The newly created sale
	 */
	public Sale newSale (Customer customer) throws ApplicationException {
		Sale sale = new Sale(new Date(), customer);
		em.persist(sale);
		return sale;
	}

	/**
	 * @param currentSale
	 * @return an object sale obtained with a find
	 */
	public Sale refresh(Sale sale) {
		return em.find(Sale.class, sale.getId());
	}
}
