package business.eventactivity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import business.event.Event;
import business.event.TimeFrame;
import business.instalacao.Instalacao;
import business.seat.Seat;
import business.ticket.SeatTicket;
import business.ticket.Ticket;


@Entity(name = "eventActivity")
public class EventActivity {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "eventActivity")
	private List<Ticket> tickets;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Event event;

	@Embedded
	private TimeFrame timeFrame;
	
	@JoinColumn
	private Instalacao instalacao;
	
	EventActivity() {}
	
	public EventActivity(Event event,TimeFrame timeFrame) {
		this.event = event;
		this.timeFrame = timeFrame;
		tickets = new ArrayList<>();
		instalacao = event.getInstalacao();
		List<Seat> defaul_seats = event.getInstalacao().getDefaultSeats();
		if(defaul_seats == null) {
			for (int i = 0; i < event.getEventType().getMaxWatch(); i++) {
				tickets.add(new Ticket(this,event.getIndividualPrice()));
			}
		}
		else {
			for (Seat s : defaul_seats) {
				tickets.add(new SeatTicket(this,s, event.getIndividualPrice()));
			}
		}
	}
	
	public TimeFrame getTimeFrame() {
		return timeFrame;
	}
	
	
}
