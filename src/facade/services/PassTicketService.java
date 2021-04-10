package facade.services;

import business.handlers.SellPassTicketHandler;
import facade.dto.FakePaymentINFO;
import facade.exceptions.ApplicationException;

public class PassTicketService {
	
	private SellPassTicketHandler sellPassTicketHandler;
	
	public PassTicketService(SellPassTicketHandler sellPassTicketHandler) {
		this.sellPassTicketHandler = sellPassTicketHandler;
	}
	
	public long setEvent(String event) throws ApplicationException {
		return this.sellPassTicketHandler.setEvent(event);
	}
	
	public FakePaymentINFO buy(int nTickets,String email) throws ApplicationException {
		return this.sellPassTicketHandler.buy(nTickets, email);
	}
	
	
	
}
