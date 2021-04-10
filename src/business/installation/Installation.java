package business.installation;

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
	@NamedQuery(name=Installation.GET_ALL_INSTALLATIONS, query="SELECT inst FROM Installation inst"),
	@NamedQuery(name=Installation.INSTALLATION_BY_NAME, query="SELECT inst FROM Installation inst WHERE inst.name = :" +Installation.INSTALLATION_NAME)
})

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "INSTALLATIONTYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "Installation")
public class Installation {

	public static final String GET_ALL_INSTALLATIONS = "Installation.getAllNames";
	public static final String INSTALLATION_NAME = "name";
	public static final String INSTALLATION_BY_NAME = "Installation.findByName";

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "installation")
	private List<EventActivity> activities;

	@Column(nullable = false)
	private int maxCapacity;
	
	Installation(){}
	
	public Installation(String name,int max_capacity) {
		this.name = name;
		this.maxCapacity = max_capacity;		
	}
	
	
	public String getName() {
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
