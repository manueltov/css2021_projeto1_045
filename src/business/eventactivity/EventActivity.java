package business.eventactivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import business.event.Event;
import business.event.TimeFrame;
import business.instalacao.Instalacao;
import business.instalacao.InstalacaoSentada;
import business.seat.Seat;
import business.ticket.SeatTicket;
import business.ticket.Ticket;


@Entity(name = "eventActivity")
@NamedQueries({
	@NamedQuery(name=EventActivity.GET_TIME_FRAMES_OF_EVENT, 
    query="SELECT ea.timeFrame FROM eventActivity ea WHERE ea.event.id = :"+EventActivity.EVENT_ACTIVITY_EVENT_ID),
	@NamedQuery(name=EventActivity.GET_ACTIVITY_OF_EVENT_DATE, 
    query="SELECT ea FROM eventActivity ea WHERE ea.event.id = :"+EventActivity.EVENT_ACTIVITY_EVENT_ID+
    " and ea.timeFrame.date = :"+EventActivity.EVENT_ACTIVITY_TIME_FRAME_DATE)
})
public class EventActivity {
	
	public static final String GET_TIME_FRAMES_OF_EVENT = "eventActivity.getTimeFramesOfEvent";
	public static final String GET_ORDERED_TICKETS_OF_DATE_EVENT = "eventActivity.getOrderedTicketsOfDateEvent";
	public static final String EVENT_ACTIVITY_EVENT_ID = "event";
	public static final String EVENT_ACTIVITY_TIME_FRAME_DATE = "date";
	public static final String GET_ACTIVITY_OF_EVENT_DATE = "eventActivity.getActivityOfEventDate";
	
	
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
	
	public EventActivity(Event event,TimeFrame timeFrame,Instalacao instalacao) {
		this.event = event;
		this.timeFrame = timeFrame;
		this.instalacao = instalacao;
		tickets = new ArrayList<>();
		if(instalacao instanceof InstalacaoSentada) {
			InstalacaoSentada aux = (InstalacaoSentada) instalacao;
			Seat[] seats = aux.getLugares();
			for (int i = 0; i < seats.length; i++) {
				Seat s = seats[i];
				tickets.add(new SeatTicket(this,s,event.getIndividualPrice()));
			}
		}
		else {
			for (int i = 0; i < instalacao.getCapacity(); i++) {
				tickets.add(new Ticket(this, event.getIndividualPrice()));
			}
		}
		
	}
	
	public TimeFrame getTimeFrame() {
		return timeFrame;
	}
	
	public boolean stillOpen() {
		for (Ticket ticket : tickets) {
			if(ticket.isAvailable()) {
				return true;
			}
		}
		return false;
	}

	public Iterable<Seat> getOpenSeats() {
		if(!(instalacao instanceof InstalacaoSentada)) {
			return null;
		}
		List<Seat> seats = new ArrayList<>();
		for (Ticket ticket : tickets) {
			if(ticket.isAvailable()) {
				SeatTicket st = (SeatTicket) ticket;
				seats.add(st.getSeat());
			}
		}
		Collections.sort(seats);
		return seats;
	}
	
	
	public int getId() {
		return id;
	}
	
	public Instalacao getInstalacao() {
		return instalacao;
	}
	
	public Event getEvent() {
		return event;
	}
	
	
}
