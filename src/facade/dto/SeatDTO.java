package facade.dto;

public class SeatDTO {

	
	private String fila;
	private int numero;
	private String instalacao;
	
	public SeatDTO(String instalacao,String fila,int numero) {
		this.fila = fila;
		this.numero = numero;
		this.instalacao = instalacao;
	}
	
	public int getNumero() {
		return numero;
	}
	public String getInstalacao() {
		return instalacao;
	}
	public String getFila() {
		return fila;
	}
	@Override
	public String toString() {
		return "Instalacao: "+instalacao+" | "+fila+" - "+numero;
	}
}
