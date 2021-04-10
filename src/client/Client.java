package client;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import business.event.TimeFrame;
import dateUtils.DateUtils;
import dbutils.DatabaseUtils;
import facade.dto.SeatDTO;
import facade.services.EventService;
import facade.services.InstalacaoService;
import facade.services.PasseTicketService;
import facade.services.IndividualTicketService;
import facade.startup.EventSys;

public class Client extends Thread{

	public static void main(String[] args) throws IOException, SQLException {

		EventSys app = new EventSys();
		try {
			DatabaseUtils.clearTables();
			DatabaseUtils.populateTables();
			app.run();
			int results = 0;
			System.out.println("---------TESTING UC1-----------");
			results += test1(app);
			results += test2(app);
			results += test3(app);
			results += test4(app);
			results += test5(app);
			results += test6(app);
			System.out.println("----------END TESTING----------");
			System.out.println("UC1\nRESULTS: "+results+"/6 "+((double) results/6.0)*100+"%");
			sleep(1000);
			results = 0;
			System.out.println("---------TESTING UC2-----------");
			results += test7(app);
			results += test8(app);
			results += test9(app);
			results += test10(app);
			results += test11(app);
			System.out.println("----------END TESTING----------");
			System.out.println("UC2\nRESULTS: "+results+"/5 "+((double) results/5.0)*100+"%");
			sleep(1000);
			results = 0;
			System.out.println("---------TESTING UC3-----------");
			results += test12(app);
			results += test13(app);
			results += test14(app);
			results += test15(app);
			results += test16(app);
			System.out.println("----------END TESTING----------");
			System.out.println("UC3\nRESULTS: "+results+"/5 "+((double) results/5.0)*100+"%");
			sleep(1000);
			results = 0;
			System.out.println("---------TESTING UC4-----------");
			results += test17(app);
			results += test18(app);
			results += test19(app);
			results += test20(app);
			System.out.println("----------END TESTING----------");
			System.out.println("UC4\nRESULTS: "+results+"/4 "+((double) results/4.0)*100+"%");
			app.stopRun();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int test20(EventSys app) {
		try {
			PasseTicketService pts = app.getPasseTicketService();
			long n = pts.setEvento("Festival Estou de Ferias");
			System.out.println("TICKET AVAILABLE:"+n);
			System.out.println(pts.buy(4, "u7@gmail.com"));
			System.out.println("Test 20: Failed");
			return 0;
		}catch (Exception e) {
			System.out.println("Test 20: Passed");
		}
		return 1;
	}

	private static int test19(EventSys app) {
		try {
			PasseTicketService pts = app.getPasseTicketService();
			long n = pts.setEvento("Festival Estou de Ferias");
			System.out.println("TICKET AVAILABLE:"+n);
			System.out.println(pts.buy(7, "u6@gmail.com"));
			System.out.println("Test 19: Failed");
			return 0;
		}catch (Exception e) {
			System.out.println("Test 19: Passed");
		}
		return 1;
	}

	private static int test18(EventSys app) {
		try {
			PasseTicketService pts = app.getPasseTicketService();
			long n = pts.setEvento("Open dos exames");
			System.out.println("TICKET AVAILABLE:"+n);
			System.out.println(pts.buy(3, "u5@gmail.com"));
			System.out.println("Test 18: Passed");
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test 18: Failed");
		}
		return 0;
	}

	private static int test17(EventSys app) {
		try {
			PasseTicketService pts = app.getPasseTicketService();
			long n = pts.setEvento("Open dos exames");
			System.out.println("TICKETS AVAILABLE:"+n);
			System.out.println(pts.buy(2, "u4@gmail.com"));
			System.out.println("Test 17: Passed");
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test 17: Failed");
		}
		return 0;
	}

	private static int test16(EventSys app) {
		try {
			System.out.println("------------------------");
			IndividualTicketService ts = app.getTicketService();
			List<TimeFrame> tfs = ts.setEvento("Open dos exames");
			Date d = tfs.get(0).getDate();
			List<SeatDTO> seats = ts.setDate(d);
			SeatDTO s1 = seats.get(0);
			SeatDTO s2 = seats.get(1);
			if(!s1.getFila().contentEquals("A") || s1.getNumero() != 1 || !s2.getFila().contentEquals("A") || s2.getNumero() != 2)
				throw new Exception("Wrong seats");
			ts.addLugar(s1.getFila(), s1.getNumero());
			ts.addLugar(s2.getFila(), s2.getNumero());
			ts.setEmail("u3@gmail.com");
			System.out.println(ts.reserveTickets());
			System.out.println("Test 16: Passed");
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test 16: Failed");
		}
		return 0;
	}

	private static int test15(EventSys app) {
		try {
			System.out.println("------------------------");
			IndividualTicketService ts = app.getTicketService();
			ts.setEvento("Festival Estou de Ferias").forEach(System.out::println);
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 9));
			ts.setDate(d);
			ts.addLugar("B", 1);
			ts.setEmail("u2@gmail.com");
			System.out.println(ts.reserveTickets());
			System.out.println("Test 15: Failed");
			return 0;
		}catch (Exception e) {
			System.out.println("Test 15: Passed");
		}
		return 1;
	}

	private static int test14(EventSys app) {
		try {
			System.out.println("------------------------");
			IndividualTicketService ts = app.getTicketService();
			ts.setEvento("Bye Semestre X").forEach(System.out::println);;
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 9));
			ts.setDate(d);
			ts.addLugar("B", 2);
			ts.setEmail("u2@gmail.com");
			System.out.println(ts.reserveTickets());
			System.out.println("Test 14: Passed");
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test 14: Failed");
		}
		return 0;
	}

	private static int test13(EventSys app) {
		try {
			System.out.println("------------------------");
			IndividualTicketService ts = app.getTicketService();
			ts.setEvento("Bye Semestre X").forEach(System.out::println);;
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 9));
			ts.setDate(d);
			ts.addLugar("B", 1);
			ts.setEmail("u2@gmail.com");
			System.out.println(ts.reserveTickets());
			System.out.println("Test 13: Failed");
			return 0;
		}catch (Exception e) {
			System.out.println("Test 13: Passed");
		}
		return 1;
	}

	private static int test12(EventSys app) {
		try {
			System.out.println("------------------------");
			IndividualTicketService ts = app.getTicketService();
			ts.setEvento("Bye Semestre X").forEach(System.out::println);;
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 9));
			ts.setDate(d);
			ts.addLugar("A", 1);
			ts.addLugar("A", 2);
			ts.addLugar("B", 1);
			ts.setEmail("u1@gmail.com");
			System.out.println(ts.reserveTickets());
			System.out.println("Test 12: Passed");
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test 12: Failed");
		}
		return 0;
	}

	private static int test11(EventSys app) {
		try {
			System.out.println("------------------------");
			InstalacaoService is = app.getInstalacaoService();
			is.getInstalacoes().forEach(System.out::println);
			is.setEvent("Festival Estou de Ferias");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 1));
			is.setSaleDate(d);
			is.setInstalacao("Pequeno Relvado");
			is.setIndividualPrice(15);
			is.setInstalacaoToEvento();
			System.out.println("Test 11: Passed");
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test 11: Failed");
		}
		return 0;
	}

	private static int test10(EventSys app) {
		try {
			System.out.println("------------------------");
			InstalacaoService is = app.getInstalacaoService();
			is.getInstalacoes().forEach(System.out::println);
			is.setEvent("Open dos Exames");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 1));
			is.setSaleDate(d);
			is.setInstalacao("Mini Estadio");
			is.setIndividualPrice(20);
			is.setPassePrice(30);
			is.setInstalacaoToEvento();
			System.out.println("Test 10: Passed");
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test 10: Failed");
		}
		return 0;
	}

	private static int test9(EventSys app) {
		try {
			System.out.println("------------------------");
			InstalacaoService is = app.getInstalacaoService();
			is.getInstalacoes().forEach(System.out::println);
			is.setEvent("Bye Semestre Y");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 1));
			is.setSaleDate(d);
			is.setInstalacao("Micro Pavilhao");
			is.setIndividualPrice(20);
			is.setInstalacaoToEvento();
			System.out.println("Test 9: Failed");
			return 0;
		}catch (Exception e) {
			System.out.println("Test 9: Passed");
		}
		return 1;
	}

	private static int test8(EventSys app) {
		try {
			System.out.println("------------------------");
			InstalacaoService is = app.getInstalacaoService();
			is.getInstalacoes().forEach(System.out::println);
			is.setEvent("Bye Semestre X");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 1));
			is.setSaleDate(d);
			is.setInstalacao("Micro Pavilhao");
			is.setIndividualPrice(20);
			is.setInstalacaoToEvento();
			System.out.println("Test 8: Passed");
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test 8: Failed");
		}
		return 0;
	}

	private static int test7(EventSys app) {
		try {
			System.out.println("------------------------");
			InstalacaoService is = app.getInstalacaoService();
			is.getInstalacoes().forEach(System.out::println);
			is.setEvent("Bye Semestre X");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 1));
			is.setSaleDate(d);
			is.setInstalacao("Mini Estadio");
			System.out.println("Test 7: Failed");
			return 0;
		}catch (Exception e) {
			System.out.println("Test 7: Passed");
		}
		return 1;
	}

	@Override
	public void run() {
		//ignore warning
	}

	private static int test2(EventSys app) {
		try {
			System.out.println("------------------------");
			EventService es = app.getEventService();
			es.tryCreateEvent().forEach(System.out::println);
			es.setTipoDeEvento("TeteATete");
			es.setNome("Bye Semestre Y");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 9));
			Date inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 5, 9, 20, 0));
			Date fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 5, 9,22, 0));
			es.addDate(d,inicio, fim);
			es.setEmpresa(1);
			es.createEvent();
			System.out.println("Test 2: Passed");
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test 2: Failed");
		}
		return 0;
	}

	private static int test1(EventSys app) {
		try {
			System.out.println("------------------------");
			EventService es = app.getEventService();
			es.tryCreateEvent().forEach(System.out::println);
			es.setTipoDeEvento("TeteATete");
			es.setNome("Bye Semestre X");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 5, 9));
			Date inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 5, 9, 21, 0));
			Date fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 6, 9, 0, 0));
			es.addDate(d,inicio, fim);
			es.setEmpresa(1);
			es.createEvent();
			System.out.println("Test 1: Passed");
			return 1;
		}catch (Exception e) {
			System.out.println("Test 1: Failed");
		}
		return 0;
	}

	private static int test3(EventSys app) {
		try {
			System.out.println("------------------------");
			EventService es = app.getEventService();
			es.tryCreateEvent().forEach(System.out::println);
			es.setTipoDeEvento("BandoSentado");
			es.setNome("Open dos Exames");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 7, 17));
			Date inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 7, 17, 21, 0));
			Date fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 7, 17, 23, 30));
			es.addDate(d,inicio, fim);
			d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 7, 18));
			inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 7, 18, 15, 0));
			fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 7, 18, 20, 0));
			es.addDate(d,inicio, fim);
			es.setEmpresa(1);
			es.createEvent();
			System.out.println("Test 3: Passed");
			return 1;
		}catch (Exception e) {
			System.out.println("Test 3: Failed");
		}
		return 0;
	}

	private static int test4(EventSys app) {
		try {
			System.out.println("------------------------");
			EventService es = app.getEventService();
			es.tryCreateEvent().forEach(System.out::println);
			es.setTipoDeEvento("MultidaoEmPe");
			es.setNome("Festival Estou de Ferias");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 7, 31));
			Date inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 7, 31, 21, 0));
			Date fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 7, 31, 23, 0));
			es.addDate(d,inicio, fim);
			d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 8, 1));
			inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 8, 1, 14, 0));
			fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 8, 1, 19, 0));
			es.addDate(d,inicio, fim);
			es.setEmpresa(1);
			es.createEvent();
			System.out.println("Test 4: Failed");
			return 0;
		}catch (Exception e) {
			System.out.println("Test 4: Passed");
		}
		return 1;
	}

	private static int test5(EventSys app) {
		try {
			System.out.println("------------------------");
			EventService es = app.getEventService();
			es.tryCreateEvent().forEach(System.out::println);
			es.setTipoDeEvento("MultidaoEmPe");
			es.setNome("Festival Estou de Ferias");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 12, 31));
			Date inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 12, 31, 21, 0));
			Date fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 12, 31, 23, 0));
			es.addDate(d,inicio, fim);
			d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 8, 1));
			inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 8, 1, 14, 0));
			fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 8, 1, 19, 0));
			es.addDate(d,inicio, fim);
			es.setEmpresa(2);
			es.createEvent();
			System.out.println("Test 5: Failed");
			return 0;
		}catch (Exception e) {
			System.out.println("Test 5: Passed");
		}
		return 1;
	}

	private static int test6(EventSys app) {
		try {
			System.out.println("------------------------");
			EventService es = app.getEventService();
			es.tryCreateEvent().forEach(System.out::println);
			es.setTipoDeEvento("MultidaoEmPe");
			es.setNome("Festival Estou de Ferias");
			Date d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 7, 31));
			Date inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 7, 31, 21, 0));
			Date fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 7, 31, 23, 0));
			es.addDate(d,inicio, fim);
			d = DateUtils.convertLocalDateToDate(LocalDate.of(2021, 8, 1));
			inicio =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 8, 1, 14, 0));
			fim =  DateUtils.convertLocalDateTimeToDate(LocalDateTime.of(2021, 8, 1, 19, 0));
			es.addDate(d,inicio, fim);
			es.setEmpresa(2);
			es.createEvent();
			System.out.println("Test 6: Passed");
			return 1;
		}catch (Exception e) {
			System.out.println("Test 6: Failed");
		}
		return 0;
	}

}
