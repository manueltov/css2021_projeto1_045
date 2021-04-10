package business.eventactivity;

import java.util.Date;

import javax.persistence.EntityManager;

import business.event.Event;
import business.event.TimeFrame;
import business.instalacao.Instalacao;

public class EventActivityCatalog {


	private EntityManager em;

	public EventActivityCatalog(EntityManager em) {
		this.em = em;
	}

	public void createNewActivities(Event event, Date saleDate, Instalacao instalacao) {
		event.setInstalacao(instalacao);
		event.setSaleDate(saleDate);
		TimeFrame[] dates = event.getDatas();
		for (int i = 0; i < dates.length; i++) {
			TimeFrame tf = dates[i];
			EventActivity ea = new EventActivity(event, tf,instalacao);
			instalacao.addActivity(ea);
			em.merge(ea);
			em.merge(instalacao);
		}
		
	}
	
	
}
