package client;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import dateUtils.DateUtils;
import dbutils.DatabaseUtils;
import facade.services.EventService;
import facade.startup.EventSys;

public class Client {

	public static void main(String[] args) throws IOException, SQLException {

		EventSys app = new EventSys();
		try {
			DatabaseUtils.clearTables();
			DatabaseUtils.populateTables();
			app.run();
			EventService es = app.getEventService();
			es.tryCreateEvent().forEach(System.out::println);
			es.setTipoDeEvento("TeteATete");
			es.setNome("Bye Semestre X");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 2));
			Date inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 5, 3, 20, 0));
			Date fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 5, 3, 21, 0));
			es.addDate(d,inicio, fim);
			es.setEmpresa(2);
			es.createEvent();
			app.stopRun();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
