package facade.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.handlers.SetInstalacaoHandler;
import business.instalacao.Instalacao;
import facade.dto.InstalacaoDTO;
import facade.exceptions.ApplicationException;

public class InstalacaoService {

	private SetInstalacaoHandler setInstalacaoHandler;
	
	public InstalacaoService(SetInstalacaoHandler setInstalacaoHandler) {
		this.setInstalacaoHandler = setInstalacaoHandler;
	}
	
	public Iterable<InstalacaoDTO> getInstalacoes() throws ApplicationException{
		Iterable<Instalacao> aux = this.setInstalacaoHandler.getInstalacoes();
		List<InstalacaoDTO> r = new ArrayList<>();
		for (Instalacao instalacao : aux) {
			r.add(new InstalacaoDTO(instalacao.getNome(), instalacao.getCapacity()));
		}
		return r;
	}
	
	
	public void setEvent(String evento) throws ApplicationException {
		this.setInstalacaoHandler.setEvent(evento);
	}
	
	public void setInstalacao(String instalacao) throws ApplicationException{
		this.setInstalacaoHandler.setInstalacao(instalacao);
	}
	
	public void setSaleDate(Date date) throws ApplicationException {
		this.setInstalacaoHandler.setDate(date);
	}
	
	public void setIndividualPrice(double price) throws ApplicationException{
		this.setInstalacaoHandler.setIndividualPrice(price);
	}
	
	public void setPassePrice(double price) throws ApplicationException{
		this.setInstalacaoHandler.setPassePrice(price);
	}

	public void setInstalacaoToEvento() throws ApplicationException {
		this.setInstalacaoHandler.setInstalacaoToEvent();
	}
}
