package business.seat;

import javax.persistence.EntityManager;

public class SeatCatalog {

	private EntityManager em;
	
	public SeatCatalog(EntityManager em) {
		this.em = em;
	}

}
