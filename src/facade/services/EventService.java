package facade.services;


import java.util.Date;
import java.util.ArrayList;

import business.eventtype.EventType;
import business.handlers.NewEventHandler;
import facade.dto.EventTypeDto;
import facade.exceptions.ApplicationException;

public class EventService {

	private NewEventHandler newEventhandler;
	public EventService(NewEventHandler newEventhandler) {
		this.newEventhandler = newEventhandler;
	}
	
	public void createEvent() throws ApplicationException {
		newEventhandler.createEvent();
	}
	
	public Iterable<EventTypeDto> tryCreateEvent() throws ApplicationException {
		ArrayList<EventTypeDto> aux = new ArrayList<>();
		Iterable<EventType> evts = newEventhandler.tryCreateEvent();
		for (EventType et : evts) {
			aux.add(new EventTypeDto(et.getType(), et.getMaxWatch(), et.getTypeOfSeats().toString()));			
		}
		return aux;
	}
	
	public void setTypeOfEvent(String type) throws ApplicationException {
		newEventhandler.setType(type);
	}
	
	public void setName(String name) throws ApplicationException {
		newEventhandler.setName(name);
	}
	
	public void setCompany(int company) throws ApplicationException {
		newEventhandler.setCompany(company);
	}
	
	public void addDate(Date date,Date start,Date end) throws ApplicationException {
		newEventhandler.addDate(date,start, end);
	}
	
	
}
