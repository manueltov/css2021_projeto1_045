package business.instalacao;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import business.eventactivity.EventActivity;

@NamedQueries({
	@NamedQuery(name=Instalacao.GET_ALL_INSTALACOES, query="SELECT inst FROM Instalacao inst"),
	@NamedQuery(name=Instalacao.INSTALACAO_BY_NAME, query="SELECT inst FROM Instalacao inst WHERE inst.name = :" +Instalacao.INSTALACAO_NAME)
})

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "INSTALACAOTYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "Instalacao")
public class Instalacao {

	public static final String GET_ALL_INSTALACOES = "Instalacao.getAllNames";
	public static final String INSTALACAO_NAME = "name";
	public static final String INSTALACAO_BY_NAME = "Instalacao.findByName";

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "instalacao")
	private List<EventActivity> activities;

	@Column(nullable = false)
	private int maxCapacity;
	
	Instalacao(){}
	
	public Instalacao(String nome,int max_capacity) {
		this.name = nome;
		this.maxCapacity = max_capacity;		
	}
	
	
	public String getNome() {
		return name;
	}
	
	public boolean availableOn(Date date) {
		for (EventActivity activity : activities) {
			if(activity.getTimeFrame().getDate().equals(date)) {
				return false;
			}
		}
		return true;
	}

	public int getCapacity() {
		return maxCapacity;
	}

	public void addActivity(EventActivity ea) {
		activities.add(ea);
	}
	
}
