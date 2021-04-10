package facade.dto;

public class InstallationDTO {

	private String name;
	private int maxCapacity;
	
	public InstallationDTO(String name,int maxCapacity) {
		this.name = name;
		this.maxCapacity = maxCapacity;
	}
	
	public int getMaxCapacity() {
		return maxCapacity;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "NAME:"+name+" | Capacity:"+maxCapacity;
	}
}
