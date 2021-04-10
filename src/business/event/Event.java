package business.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import business.company.Company;
import business.eventactivity.EventActivity;
import business.eventtype.EventType;
import business.installation.Installation;



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
	private List<TimeFrame> dates;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private List<EventActivity> activities;
	
	@JoinColumn(nullable = false)
	private EventType eventType;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Company company;

	@ManyToOne
	@JoinColumn
	private Installation installation;


	private double individualPrice;
	private double passPrice;
	
	@Temporal(TemporalType.DATE)
	private Date saleDate;
	
	Event(){}


	public Event(String name, EventType eventType, List<TimeFrame> timeFrames, Company company) {
		this.name = name;
		this.eventType = eventType;
		this.dates = new ArrayList<>();
		this.dates.addAll(timeFrames);
		this.company = company;
		this.individualPrice = -1;
		this.passPrice = -1;
		this.activities = new ArrayList<>();
	}

	public Installation getInstallation() {
		return this.installation;
	}

	public void setInstallation(Installation installation) {
		this.installation = installation;
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

	public Company getCompany() {
		return company;
	}

	public TimeFrame[] getDates() {
		TimeFrame[] aux = new TimeFrame[dates.size()];
		return dates.toArray(aux);
	}


	public double getIndividualPrice() {
		return individualPrice;
	}

	public void setIndividualPrice(double individualPrice) {
		this.individualPrice = individualPrice;
	}


	public void setPassPrice(double passPrice) {
		this.passPrice = passPrice;
	}
	
	public double getPassPrice() {
		return passPrice;
	}


	public Date getFirstDate() {
		Collections.sort(dates);
		return dates.get(0).getDate();
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
