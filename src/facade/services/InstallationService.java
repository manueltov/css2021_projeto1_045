package facade.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.handlers.SetInstallationHandler;
import business.installation.Installation;
import facade.dto.InstallationDTO;
import facade.exceptions.ApplicationException;

public class InstallationService {

	private SetInstallationHandler setInstallationHandler;
	
	public InstallationService(SetInstallationHandler setInstallationHandler) {
		this.setInstallationHandler = setInstallationHandler;
	}
	
	public Iterable<InstallationDTO> getInstallations() throws ApplicationException{
		Iterable<Installation> aux = this.setInstallationHandler.getInstallations();
		List<InstallationDTO> r = new ArrayList<>();
		for (Installation installation : aux) {
			r.add(new InstallationDTO(installation.getName(), installation.getCapacity()));
		}
		return r;
	}
	
	
	public void setEvent(String event) throws ApplicationException {
		this.setInstallationHandler.setEvent(event);
	}
	
	public void setInstallation(String installation) throws ApplicationException{
		this.setInstallationHandler.setInstallation(installation);
	}
	
	public void setSaleDate(Date date) throws ApplicationException {
		this.setInstallationHandler.setDate(date);
	}
	
	public void setIndividualPrice(double price) throws ApplicationException{
		this.setInstallationHandler.setIndividualPrice(price);
	}
	
	public void setPassPrice(double price) throws ApplicationException{
		this.setInstallationHandler.setPassPrice(price);
	}

	public void setInstallationForEvent() throws ApplicationException {
		this.setInstallationHandler.setInstallationForEvent();
	}
}
