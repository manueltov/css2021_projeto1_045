package business.instalacao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.exceptions.InstalacaoNotFoundException;


public class InstalacaoCatalog {

	private EntityManager em;
	
	public InstalacaoCatalog(EntityManager em) {
		this.em = em;
	}

	public Iterable<Instalacao> getInstalacoes() {
		try {
			TypedQuery<Instalacao> query = em.createNamedQuery(Instalacao.GET_ALL_INSTALACOES, Instalacao.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>(); 
		}	
	}

	public Instalacao getInstalacaoByName(String instalacao) throws InstalacaoNotFoundException {
		try {
			TypedQuery<Instalacao> query = em.createNamedQuery(Instalacao.INSTALACAO_BY_NAME, Instalacao.class);
			query.setParameter(Instalacao.INSTALACAO_NAME, instalacao);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new InstalacaoNotFoundException("Instalacao with name " + instalacao + " does not exist", e);
		}
	}

}
