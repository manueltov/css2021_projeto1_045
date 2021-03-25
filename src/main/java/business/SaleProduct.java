package business;

import static javax.persistence.CascadeType.MERGE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * A sale product.
 *	
 * @author fmartins
 * @author malopes
 * @version 1.2 (1/04/2019)
 *
 */
@Entity 
public class SaleProduct {
	
	// Sale product attributes 

	/**
	 * Sale product primary key. Needed by JPA. Notice that it is not part of the
	 * original domain model.
	 */
	@Id @GeneratedValue private int id;
	
	/**
	 * The product of the sale
	 */
	@ManyToOne(cascade = MERGE)
	private Product product;

	/**
	 * The number of items purchased
	 */
	private double qty;
	
	
	// 1. constructors
	
	/**
	 * Constructor needed by JPA.
	 */
	SaleProduct() { 
	}
	
	/**
	 * Creates a product that is part of a sale. The qty is the quantity of items in the sale. 
	 * 
	 * @param product The product to be associated with the sale.
	 * @param qty The number of products sold.
	 */
	public SaleProduct (Product prod, double qty) {
		this.product = prod;
		this.qty = qty;
	}

	
	// 2. getters and setters

	/**
	 * @return The product of the product sale
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @return The quantity of the product sale
	 */
	public double getQty() {
		return qty;
	}

	/**
	 * @return The sub total of the product sale
	 */
	public double getSubTotal() {
		return qty * product.getFaceValue();
	}

	/**
	 * @return The eligible sub total of the product sale
	 */
	public double getEligibleSubtotal() {
		return product.isEligibleForDiscount() ? getSubTotal() : 0;
	}
}
