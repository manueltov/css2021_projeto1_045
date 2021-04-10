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
	private String fila;
	
	@Column(nullable = false)
	private int numero;
	
	Seat(){}
	
	public Seat(Instalacao instalacao,String fila,int numero) {
		this.instalacao = instalacao;
		this.fila = fila;
		this.numero = numero;
	}
	
	public String getFila() {
		return fila;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public int getId() {
		return id;
	}
	
	public Instalacao getInstalacao() {
		return instalacao;
	}

	@Override
	public int compareTo(Seat o) {
		int f = fila.compareTo(o.fila);
		if(f == 0) {
			return numero-o.numero;
		}
		return f;
	}
	
}
