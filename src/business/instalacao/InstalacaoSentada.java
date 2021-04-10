package business.instalacao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import business.seat.Seat;

@Entity
@DiscriminatorValue(value = "InstalacaoSentada")
public class InstalacaoSentada extends Instalacao{

	@OneToMany(cascade = CascadeType.ALL,mappedBy="instalacao")
	private List<Seat> lugares;
	
	InstalacaoSentada() {}
	
	public InstalacaoSentada(String nome,List<Seat> seats) {
		super(nome,seats.size());
		this.lugares = new ArrayList<>();
		this.lugares.addAll(seats);
	}
	
	
	public Seat[] getLugares() {
		Seat[] aux = new Seat[lugares.size()];
		return lugares.toArray(aux);
	}
	
}
