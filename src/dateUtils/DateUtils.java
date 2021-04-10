package dateUtils;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

	public static final Date present = convertLocalDateToDate(LocalDate.of(2021, 5, 1));

	public static boolean isPast(Date d) {
		return d.before(present);
	}

	public static Date convertLocalDateToDate(LocalDate dateToConvert) {
		return java.util.Date.from(dateToConvert.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant());
	}

	public static Date convertLocalDateTimeToDate(LocalDateTime dateToConvert) {
		return java.util.Date
				.from(dateToConvert.atZone(ZoneId.systemDefault())
						.toInstant());
	}

	public static LocalDate dateToLocalDate(Date d) {
		return Instant.ofEpochMilli(d.getTime())
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}

}
