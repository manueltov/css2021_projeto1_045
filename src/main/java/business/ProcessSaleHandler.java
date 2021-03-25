package business;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import facade.exceptions.ApplicationException;

/**
 * Handles use case process sale (version with two operations: 
 * newSale followed by an arbitrary number of addProductToSale) 
 * 
 * @author fmartins
 * @author malopes
 * @version 1.2 (1/04/2019)
 *
 */
public class ProcessSaleHandler {
	
	/**
	 * Entity manager factory for accessing the persistence service 
	 */
	private EntityManagerFactory emf;
	
	/**
	 * The current sale
	 */
	private Sale currentSale;

	/**
	 * Creates a handler for the add customer use case given
	 * the application's entity manager factory
	 *  
	 * @param emf The entity manager factory of tha application
	 */
	public ProcessSaleHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Creates a new sale
	 * 
	 * @param vatNumber The customer's VAT number for the sale
	 * @throws ApplicationException In case the customer is not in the repository
	 */
	public void newSale (int vatNumber) throws ApplicationException {
		// the entity manager and catalogs for this transaction
		EntityManager em = emf.createEntityManager();
		SaleCatalog saleCatalog = new SaleCatalog(em);
		CustomerCatalog customerCatalog = new CustomerCatalog(em);
		
		// the transaction
		try {
			em.getTransaction().begin();
			Customer customer = customerCatalog.getCustomer(vatNumber);
			currentSale = saleCatalog.newSale(customer);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error adding sale", e);
		} finally {
			em.close();
		}
	}

	/**
	 * Adds a product to the current sale
	 * 
	 * @param prodCod The product code to be added to the sale 
	 * @param qty The quantity of the product sold
	 * @throws ApplicationException When the sale is closed, the product code
	 * is not part of the product's catalog, or when there is not enough stock
	 * to proceed with the sale
	 */
	public void addProductToSale (int prodCod, double qty) throws ApplicationException {
		// the entity manager and catalogs for this transaction
		EntityManager em = emf.createEntityManager();
		ProductCatalog productCatalog = new ProductCatalog(em);

		// the transaction
		try {
			em.getTransaction().begin();
			currentSale = em.merge(currentSale);
			Product product = productCatalog.getProduct(prodCod);			
			currentSale.addProductToSale(product, qty);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error adding product to sale", e);
		} finally {
			em.close();
		}

	}

	/**
	 * @return The sale's discount, according to the customer discount type
	 * @throws ApplicationException  When there is some internal problem
	 */
	public double getSaleDiscount () throws ApplicationException   {
		EntityManager em = emf.createEntityManager();
		SaleCatalog saleCatalog = new SaleCatalog(em);
		// the transaction
		try {
			em.getTransaction().begin();
			// we have to refresh the sale because the product associated with
			// each SaleProduct might have changed since it was last read
			currentSale = saleCatalog.refresh(currentSale);
			double discount = currentSale.discount();
			em.getTransaction().commit();
			return discount;
		} catch (Exception e) {
			throw new ApplicationException("Error calculating sale discount", e);
		} finally {
			em.close();
		}
	}

	/**
	 * @return The sale's total, before discount.
	 * @throws ApplicationException  When there is some internal problem
	 */
	public double getSaleTotal() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		SaleCatalog saleCatalog = new SaleCatalog(em);
		// the transaction
		try {
			em.getTransaction().begin();
			currentSale = saleCatalog.refresh(currentSale);
			double total = currentSale.total();
			em.getTransaction().commit();
			return total;
		} catch (Exception e) {
			throw new ApplicationException("Error calculating sale total", e);
		} finally {
			em.close();
		}
	}
}
