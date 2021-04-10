package business.eventtype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.seat.SeatType;

@Entity
@NamedQueries({
	@NamedQuery(name=EventType.FIND_BY_NAME, query="SELECT evt FROM EventType evt WHERE evt.name = :" + 
			EventType.EVENT_TYPE_NAME),
	@NamedQuery(name=EventType.GET_ALL_EVENT_TYPES, query="SELECT evt FROM EventType evt")
})
public class EventType {

	public static final String FIND_BY_NAME = "EventType.findByName";
	public static final String EVENT_TYPE_NAME = "name";
	public static final String GET_ALL_EVENT_TYPES = "EventType.getAllNames";

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private int maxWatch;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SeatType typeOfSeats;

	EventType(){}

	public EventType(String type,int maxWatch,SeatType typeOfSeats) {
		this.name = type;
		this.maxWatch = maxWatch;
		this.typeOfSeats = typeOfSeats;
	}

	public int getMaxWatch() {
		return maxWatch;
	}

	public SeatType getTypeOfSeats() {
		return typeOfSeats;
	}

	public String getType() {
		return name;
	}

	public int getId() {
		return id;
	}

}
