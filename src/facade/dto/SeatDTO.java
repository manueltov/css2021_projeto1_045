package facade.dto;

public class SeatDTO {

	
	private String row;
	private int number;
	private String installation;
	
	public SeatDTO(String installation,String row,int number) {
		this.row = row;
		this.number = number;
		this.installation = installation;
	}
	
	public int getNumber() {
		return number;
	}
	public String getInstallation() {
		return installation;
	}
	public String getRow() {
		return row;
	}
	@Override
	public String toString() {
		return "Installation: "+installation+" | "+row+" - "+number;
	}
}
