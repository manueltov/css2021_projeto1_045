package business.ticket;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.eventactivity.EventActivity;



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries({
	@NamedQuery(name=Ticket.GET_OPEN_TICKET_OF_ACTIVITY, 
    query="SELECT t FROM Ticket t WHERE t.eventActivity.id = :"+Ticket.EVENT_ACTIVITY_ID+
    " and t.status = :"+Ticket.TICKET_STATUS)
	
})
public class Ticket {


	public static final String GET_OPEN_TICKET_OF_ACTIVITY = "ticket.getTicketOfActivity";
	public static final String EVENT_ACTIVITY_ID = "id";
	public static final String TICKET_STATUS = "status";
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn
	private EventActivity eventActivity;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TicketStatus status = TicketStatus.AVAILABLE;
	
	@Column(nullable = false)
	private double preco;
	Ticket (){}
	
	public Ticket(EventActivity eventActivity,double preco) {
		this.eventActivity = eventActivity;
		this.preco = preco;
	}
	
	public boolean sell() {
		if(status == TicketStatus.AVAILABLE || status == TicketStatus.RESERVED) {
			status = TicketStatus.SOLD;
			return true;
		}
		return false;
	}
	
	public boolean reserve() {
		if(status == TicketStatus.AVAILABLE) {
			status = TicketStatus.RESERVED;
			return true;
		}
		return false;
	}
	
	public TicketStatus getStatus() {
		return status;
	}
	
	public int getId() {
		return id;
	}
	
	public EventActivity getEventActivity() {
		return eventActivity;
	}
	
	public double getPreco() {
		return preco;
	}

	public boolean isAvailable() {
		return status == TicketStatus.AVAILABLE;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	
}
