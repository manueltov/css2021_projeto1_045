package business.empresa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.exceptions.CompanyNotFoundException;


public class CompanyCatalog {

	private EntityManager em;
	
	public CompanyCatalog(EntityManager em) {
		this.em = em;
	}

	public Company getCompanyById(int id) throws CompanyNotFoundException {
		try {
			TypedQuery<Company> query = em.createNamedQuery(Company.FIND_BY_ID, Company.class);
			query.setParameter(Company.COMPANY_ID, id);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new CompanyNotFoundException("Company with id " + id + " does not exist", e);
		}
	}
	
	

}
