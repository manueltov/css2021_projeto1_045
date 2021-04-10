package facade.services;

import business.handlers.SellPasseTicketHandler;
import facade.dto.FakePaymentINFO;
import facade.exceptions.ApplicationException;

public class PasseTicketService {
	
	private SellPasseTicketHandler sellPasseTicketHandler;
	
	public PasseTicketService(SellPasseTicketHandler sellPasseTicketHandler) {
		this.sellPasseTicketHandler = sellPasseTicketHandler;
	}
	
	public long setEvento(String evento) throws ApplicationException {
		return this.sellPasseTicketHandler.setEvento(evento);
	}
	
	public FakePaymentINFO buy(int nTickets,String email) throws ApplicationException {
		return this.sellPasseTicketHandler.buy(nTickets, email);
	}
	
	
	
}
