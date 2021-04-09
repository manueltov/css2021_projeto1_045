package business.event;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class TimeFrame implements Comparable<TimeFrame> {
	
	@Temporal(TemporalType.DATE) @Column(nullable = false)
	private Date date;
	
	@Temporal(TemporalType.DATE) @Column(nullable = false)
	private Date start;
	
	@Temporal(TemporalType.DATE) @Column(nullable = false)
	private Date end;
	
	TimeFrame(){}
	
	public TimeFrame(Date date,Date start,Date end) {
		this.date = date;
		this.start = start;
		this.end = end;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public Date getStart() {
		return start;
	}
	
	@Override
	public int compareTo(TimeFrame o) {
		return this.date.compareTo(o.date);
	}
	
}
