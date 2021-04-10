package business.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import business.empresa.Empresa;
import business.eventactivity.EventActivity;
import business.eventtype.EventType;
import business.instalacao.Instalacao;



@Entity(name = "Event")
@NamedQueries({
	@NamedQuery(name=Event.GET_ALL_NAMES, query="SELECT ev.name FROM Event ev"),
	@NamedQuery(name=Event.FIND_BY_NAME, query="SELECT ev FROM Event ev WHERE ev.name = :" + 
			Event.EVENT_NAME)
})
public class Event {


	public static final String GET_ALL_NAMES = "Event.getAllNames";

	public static final String FIND_BY_NAME = "Event.findByName";
	public static final String EVENT_NAME = "name";

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false, unique = true)
	private String name;

	@ElementCollection
	@CollectionTable(name="DATES", joinColumns=@JoinColumn(name="id",referencedColumnName = "id"))
	private List<TimeFrame> datas;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private List<EventActivity> activities;
	
	@JoinColumn(nullable = false)
	private EventType eventType;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn
	private Instalacao instalacao;


	private double individualPrice;
	private double passePrice;
	
	@Temporal(TemporalType.DATE)
	private Date saleDate;
	
	Event(){}


	public Event(String nome, EventType eventType, List<TimeFrame> timeFrames, Empresa empresa) {
		this.name = nome;
		this.eventType = eventType;
		this.datas = new ArrayList<>();
		this.datas.addAll(timeFrames);
		this.empresa = empresa;
		this.individualPrice = -1;
		this.passePrice = -1;
		this.activities = new ArrayList<>();
	}

	public Instalacao getInstalacao() {
		return this.instalacao;
	}

	public void setInstalacao(Instalacao instalacao) {
		this.instalacao = instalacao;
	}


	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public EventType getEventType() {
		return eventType;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public TimeFrame[] getDatas() {
		TimeFrame[] aux = new TimeFrame[datas.size()];
		return datas.toArray(aux);
	}


	public double getIndividualPrice() {
		return individualPrice;
	}

	public void setIndividualPrice(double individualPrice) {
		this.individualPrice = individualPrice;
	}


	public void setPassePrice(double passePrice) {
		this.passePrice = passePrice;
	}
	
	public double getPassePrice() {
		return passePrice;
	}


	public Date getFirstDate() {
		Collections.sort(datas);
		return datas.get(0).getDate();
	}
	
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	
	public Date getSaleDate() {
		return saleDate;
	}
	
	public void addNewActivities(EventActivity e) {
		this.activities.add(e);
	}
	
	public EventActivity[] getActivities() {
		EventActivity[] aux = new EventActivity[activities.size()];
		return activities.toArray(aux);
	}


	public TimeFrame[] getOpenTimeFrames() {
		List<TimeFrame> aux1 = new ArrayList<>();
		for (EventActivity eventActivity : activities) {
			if(eventActivity.stillOpen()) {
				aux1.add(eventActivity.getTimeFrame());
			}
		}		
		TimeFrame[] aux2 = new TimeFrame[aux1.size()];
		return aux1.toArray(aux2);
	}


	public EventActivity getActivityOfTimeFrame(TimeFrame timeFrameChoosed) {
		for (EventActivity eventActivity : activities) {
			if(eventActivity.getTimeFrame().compareTo(timeFrameChoosed) == 0) {
				return eventActivity;
			}
		}
		return null;
	}
	


}
