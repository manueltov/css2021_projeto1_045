package business.seat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import business.installation.Installation;

@Entity
public class Seat implements Comparable<Seat>{

	

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Installation installation;
	
	@Column(nullable = false)
	private String row;
	
	@Column(nullable = false)
	private int number;
	
	Seat(){}
	
	public Seat(Installation installation,String row,int number) {
		this.installation = installation;
		this.row = row;
		this.number = number;
	}
	
	public String getRow() {
		return row;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getId() {
		return id;
	}
	
	public Installation getInstallation() {
		return installation;
	}

	@Override
	public int compareTo(Seat o) {
		int f = row.compareTo(o.row);
		if(f == 0) {
			return number-o.number;
		}
		return f;
	}
	
	@Override
	public String toString() {
		return row +" - "+number;
	}
	
}
