package facade.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.event.TimeFrame;
import business.handlers.SellIndividualTicketHandler;
import business.seat.Seat;
import facade.dto.FakePaymentINFO;
import facade.dto.SeatDTO;
import facade.exceptions.ApplicationException;

public class IndividualTicketService {

	private SellIndividualTicketHandler individualTicketHandler;
	
	public IndividualTicketService(SellIndividualTicketHandler individualTicketHandler) {
		this.individualTicketHandler = individualTicketHandler;
	}
	
	public List<TimeFrame> setEvent(String event) throws ApplicationException{
		return this.individualTicketHandler.setEvent(event);
	}
	
	public List<SeatDTO> setDate(Date d) throws ApplicationException {
		 List<Seat> aux =  this.individualTicketHandler.setDate(d);
		 List<SeatDTO> r = new ArrayList<>();
		 for (Seat s : aux) {
			r.add(new SeatDTO(s.getInstallation().getName(), s.getRow(), s.getNumber()));
		}
		return r;
	}
	
	public void addSeat(String row,int number) throws ApplicationException {
		this.individualTicketHandler.addSeatToTicket(row, number);
	}
	
	public void setEmail(String email) throws ApplicationException {
		this.individualTicketHandler.setEmail(email);
	}
	
	public FakePaymentINFO reserveTickets() throws ApplicationException {
		return this.individualTicketHandler.reserveTickets();
	}
}
