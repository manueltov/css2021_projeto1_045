package facade.services;

import java.util.Date;
import java.util.List;

import business.event.TimeFrame;
import business.handlers.SellIndividualTicketHandler;
import business.seat.Seat;
import facade.exceptions.ApplicationException;

public class TicketService {

	private SellIndividualTicketHandler individualTicketHandler;
	
	public TicketService(SellIndividualTicketHandler individualTicketHandler) {
		this.individualTicketHandler = individualTicketHandler;
	}
	
	public List<TimeFrame> setEvento(String evento) throws ApplicationException{
		return this.individualTicketHandler.setEvento(evento);
	}
	
	public List<Seat> setDate(Date d) throws ApplicationException {
		return this.individualTicketHandler.setDate(d);
	}
	
	public void addLugar(String row,int number) throws ApplicationException {
		this.individualTicketHandler.addSeatToTicket(row, number);
	}
	
	public void setEmail(String email) throws ApplicationException {
		this.individualTicketHandler.setEmail(email);
	}
	
	public void reserveTickets() throws ApplicationException {
		this.individualTicketHandler.reserveTickets();
	}
}
