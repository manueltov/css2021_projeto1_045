package business.installation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import business.seat.Seat;

@Entity
@DiscriminatorValue(value = "SeatedInstallation")
public class SeatedInstallation extends Installation{

	@OneToMany(cascade = CascadeType.ALL,mappedBy="installation")
	private List<Seat> seats;
	
	SeatedInstallation() {}
	
	public SeatedInstallation(String name,List<Seat> seats) {
		super(name,seats.size());
		this.seats = new ArrayList<>();
		this.seats.addAll(seats);
	}
	
	
	public Seat[] getSeats() {
		Seat[] aux = new Seat[seats.size()];
		return seats.toArray(aux);
	}
	
}
