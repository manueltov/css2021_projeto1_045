package facade.dto;

public class InstalacaoDTO {

	private String nome;
	private int maxCapacity;
	
	public InstalacaoDTO(String nome,int maxCapacity) {
		this.nome = nome;
		this.maxCapacity = maxCapacity;
	}
	
	public int getMaxCapacity() {
		return maxCapacity;
	}
	
	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return "NAME:"+nome+" | Capacity:"+maxCapacity;
	}
}
