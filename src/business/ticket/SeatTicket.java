package business.ticket;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.eventactivity.EventActivity;
import business.seat.Seat;

@Entity
@NamedQueries({
	@NamedQuery(name=SeatTicket.GET_TICKET_OF_SEAT, 
            query="SELECT st FROM SeatTicket st WHERE st.seat.row = :"+SeatTicket.SEAT_TICKET_ROW+
            " and st.seat.number = :"+SeatTicket.SEAT_TICKET_NUMBER+ " and st.eventActivity.id = :"+SeatTicket.EVENT_ACTIVITY_ID),
	@NamedQuery(name=SeatTicket.GET_OPEN_SEATS_OF_ACTIVITY, 
    query="SELECT st.seat FROM SeatTicket st WHERE st.status = :"+SeatTicket.TICKET_STATUS+""
    		+ " and st.eventActivity.id = :"+SeatTicket.EVENT_ACTIVITY_ID+" ORDER BY st.seat.row,st.seat.number"),
	@NamedQuery(name=SeatTicket.GET_OPEN_TICKET_OF_ACTIVITY, 
    query="SELECT st FROM SeatTicket st WHERE st.eventActivity.id = :"+SeatTicket.EVENT_ACTIVITY_ID+
    " and st.status = :"+SeatTicket.TICKET_STATUS)
	
})
public class SeatTicket extends Ticket{
	
	public static final String TICKET_STATUS = "status";
	public static final String GET_TICKET_OF_SEAT = "seatTicket.getTicketOfSeat";
	public static final String SEAT_TICKET_ROW = "row";
	public static final String SEAT_TICKET_NUMBER = "number";
	public static final String EVENT_ACTIVITY_ID = "id";
	public static final String GET_OPEN_SEATS_OF_ACTIVITY = "seatTicket.getOpenSeatsOfActivity";
	public static final String GET_OPEN_TICKET_OF_ACTIVITY = "seatTicket.getTicketOfActivity";
	
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
