package facade.dto;

public class EventTypeDto {

	private String name;
	private int max_watch;
	private String seats;
	
	public EventTypeDto(String name,int max_watch,String seats) {
		this.name = name;
		this.max_watch = max_watch;
		this.seats = seats;
	}
	
	@Override
	public String toString() {
		return "Type of event:"+name+"\nMax lotation:"+max_watch+"\nType of seat:"+seats;
	}
}
