package business.ticket;

import javax.persistence.Entity;

import business.eventactivity.EventActivity;
import business.seat.Seat;

@Entity
public class SeatTicket extends Ticket{
	
	
	private Seat seat;
	
	SeatTicket() {}
	
	public SeatTicket(EventActivity eventActivity,Seat s,double preco) {
		super(eventActivity,preco);
		this.seat = s;
	}
	
	public Seat getSeat() {
		return seat;
	}

}
