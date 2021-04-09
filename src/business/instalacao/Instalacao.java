package business.instalacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import business.eventactivity.EventActivity;
import business.eventtype.EventType;
import business.seat.Seat;
import business.seat.SeatType;
import javax.persistence.JoinColumn;

@NamedQueries({
	@NamedQuery(name=Instalacao.GET_ALL_NAMES, query="SELECT inst.name FROM Instalacao inst")
})
@Entity
public class Instalacao {

	public static final String GET_ALL_NAMES = "Instalacao.getAllNames";

	public static final String INSTALACAO_NAME = null;

	public static final String FIND_BY_NAME = null;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@JoinColumn(nullable = false)
	private EventType eventType;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="instalacao")
	private List<Seat> lugares;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	private List<EventActivity> activities;

	@Column(nullable = false)
	private int maxCapacity;
	
	Instalacao(){}
	
	public Instalacao(String nome,EventType eventType,List<Seat> seats,int max_capacity) {
		this.eventType = eventType;
		this.name = nome;
		this.maxCapacity = max_capacity;
		if(eventType.getTipoDeLugares() == SeatType.SENTADO) {
			lugares = new ArrayList<>();
			for (Seat seat : seats) {
				lugares.add(seat);
			}
		}			
	}
	
	
	public String getNome() {
		return name;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	
	public boolean availableOn(Date date) {
		for (EventActivity activity : activities) {
			if(activity.getTimeFrame().getDate().equals(date)) {
				return false;
			}
		}
		return true;
	}

	public List<Seat> getDefaultSeats() {
		return lugares;
	}

	public int getCapacity() {
		return maxCapacity;
	}
	
}
