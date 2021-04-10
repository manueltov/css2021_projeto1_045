package business.installation;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.exceptions.InstalacaoNotFoundException;


public class InstallationCatalog {

	private EntityManager em;
	
	public InstallationCatalog(EntityManager em) {
		this.em = em;
	}

	public Iterable<Installation> getInstallations() {
		try {
			TypedQuery<Installation> query = em.createNamedQuery(Installation.GET_ALL_INSTALLATIONS, Installation.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>(); 
		}	
	}

	public Installation getInstallationByName(String installation) throws InstalacaoNotFoundException {
		try {
			TypedQuery<Installation> query = em.createNamedQuery(Installation.INSTALLATION_BY_NAME, Installation.class);
			query.setParameter(Installation.INSTALLATION_NAME, installation);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new InstalacaoNotFoundException("Installation with name " + installation + " does not exist", e);
		}
	}

}
