package facade.dto;

import java.time.LocalDate;
import java.util.Random;

import dateUtils.DateUtils;

public class FakePaymentINFO {

	private int referencia;
	private String email;
	private LocalDate limit;
	
	public FakePaymentINFO(String email) {
		this.email = email;
		this.referencia = new Random().nextInt(999999999) + 1000000000;
		this.limit = DateUtils.dateToLocalDate(DateUtils.present).plusDays(7);
	}
	
	@Override
	public String toString() {
		return "-------------------PAYMENT INFO-------------------\nEmail:"+email+"\nRef:"+referencia+"\nData Limite:"+limit+"\n--------------------------------------------------";
	}
	
	
}
