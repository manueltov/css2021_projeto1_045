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

import business.eventactivity.EventActivity;



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ticket {


	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn
	private EventActivity eventActivity;
	
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
	
}
