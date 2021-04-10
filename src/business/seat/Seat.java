package business.seat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import business.instalacao.Instalacao;

@Entity
public class Seat implements Comparable<Seat>{

	

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Instalacao instalacao;
	
	@Column(nullable = false)
	private String row;
	
	@Column(nullable = false)
	private int number;
	
	Seat(){}
	
	public Seat(Instalacao instalacao,String fila,int numero) {
		this.instalacao = instalacao;
		this.row = fila;
		this.number = numero;
	}
	
	public String getFila() {
		return row;
	}
	
	public int getNumero() {
		return number;
	}
	
	public int getId() {
		return id;
	}
	
	public Instalacao getInstalacao() {
		return instalacao;
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
